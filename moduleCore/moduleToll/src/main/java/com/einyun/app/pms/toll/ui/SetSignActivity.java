package com.einyun.app.pms.toll.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.util.LogTime;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.utils.FormatUtil;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivityAddWorthReminderBinding;
import com.einyun.app.pms.toll.databinding.ActivitySetSignBinding;
import com.einyun.app.pms.toll.databinding.SignItemBinding;
import com.einyun.app.pms.toll.databinding.UnitCheckPopwindowItemBinding;
import com.einyun.app.pms.toll.model.BuildModel;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.FeeRequset;
import com.einyun.app.pms.toll.model.GetSignModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.LoginException;

@Route(path = RouterUtils.ACTIVITY_SET_SIGN)
public class SetSignActivity extends BaseHeadViewModelActivity<ActivitySetSignBinding, TollViewModel> {
    @Autowired(name = RouteKey.HOUSE_ID)
    String houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.CLIENT_ID)
    String clientId;
    ArrayList<String> lables = new ArrayList<>();
    private RVBindingAdapter<SignItemBinding, GetSignModel.DataBean.TagListBean> adapterGetSign;
    private RVBindingAdapter<SignItemBinding, GetSignModel.DataBean.TagListBean> adapterSetSign;
    private FeeDetailRequset mRequset;
    private List<GetSignModel.DataBean.TagListBean> tagList;
    private List<GetSignModel.DataBean.TagListBean> mTagLists = new ArrayList<>();
    private AlertDialog alertAddDialog;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_sign;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        setTxtColor(getResources().getColor(R.color.blackTextColor));
        setHeadTitle(R.string.tv_add_worth_reminder);
    }

    private static final String TAG = "SetSignActivity";

    @Override
    protected void initData() {
        super.initData();
        binding.setCallBack(this);
        getData();
    }

    private void getData() {
        mRequset = new FeeDetailRequset();
        mRequset.setDivideId(divideId);
        mRequset.setClientId(clientId);
        viewModel.getSign(mRequset).observe(this, model -> {
            if (model == null || model.getData() == null) {
                return;
            }
            mTagLists = model.getData().getTagList();

            if (mTagLists != null) {

                adapterGetSign.setDataList(mTagLists);
            }
        });
        tagList = new ArrayList<>();
        tagList.clear();
        GetSignModel.DataBean.TagListBean tagListBean = new GetSignModel.DataBean.TagListBean();
        tagList.add(tagList.size(), tagListBean);
        adapterSetSign = new RVBindingAdapter<SignItemBinding, GetSignModel.DataBean.TagListBean>(this, com.einyun.app.pms.toll.BR.check) {

            @Override
            public void onBindItem(SignItemBinding binding, GetSignModel.DataBean.TagListBean model, int position) {
                if (position == tagList.size() - 1) {
                    binding.etContent.setVisibility(View.VISIBLE);
                    binding.tvContent.setVisibility(View.GONE);
                    if (position == 4) {
                        binding.llItem.setVisibility(View.GONE);
                        binding.etContent.setVisibility(View.GONE);
                        binding.tvContent.setVisibility(View.VISIBLE);
                    }
                    binding.etContent.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            model.setTagValue(charSequence.toString().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    binding.etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                            //判断是否是“完成”键
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                //隐藏软键盘
                                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (imm.isActive()) {
                                    imm.hideSoftInputFromWindow(
                                            textView.getApplicationWindowToken(), 0);
                                }
                                if (!StringUtil.isEmpty(textView.getText().toString().trim())) {
                                    model.setTagValue(textView.getText().toString());
                                    if (tagList.size() < 5) {
                                    }
                                    if (tagList.size() < 5) {
                                        tagList.add(tagList.size(), new GetSignModel.DataBean.TagListBean());
                                        if (mTagLists != null) {
                                            for (GetSignModel.DataBean.TagListBean mTagList : mTagLists) {
                                                if (mTagList.getTagValue().equals(textView.getText().toString())) {
                                                    model.setChecked(1);
                                                    mTagList.setChecked(1);
                                                }
                                            }
                                        }
                                        adapterSetSign.setDataList(tagList);
                                        Log.e(TAG, "onEditorAction: " + textView.getText().toString());

                                    } else if (tagList.size() == 4) {
                                        adapterSetSign.setDataList(tagList);
                                    }
                                    if (mTagLists != null) {
                                        adapterGetSign.setDataList(mTagLists);
                                    }
                                }
                                return true;
                            }
                            return false;
                        }
                    });
                    binding.etContent.setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                                String content = binding.etContent.getText().toString().trim();
                                if (content.length() == 0) {
                                    Log.e(TAG, "onKey: 输入为空了");
                                    if (tagList.size() > 1) {

                                        tagList.remove(tagList.size() - 2);
                                        if (mTagLists != null) {
                                            for (GetSignModel.DataBean.TagListBean mTagList : mTagLists) {
                                                for (GetSignModel.DataBean.TagListBean listBean : tagList) {

                                                    if (mTagList.getTagValue().equals(listBean.getTagValue())) {
                                                        listBean.setChecked(1);
                                                        mTagList.setChecked(1);
                                                    } else {
                                                        listBean.setChecked(0);
                                                        mTagList.setChecked(0);
                                                    }
                                                }
                                            }

                                            adapterSetSign.setDataList(tagList);
                                            adapterGetSign.setDataList(mTagLists);
                                        }


                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                    binding.tvContent.setOnClickListener(view1 -> {

                        mPosition=position;
                        if (alertAddDialog == null) {
                            alertAddDialog = new AlertDialog(SetSignActivity.this).builder().setTitle(getResources().getString(R.string.tip))
                                    .setMsg("确定删除该标签?")
                                    .setNegativeButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setPositiveButton("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

//                                            if (tagList.size() == 5) {
//                                                tagList.remove(tagList.size() - 1);
//                                                tagList.remove(mPosition);
//                                            } else {
//                                                tagList.remove(mPosition);
//                                            }
//                                            adapterSetSign.setDataList(tagList);
                                            tagList.remove(mPosition);
                                            if (mTagLists!=null) {
                                                for (GetSignModel.DataBean.TagListBean mTagList : mTagLists) {
                                                    for (GetSignModel.DataBean.TagListBean listBean : tagList) {

                                                        if (mTagList.getTagValue().equals(listBean.getTagValue())) {
                                                            listBean.setChecked(1);
                                                            mTagList.setChecked(1);
                                                        }else {
                                                            listBean.setChecked(0);
                                                            mTagList.setChecked(0);
                                                        }
                                                    }
                                                }
                                                adapterGetSign.setDataList(mTagLists);
                                            }
                                            adapterSetSign.notifyDataSetChanged();
                                        }
                                    });
                            alertAddDialog.show();
                        } else {
                            if (!alertAddDialog.isShowing()) {
                                alertAddDialog.show();
                            }
                        }
//                    adapterSetSign.notifyDataSetChanged();
                    });
                } else {
                    binding.etContent.setVisibility(View.GONE);
                    binding.tvContent.setVisibility(View.VISIBLE);
                }
