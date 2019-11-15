package com.einyun.app.base.event;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base.event
 * @ClassName: Status
 * @Description: UI状态
 * 比如是否显示loading加载框，网络状态等
 * @Author: chumingjun
 * @CreateDate: 2019/09/09 17:58
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/09 17:58
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class Status {
    private boolean isLoadingShow;
    private boolean isProgressIndicatorShown;
    private boolean isGenderDialogShown;

    public boolean isLoadingShow() {
        return isLoadingShow;
    }

    public void setLoadingShow(boolean loadingShow) {
        isLoadingShow = loadingShow;
    }

    public boolean isProgressIndicatorShown() {
        return isProgressIndicatorShown;
    }

    public void setProgressIndicatorShown(boolean progressIndicatorShown) {
        isProgressIndicatorShown = progressIndicatorShown;
    }

    public boolean isGenderDialogShown() {
        return isGenderDialogShown;
    }

    public void setGenderDialogShown(boolean genderDialogShown) {
        isGenderDialogShown = genderDialogShown;
    }
}
