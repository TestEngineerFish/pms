package com.einyun.app.common.constants;

import com.einyun.app.base.util.SPUtils;
import com.einyun.app.common.BuildConfig;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.utils.PicEvUtils;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.common.constants
 * @ClassName: DataConstants
 * @Description: intent数据传递
 * 常量数据
 * @Author: chumingjun
 * @CreateDate: 2019/10/15 17:28
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/15 17:28
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DataConstants {
    public static final String KEY_BLOCK_ID = "block_id";
    public static final String KEY_BLOCK_NAME = "block_name";
    public static final String KEY_BLOCK_CODE = "block_code";
    public static final String KEY_QR_SCAN_RESULT="qr_scan_result";
    public static final String KEY_OPTION_RESULT="option_result";
    public static final String KEY_ORG_HQ = "ORG_ZongBu";

    public static final String KEY_ORG_DIVIDE = "organization_type_divide";

    public static final String KEY_ORG_BLOCK = "ORG_DiKuai";
    public static final String PROVIDE_DCIM="/mq_DCIM/";
    public static final String PREX_JPP=".jpg";

    public static final String DATA_PROVIDER_NAME = "com.einyun.app.pms.fileprovider";
    public static final String KEY_SCANNER_CONTENT = "SCANNER_CONTENT";
    public static final String KEY_CHOOSE_DISPOSE_PERSON_CONTENT = "CHOOSE_DISPOSE_PERSON_CONTENT";

    public static final String WORK_ORDER_LIST_TYPE_CREATE = "WORK_ORDER_LIST_TYPE_CREATE";
    public static final String KEY_POSITION="position";
    public static final String KEY_IAMGES="mImages";

    public static final String NOTICE_DETAIL_URL = PicEvUtils.getBaseUrl((String)SPUtils.get(CommonApplication.getInstance(),SPKey.SP_KEY_BUILD_TYPE,"")) + "/h5/pmc/#/communityDetail?id=";//隐私协议
    public static final String WECHAT_APPID = "wxc5e060007fedcd88";
    public static final String WECHAT_APP_SECRET = "472407c1c23b93ec9bc32fbdebc14e0d";
}