//                binding.llItem.setOnClickListener(view1 -> {
//                    if (model.getChecked() == 0) {
//                        model.setChecked(1);
//                    } else {
//                        model.setChecked(0);
//                    }
//                    adapterSetSign.notifyDataSetChanged();
//                });
                if (model.getChecked() == 1) {
                    binding.tvContent.setTextColor(getResources().getColor(R.color.blueTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius5_blue_solid);
                    model.setChecked(1);
                } else {
                    binding.tvContent.setTextColor(getResources().getColor(R.color.blackTextColor));
                    model.setChecked(0);
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius5_grey);
                }
                binding.tvContent.setText(model.getTagValue());

            }

            @Override
            public int getLayoutId() {
                return R.layout.sign_item;
            }

        };

        adapterSetSign.setDataList(tagList);
        binding.listSetSign.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        binding.listSetSign.setAdapter(adapterSetSign);

        adapterGetSign = new RVBindingAdapter<SignItemBinding, GetSignModel.DataBean.TagListBean>(this, com.einyun.app.pms.toll.BR.check) {

            @Override
            public void onBindItem(SignItemBinding binding, GetSignModel.DataBean.TagListBean model, int position) {


                if (model.getChecked() == 1) {
                    binding.tvContent.setTextColor(getResources().getColor(R.color.blueTextColor));
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius5_blue_solid);
                    model.setChecked(1);
                } else {
                    binding.tvContent.setTextColor(getResources().getColor(R.color.blackTextColor));
                    model.setChecked(0);
                    binding.tvContent.setBackgroundResource(R.drawable.shape_rect_radius5_grey);
                }
                binding.tvContent.setText(model.getTagValue());

            }

            @Override
            public int getLayoutId() {
                return R.layout.sign_item;
            }

        };

        binding.listGetSign.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        binding.listGetSign.setAdapter(adapterGetSign);
    }

    int mPosition;
    /**
     * 提交按钮
     */
    public void onPassClick() {
//        if (IsFastClick.isFastDoubleClick()) {

        lables.clear();
        if (tagList.size() != 0) {
            if (StringUtil.isEmpty(tagList.get(0).getTagValue())) {

                ToastUtil.show(this, "请输入标签");
                return;
            }
        }
        for (GetSignModel.DataBean.TagListBean tagListBean : tagList) {
            if (tagListBean.getTagValue() != null) {

                lables.add(tagListBean.getTagValue());
            }
        }

        if (mTagLists != null) {
            for (GetSignModel.DataBean.TagListBean mTagList : mTagLists) {
                if (mTagList.getTagValue() != null) {

                    if (!lables.contains(mTagList.getTagValue())) {
                        lables.add(mTagList.getTagValue());
                    }
                }
            }
        }
        FeeDetailRequset mRequset = new FeeDetailRequset();
        mRequset.setDivideId(divideId);
        mRequset.setClientId(clientId);
        mRequset.setLables(lables);
        viewModel.setSign(mRequset).observe(this, model -> {

            if (model.getCode() == 0) {
                getData();
            } else {
                ToastUtil.show(SetSignActivity.this, model.getMsg());
            }


        });
//        }
    }

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
