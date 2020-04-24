package com.einyun.app.common.utils;

import com.einyun.app.base.BasicApplication;
import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.Constants;

public class UserUtil {
    public  static String getUserName(){
        return  (String) SPUtils.get(BasicApplication.getInstance(), Constants.SP_KEY_USER_NAME, "");
    }
}
