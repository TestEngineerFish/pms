package com.einyun.app.pms.health.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.einyun.app.base.util.DownloadUtil;
import com.einyun.app.pms.health.Constants;
import com.einyun.app.pms.health.event.DownLoadEvent;
import com.einyun.app.pms.health.event.DownloadState;

import java.io.File;
import java.util.UUID;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.health.work
 * @ClassName: DownloadWork
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/09/06 11:31
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/06 11:31
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class DownloadWork extends Worker {

    public DownloadWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        String url=data.getString(Constants.KEY_REPORT_URL);
        String dir=data.getString(Constants.KEY_REPORT_SAVE_DIR);
        String uuid=UUID.randomUUID().toString();
        DownloadUtil.get().download(url, dir, uuid, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                LiveEventBus.get().with(Constants.KEY_LIVE_BUS_EVENT_DOWNLOAD)
                        .post(new DownLoadEvent(uuid,DownloadState.FINISHED));
            }

            @Override
            public void onDownloading(int progress) {
                LiveEventBus.get().with(Constants.KEY_LIVE_BUS_EVENT_DOWNLOAD)
                        .post(new DownLoadEvent(progress, DownloadState.PROGRESS));
            }

            @Override
            public void onDownloadFailed(Exception e) {
                LiveEventBus.get().with(Constants.KEY_LIVE_BUS_EVENT_DOWNLOAD)
                        .post(new DownLoadEvent(DownloadState.CANCELED));
            }

        });
        LiveEventBus.get().with(Constants.KEY_LIVE_BUS_EVENT_DOWNLOAD)
                .post(new DownLoadEvent(DownloadState.FINISHED));
        return Result.success();
    }
}
