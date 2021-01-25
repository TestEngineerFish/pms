package com.einyun.app.pms.main.core.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.einyun.app.base.BaseViewModelDialogFragment;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.library.uc.user.model.KaoQingHistroyModel;
import com.einyun.app.library.uc.user.model.KaoQingOrgModel;
import com.einyun.app.library.uc.user.model.Param;
import com.einyun.app.pms.main.BR;
import com.einyun.app.pms.main.R;
import com.einyun.app.pms.main.core.viewmodel.ViewModelFactory;
import com.einyun.app.pms.main.core.viewmodel.WorkBenchViewModel;
import com.einyun.app.pms.main.databinding.FragmentKaoqingBinding;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.pms.main.databinding.ItemKaoqingBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class KaoqingFragment extends BaseViewModelDialogFragment<FragmentKaoqingBinding, WorkBenchViewModel> {


    private LatLng mDestinationPoint;//目的地坐标点
    private LocationClient client;//定位监听
    private LocationClientOption mOption;//定位属性
    private double mDistance = 0;
    private MapFragment mapFragment;
    private List<KaoQingOrgModel> orgModels = new ArrayList<>();
    private KaoQingOrgModel mDestinationOrg;
    RVBindingAdapter<ItemKaoqingBinding, KaoQingHistroyModel> adapter;
    private String ifOut = "0";//默认不可外勤打卡
    private String workStatus;
    LocationManager lm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getContentResolver()
                .registerContentObserver(
                        Settings.Secure
                                .getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
                        false, mGpsMonitor);
        Dialog dialog = getDialog();
        Window window;
        if (dialog != null && (window = dialog.getWindow()) != null) {
            // 一定要设置Background，如果不设置，window属性设置无效
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
            window.getDecorView().setPadding(0, 200, 0, 0);
            window.setAttributes(params);

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kaoqing;
    }

    @Override
    protected void setUpListener() {
        super.setUpListener();
        binding.closeMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KaoqingFragment.this.dismiss();
            }
        });
        binding.kaoqingSize.setOnClickListener(unableListener);
    }

    @Override
    protected void setUpView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.histrou.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            adapter = new RVBindingAdapter<ItemKaoqingBinding, KaoQingHistroyModel>(getActivity(), BR.kaoqingModel) {

                @Override
                public void onBindItem(ItemKaoqingBinding binding, KaoQingHistroyModel model, int position) {
                    if (model.getMark() == null || model.getMark().equals("0")) {
                        binding.outCard.setVisibility(View.GONE);
                    } else {
                        binding.outCard.setVisibility(View.VISIBLE);
                    }
                    if (model.getStatus().equals("1")) {
                        binding.cardStatus.setText("下班打卡");
                    } else {
                        binding.cardStatus.setText("上班打卡");
                    }
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_kaoqing;
                }
            };
        }
        binding.histrou.setAdapter(adapter);
    }

    @Override
    protected void setUpData() {
        viewModel.getWorkState().observe(this, status -> {
            if (status != null) {
                this.workStatus = status;
                viewModel.ifKaoQingOut().observe(this, data1 -> {
                    if (getIfKaoQingOut(data1) != null) {
                        this.ifOut = getIfKaoQingOut(data1).getValue();
                    }
                    viewModel.getOrgLocation().observe(this, data -> {
                        this.orgModels = data;
                        getLocationClientOption();//2、定位开启
                        mapFragment = new MapFragment();
                        mHandler.post(run);//设置系统时间
                        setLatitude(data);
                    });
                });
            }
        });
        getKaoQingHistroy();
    }

    private Param getIfKaoQingOut(List<Param> params) {
        for (Param param : params) {
            if (param.getAlias().equals("clockAuthority")) {
                return param;
            }
        }
        return null;
    }

    /**
     * 设置经纬度
     */
    private void setLatitude(List<KaoQingOrgModel> orgModels) {
        try {
            for (KaoQingOrgModel model : orgModels) {
                model.setLatitude(model.getCyyzb().split("，")[0]);
                model.setLongtitude(model.getCyyzb().split("，")[1]);
            }
        } catch (Exception e) {
            ToastUtil.show(getActivity(), "请配置考勤参数");
        }

    }

    /**
     * 筛选当前考勤产业园
     */
    private KaoQingOrgModel selectKaoQingOrg(LatLng var) {
        List<Double> distances = new ArrayList<>();
        for (KaoQingOrgModel model : orgModels) {
            distances.add(DistanceUtil.getDistance(new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongtitude())), var));
        }
        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) == Collections.min(distances)) {
                mDestinationOrg = orgModels.get(i);
                return orgModels.get(i);
            }
        }
        return null;
    }

    @Override
    protected WorkBenchViewModel initViewModel() {
        return new ViewModelProvider(this, new ViewModelFactory()).get(WorkBenchViewModel.class);

    }


    //设置打卡目标范围圈
    private void setCircleOptions(KaoQingOrgModel model) {
        //打卡范围
        mDestinationPoint = new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongtitude()));//假设公司坐标
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x210565FB)
                .center(mDestinationPoint).stroke(new Stroke(3, 0x660064FF)).radius(model.getKqbj());
    }

    /***
     * 定位选项设置
     * @return
     */
    public void getLocationClientOption() {
        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mOption.setOpenGps(true);//可选，默认false，设置是否开启Gps定位
        mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        client = new LocationClient(getActivity());
        client.setLocOption(mOption);
        client.registerLocationListener(BDAblistener);
        client.start();
    }

    /***
     * 接收定位结果消息，并显示在地图上
     */
    private BDAbstractLocationListener BDAblistener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {

            if ("4.9E-324".equals(location.getLatitude() + "") || !isGpsOpen()) {
                ToastUtil.show(getActivity(), "定位失败,请查看手机是否开启了定位权限");
                showUnnableUi();
            } else {
                //更改UI
                Message message = new Message();
                message.obj = location;
                mHandler.sendMessage(message);
            }
        }
    };

    /**
     * 处理连续定位的地图UI变化
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BDLocation location = (BDLocation) msg.obj;
            LatLng LocationPoint = new LatLng(location.getLatitude(), location.getLongitude());
            selectKaoQingOrg(LocationPoint);
            setCircleOptions(mDestinationOrg);
            //计算两点距离,单位：米
            mDistance = DistanceUtil.getDistance(mDestinationPoint, LocationPoint);
            binding.kaoqingRange.setText(mDestinationOrg.getOrgName());
            binding.kaoqingSize.setOnClickListener(enableListener);
            binding.kaoqingStatusImg.setEnabled(true);
            binding.kaoqingStatusImg.setVisibility(View.VISIBLE);
            if (mDistance <= mDestinationOrg.getKqbj()) {
                mDestinationOrg.setMark("0");
                binding.kaoqingStatusImg.setImageResource(R.drawable.kaoqing_normal);
                setKaoQingText();
            } else {
                binding.kaoqingStatusImg.setImageResource(R.drawable.out_kaoqing);
                binding.kaoqingTxt.setText("外勤打卡");
                mDestinationOrg.setMark("1");
                if (ifOut.equals("0")) {
                    binding.kaoqingStatusImg.setImageResource(R.drawable.unable_kaoqing);
                    binding.kaoqingTxt.setText("无法打卡");
                    binding.kaoqingStatusImg.setEnabled(false);
                }
            }
            kaoqing();
        }
    };

    private void setKaoQingText() {
        if (workStatus.equals("1")) {
            binding.kaoqingTxt.setText("上班打卡");
        } else {
            binding.kaoqingTxt.setText("下班打卡");
        }

    }

    View.OnClickListener enableListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mapFragment.mDestinationOrg = mDestinationOrg;
            mapFragment.show(getActivity().getSupportFragmentManager(), "");
            binding.kaoqingSize.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.kaoqingSize.setEnabled(true);
                }
            }, 1000);
        }
    };
    View.OnClickListener unableListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil.show(getActivity(), "定位失败,请查看手机是否开启了定位权限");

        }
    };

    /**
     * 打卡
     */
    private void kaoqing() {
        binding.kaoqingStatusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.kaoqingStatusImg.setEnabled(false);
                if (!TextUtils.isEmpty(binding.note.getText().toString())) {
                    mDestinationOrg.setNote(binding.note.getText().toString());
                }
                mDestinationOrg.setStatus("0".equals(workStatus) ? "1" : "0");
                viewModel.kaoQing(mDestinationOrg).observe(getActivity(), data -> {
                    if (data != null) {
                        {
                            if (data.equals("0") || data.equals("1")) {
                                workStatus = data;
                                ToastUtil.show(getActivity(), "打卡成功");
                                setKaoQingText();
                            } else {
                                ToastUtil.show(getActivity(), "请勿重复打卡");
                            }
                        }
                    } else {
                        ToastUtil.show(getActivity(), "打卡失败");
                    }
                    binding.kaoqingStatusImg.setEnabled(true);
                    getKaoQingHistroy();
                });
            }
        });
    }

    /**
     * 获取打卡记录
     */

    private void getKaoQingHistroy() {
        viewModel.getKaoQingHistroy().observe(getActivity(), data1 -> {
            adapter.setDataList(data1);
        });
    }


    /**
     * 设置系统时间
     */
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
            Date date = new Date(System.currentTimeMillis());//获取当前时间
            binding.kaoqingTime.setText(simpleDateFormat.format(date)); //更新时间
            mHandler.postDelayed(run, 1000);
        }
    };

    @Override
    public void onDestroy() {
        if (BDAblistener != null) {
            client.unRegisterLocationListener(BDAblistener);

        }
        if (client != null && client.isStarted()) {
            client.stop();
        }
        mHandler.removeCallbacks(run);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        //取消注册传感器监听
        getActivity().getContentResolver().unregisterContentObserver(mGpsMonitor);
    }

    /**
     * 判断gps有么有打开
     **/
    private boolean isGpsOpen() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gpsEnabled;
    }

    /**
     * 监听gps开关
     */
    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            boolean enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            client.restart();
        }
    };
/**
 * 定位失败后界面展示
 * */
private void showUnnableUi(){
    binding.kaoqingSize.setOnClickListener(unableListener);
    binding.kaoqingStatusImg.setImageResource(R.drawable.unable_kaoqing);
    binding.kaoqingStatusImg.setEnabled(false);
    binding.kaoqingTxt.setText("正在定位");
}


}