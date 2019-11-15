package com.einyun.app.pms.health.event;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.health.event
 * @ClassName: DownLoadEvent
 * @Description: LiveDataEvent for Download
 * @Author: chumingjun
 * @CreateDate: 2019/09/06 15:13
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/06 15:13
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DownLoadEvent {
    private String uuid;
    private int progress;
    private Enum<DownloadState> state;

    public DownLoadEvent(int progress, Enum<DownloadState> state) {
        this.progress = progress;
        this.state = state;
    }

    public DownLoadEvent(Enum<DownloadState> state) {
        this.state = state;
    }

    public DownLoadEvent(String uuid, Enum<DownloadState> state) {
        this.uuid = uuid;
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Enum<DownloadState> getState() {
        return state;
    }

    public void setState(Enum<DownloadState> state) {
        this.state = state;
    }
}
