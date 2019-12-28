package com.einyun.app.common.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.ui.dialog.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * desc
 * author   zhangmeng
 * time     2017/3/30 22:36
 */
public class CheckUtil {
    public static Activity activity;
    /**
     * 用户id正则 8-40位数字或英文
     */
    public static final String REG_USERID = "^[0-9a-zA-Z]{8,40}$";
    /**
     * 手机号正则
     */
    public static final String REG_PHONE = "^\\d{11}$";
    /**
     * 交易密码正则
     */
    public static final String REG_TRS_PWD = "^\\d{6}$";
    /**
     * 营业执照正则
     */
    public static final String REG_YYZZ = "^\\d{13,18}$";
    /**
     * 组织机构正则
     */
    public static final String REG_ZZJG = "^[1-9a-zA-Z\\-]{10}$";
    /**
     * 结算账号正则
     */
    public static final String REG_CLEAR_ACCOUNT = "^\\d{19,22}$";
//    /**
//     * 身份证号正则
//     */
//    public static final String REG_IDNO = "^\\d{8,18}|[0-9x]{8,18}|[0-9X]{8,18}?$";
    /**
     * 军官证正则
     */
    public static final String REG_JGZ = "^\\d{8}$";
    /**
     * 护照
     */
    public static final String REG_HUZHAO = "^[a-zA-Z]{1}\\d{8}$";
    /**
     * 短信验证码
     */
    public static final String REG_SMS = "^\\d{6}$";
    /**
     * 图形验证码
     */
    public static final String REG_VCODE = "^[a-zA-Z0-9]{4}$";
    /**
     * 密码校验
     */
    public static final String REG_PWD = "^(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,20}$";
    /**
     * 清算账号
     */
    public static final String REG_CLR_ACT = "^[0-9]{19,22}$";
    /**
     * 检验备注
     */
    public static final String REG_REMARK = "^[a-zA-Z0-9\u4e00-\u9fa5]{0,20}$";
    /**
     * 英文和数字
     */
    public static final String REG_NUM_AND_ENG = "^[a-zA-Z0-9]+$";
    /**
     * 邮箱正则
     */
    public static final String REG_MAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";


    private CheckUtil() {

    }

    static CheckUtil checkUtil;
    public static CheckUtil getInstance(Activity mActivity){
        activity = mActivity;
        if (checkUtil == null){
            checkUtil = new CheckUtil();
        }
        return checkUtil;
    }

    /**
     * 校验是否输入内容
     *
     * @param editText 控件
     * @return 是否为空
     */
    public boolean isEmpty(TextView editText) {
        String name = getEditName(editText);
        if (StringUtil.isEmpty(editText.getText().toString().trim())) {
            errorAlert("请输入" + name, editText);
            return true;
        }
        return false;
    }

    /**
     * 校验是否输入内容
     *
     * @param editText 控件
     * @return 是否不为空
     */
    public boolean isNotEmpty(TextView editText) {
        String name = getEditName(editText);
        if (StringUtil.isEmpty(editText.getText().toString().trim())) {
            errorAlert("请输入" + name, editText);
            return false;
        }
        return true;
    }


    /**
     * 使用正则校验，包括空校验
     *
     * @param editText editText
     * @return 是否通过校验
     */
    public boolean isMatch(TextView editText, String regEx) {
        String text = editText.getText().toString().trim();
        String name = getEditName(editText);
        if (isEmpty(editText)) {
            return false;
        }
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            errorAlert(name + "格式不正确", editText);
            return false;
        }
        return true;
    }

    private String getEditName(TextView editText) {
        String name;
        //有contentDescription
        if (editText.getContentDescription() != null) {
            name = editText.getContentDescription().toString().trim();
            if (StringUtil.isNullStr(name)) {
                return name;
            }
        }

        if (editText.getHint() != null) {
            //没有contentDescription，取hint 截取
            name = editText.getHint().toString().trim();
            if (StringUtil.isNullStr(name) && name.length() > 3) {
                return name.substring(3, name.length());
            }
        }

        //没有hint 取父控件的第一个子view名称
        View view = ((ViewGroup) editText.getParent()).getChildAt(0);
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString().trim();
        }

        return "未知字段";
    }


    private void errorAlert(String msg, final View view) {
        new AlertDialog(activity).builder().setMsg(msg).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.requestFocus();
            }
        }).show();
    }


    /**
     * 字符串的日期格式的计算
     */
    public int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 校验日期正确性
     *
     * @param startDateTv
     * @param endDateTv
     * @return
     */
    public boolean isDateRight(TextView startDateTv, TextView endDateTv) {
        String startDate = startDateTv.getText().toString();
        String endDate = endDateTv.getText().toString();
        String startName = startDateTv.getContentDescription().toString();
        String endName = endDateTv.getContentDescription().toString();
        if (StringUtil.isEmpty(startDate)) {
            errorAlert("请选择" + startName, startDateTv);
            return false;
        }
        if (StringUtil.isEmpty(endDate)) {
            errorAlert("请选择" + endName, startDateTv);
            return false;
        }
        try {
            if (daysBetween(startDate, endDate) < 0) {
                errorAlert(endName + "不能早于" + startName, startDateTv);
                return false;
            }
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            errorAlert("请选择正确日期",startDateTv);
            return false;
        }
    }

    /**
     * 使用正则校验
     *
     * @param text text
     * @return 是否通过校验
     */
    public boolean isMatch(String text, String regEx) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}
