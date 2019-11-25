package com.einyun.app.common.skin;

import skin.support.SkinCompatManager;

public class SkinUtil {
    public final static void loadSkin(String skinName){
        SkinCompatManager.getInstance().loadSkin("night", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
    }

    public final static void loadDefaultSkin(){
        SkinCompatManager.getInstance().restoreDefaultTheme();
    }
}
