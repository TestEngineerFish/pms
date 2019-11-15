package com.einyun.app.base.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;


/**
 /**
 * @Author: chumingjun
 * @Email: 15951837502@163.com
 * Time: 2019/08/27
 * */

@SuppressLint("TrulyRandom")
public final class RSA64Util {
    @SuppressWarnings("unused")
    private static String TAG="RSAUtils";
    private static String RSA = "RSA";
    //    private static final String ALGORITHM = "RSA/None/PKCS1Padding";
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";//填充方式需要跟服务端对应

    private static char[] base64EncodeChars = new char[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                    12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
                    30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
                    -1, -1, -1};





    /**
     * 使用公钥加密数据    直接调用这个方法
     * @param mobileCode 手机号/账号
     * @param pwd   密码
     * @param publicKey 公钥字符串 （服务端获取）
     * @return  返回加密后的字符串
     */
    public static String getEncodeInfo(String mobileCode,String pwd,String publicKey){
        try {

            String rsastr = get_key_str( mobileCode, pwd);
            PublicKey key = loadPublicKey(publicKey);
            return   gwencryptData(rsastr,key);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接调用的加密
     * 用公钥加密
     *每次加密的字节数，不能超过密钥的长度值减去11
     *
     */
    private static String gwencryptData(String rsastr,PublicKey publicKey){
        byte[] data = rsastr.getBytes();
        try
        {
            //填充方式需要跟服务端对应
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return encode(cipher.doFinal(data));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




    /**
     * 加密
     * @param data
     * @return
     */
    private static String encode(byte[] data) {
        if (data == null || data.length < 1) return null;

        StringBuilder sb = new StringBuilder();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 解密
     * @param str
     * @return
     */
    private static byte[] decode(String str) {
        if (TextUtils.isEmpty(str)) return null;

        try {
            return decodePrivate(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    private static byte[] decodePrivate(String str) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) return null;

        StringBuilder sb = new StringBuilder();
        byte[] data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len) {
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do {
                b4 = data[i++];
                if (b4 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("iso8859-1");
    }



    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    private static PublicKey loadPublicKey(String publicKeyStr) throws Exception
    {
        try
        {
            byte[] buffer = decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e)
        {
            Log.i("从字符串中加载公钥","无此算法=== publicKeyStr");
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e)
        {
            Log.i("从字符串中加载公钥","公钥非法=== publicKeyStr");
            throw new Exception("公钥非法");
        } catch (NullPointerException e)
        {
            Log.i("从字符串中加载公钥","公钥数据为空=== publicKeyStr");
            throw new Exception("公钥数据为空");
        }
    }


    private static String  get_key_str(String mobileCode,String pwd){
        String phoneinfo = "PhoneInfo";
        try {
            long time = System.currentTimeMillis();
            try {
                String phoneInfo = getPhoneBrand()+"_"+getPhoneModel();
                phoneinfo = phoneInfo.replace("-","_");
//                String aaaa =  mobileCode+"-"+pwd+"-"+getStrTime(time)+"-"+phoneInfo+"-"+get_ranme(time);
//                Log.e("加密字符串",""+aaaa);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return mobileCode+"-"+pwd+"-"+getStrTime(time)+"-"+phoneinfo+"-"+get_ranme(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pwd;
    }
    private static int get_ranme(long time){
        int ii = (int)(time%1000);
        if(ii<100){
            ii+=100;
        }
        return ii;
    }

    // 将时间戳转为字符串
    private static String getStrTime(long time) {
        String re_StrTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            re_StrTime = sdf.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re_StrTime;
    }

    /**
     * 设备厂商
     *
     * @return
     */
    private static String getPhoneBrand() {
        return Build.BOARD + "  " + Build.MANUFACTURER;
    }

    /**
     * 设备名称
     *
     * @return
     */
    private static String getPhoneModel() {
        return Build.MODEL;
    }






}
