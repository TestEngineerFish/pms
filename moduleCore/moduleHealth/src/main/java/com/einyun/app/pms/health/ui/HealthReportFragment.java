package com.einyun.app.pms.health.ui;

import android.graphics.Bitmap;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.einyun.app.common.ui.fragment.BaseViewModelFragment;
import com.einyun.app.base.util.BitmapUtil;
import com.einyun.app.base.util.FileUtil;
import com.einyun.app.pms.health.Constants;
import com.einyun.app.pms.health.R;
import com.einyun.app.pms.health.databinding.FragmentHealthBinding;
import com.einyun.app.pms.health.event.DownLoadEvent;
import com.einyun.app.pms.health.event.DownloadState;
import com.einyun.app.pms.health.viewmodel.HealthViewModel;
import com.einyun.app.pms.health.viewmodel.ViewModelFactory;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 * demo of workmanager
 */
public class HealthReportFragment extends BaseViewModelFragment<FragmentHealthBinding, HealthViewModel> {

    public static HealthReportFragment newInstance() {
        return new HealthReportFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_health;
    }


    @Override
    protected void setUpView() {
        binding.setCallBack(this);
        binding.setHealthViewModel(viewModel);
    }

    @Override
    protected void setUpData() {
        LiveEventBus.get().with(Constants.KEY_LIVE_BUS_EVENT_DOWNLOAD,DownLoadEvent.class).observeForever(downLoadEvent -> {
           if(downLoadEvent.getState()== DownloadState.STARTED){
                binding.progressBar.setVisibility(View.VISIBLE);
           }else if(downLoadEvent.getState()== DownloadState.PROGRESS){

           }else if(downLoadEvent.getState()== DownloadState.CANCELED){
               binding.progressBar.setVisibility(View.GONE);
           }else if(downLoadEvent.getState()== DownloadState.FINISHED){
               binding.progressBar.setVisibility(View.GONE);
               Bitmap bitmap= BitmapUtil.getBitmap(FileUtil.getDiskCacheImagePath()+ File.separator+downLoadEvent.getUuid());
               binding.ivImage.setImageBitmap(bitmap);
           }
        });
    }

    public void onDownloadClick(){
        viewModel.doDownloadWork();
    }

    @Override
    protected HealthViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(HealthViewModel.class);
    }
}
