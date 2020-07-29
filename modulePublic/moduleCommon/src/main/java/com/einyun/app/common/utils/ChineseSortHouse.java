package com.einyun.app.common.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.einyun.app.common.model.BuildModel;
import com.einyun.app.library.uc.usercenter.model.HouseModel;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.List;

//import com.einyun.app.library.mdm.model.BuildingModel;
//import com.einyun.app.library.mdm.model.DivideModel;
//import com.einyun.app.library.mdm.model.HouseModel;
//import com.einyun.app.library.mdm.model.UnitModel;

/**
 * @Description: 对汉字进行排序
 * @Author: Liangchaojie
 * @Create On 2018/2/26 12:51
 */

public class ChineseSortHouse {

//    /**
//     * 进行冒泡排序
//     *
//     * @param list
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static void transferListHouse(List<HouseModel> list) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            for (int j = 0; j < list.size() - 1 - i; j++) {
//                exchangeNameOrderHouse(j, list);
//            }
//        }
//    }
//
    /**
     * 交换两个名字的顺序,根据首字母判断
     *
     * @param j
     * @param list
     */
    private static void exchangeNameOrderHouse(int j, List<HouseModel> list) {
        String namePinYin1 = Pinyin.toPinyin(list.get(j).getName().replaceAll("-",""),"");
        String namePinYin2 = Pinyin.toPinyin(list.get(j + 1).getName().replaceAll("-",""),"");
        int size = namePinYin1.length() >= namePinYin2.length() ? namePinYin2.length() : namePinYin1.length();
        for (int i = 0; i < size; i++) {
            char jc = namePinYin1.charAt(i);
            char jcNext = namePinYin2.charAt(i);
            if (jc < jcNext) {//A在B之前就不用比较了
                break;
            }
            if (jc > jcNext) {//A在B之后就直接交换,让A在前面B在后面
                HouseModel nameBean = list.get(j);
                list.set(j, list.get(j + 1));
                list.set(j + 1, nameBean);
                break;
            }
            //如果AB一样就继续比较后面的字母
        }
    }

    /**
     * 升序排序
     *
     * @param list
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void transferListBuild(List<HouseModel> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                exchangeNameOrderBuild(j, list);
            }
        }
    }
    /**
     * 降序排序
     *
     * @param list
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void transferListBuildDown(List<HouseModel> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                exchangeNameOrderBuildDown(j, list);
            }
        }
    }
    /**
     * 交换两个名字的顺序,根据首字母判断
     *down
     * @param j
     * @param list
     */
    private static void exchangeNameOrderBuildDown(int j, List<HouseModel> list) {
        String code1 = list.get(j).getName();
        if (code1.length()==1) {
            code1="0"+code1;
        }
        String code2 = list.get(j+1).getName();
        if (code2.length()==1) {
            code2="0"+code2;
        }
        String namePinYin2 = Pinyin.toPinyin(code1.replaceAll("-",""),"");
        String namePinYin1 = Pinyin.toPinyin(code2.replaceAll("-",""),"");
        int size = namePinYin1.length() >= namePinYin2.length() ? namePinYin2.length() : namePinYin1.length();
        for (int i = 0; i < size; i++) {
            char jc = namePinYin1.charAt(i);
            char jcNext = namePinYin2.charAt(i);
            if (jc < jcNext) {//A在B之前就不用比较了
                break;
            }
            if (jc > jcNext) {//A在B之后就直接交换,让A在前面B在后面
                HouseModel nameBean = list.get(j);
                list.set(j, list.get(j + 1));
                list.set(j + 1, nameBean);
                break;
            }
            //如果AB一样就继续比较后面的字母
        }
    }
    /**
     * 交换两个名字的顺序,根据首字母判断
     *
     * @param j
     * @param list
     */
    private static void exchangeNameOrderBuild(int j, List<HouseModel> list) {
        String code1 = list.get(j).getName();
        if (code1.length()==1) {
            code1="0"+code1;
        }
        String code2 = list.get(j+1).getName();
        if (code2.length()==1) {
            code2="0"+code2;
        }
        String namePinYin1 = Pinyin.toPinyin(code1.replaceAll("-",""),"");
        String namePinYin2 = Pinyin.toPinyin(code2.replaceAll("-",""),"");
        int size = namePinYin1.length() >= namePinYin2.length() ? namePinYin2.length() : namePinYin1.length();
        for (int i = 0; i < size; i++) {
            char jc = namePinYin1.charAt(i);
            char jcNext = namePinYin2.charAt(i);
            if (jc < jcNext) {//A在B之前就不用比较了
                break;
            }
            if (jc > jcNext) {//A在B之后就直接交换,让A在前面B在后面
                HouseModel nameBean = list.get(j);
                list.set(j, list.get(j + 1));
                list.set(j + 1, nameBean);
                break;
            }
            //如果AB一样就继续比较后面的字母
        }
    }


//    /**
//     * 进行冒泡排序
//     *
//     * @param list
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static void transferListUnit(List<UnitModel> list) {
//        for (int i = 0; i < list.size() - 1; i++) {
//            for (int j = 0; j < list.size() - 1 - i; j++) {
//                exchangeNameOrder(j, list);
//            }
//        }
//    }

//    /**
//     * 交换两个名字的顺序,根据首字母判断
//     *
//     * @param j
//     * @param list
//     */
//    private static void exchangeNameOrder(int j, List<UnitModel> list) {
//        String namePinYin1 = Pinyin.toPinyin(list.get(j).getUnitCode().replaceAll("-",""),"");
//        String namePinYin2 = Pinyin.toPinyin(list.get(j + 1).getUnitCode().replaceAll("-",""),"");
//        int size = namePinYin1.length() >= namePinYin2.length() ? namePinYin2.length() : namePinYin1.length();
//        for (int i = 0; i < size; i++) {
//            char jc = namePinYin1.charAt(i);
//            char jcNext = namePinYin2.charAt(i);
//            if (jc < jcNext) {//A在B之前就不用比较了
//                break;
//            }
//            if (jc > jcNext) {//A在B之后就直接交换,让A在前面B在后面
//                UnitModel nameBean = list.get(j);
//                list.set(j, list.get(j + 1));
//                list.set(j + 1, nameBean);
//                break;
//            }
//            //如果AB一样就继续比较后面的字母
//        }
//    }
}