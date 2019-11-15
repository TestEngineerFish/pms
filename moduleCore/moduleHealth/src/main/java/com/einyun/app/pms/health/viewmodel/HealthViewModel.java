package com.einyun.app.pms.health.viewmodel;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.einyun.app.base.BaseViewModel;
import com.einyun.app.base.util.FileUtil;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.pms.health.Constants;
import com.einyun.app.pms.health.work.DownloadWork;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.pms.health.viewmodel
 * @ClassName: HealthViewModel
 * @Description: WorkManager use demo
 * @Author: chumingjun
 * @CreateDate: 2019/09/06 11:24
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/09/06 11:24
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class HealthViewModel extends BaseViewModel {
    public String downloadUrl="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=358459715,3612699008&fm=26&gp=0.jpg";


    /**
     * workmanager ,download sample
     */
    public void doDownloadWork(){
        //构建传入参数，use for WorkRequest->doWork
        Data data=new Data.Builder()
                .putString(Constants.KEY_REPORT_URL, downloadUrl)//下载地址
                .putString(Constants.KEY_REPORT_SAVE_DIR,FileUtil.getDiskCacheImagePath())//存储地址
                .build();
        //构建一次任务
        OneTimeWorkRequest request= new OneTimeWorkRequest.Builder(DownloadWork.class)
                .setInputData(data)
                .build();
        //加入队列
        WorkManager.getInstance(CommonApplication.getInstance()).enqueue(request);
    }
}
