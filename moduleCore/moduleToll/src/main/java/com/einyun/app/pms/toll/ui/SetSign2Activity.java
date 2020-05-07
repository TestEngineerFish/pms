package com.einyun.app.pms.toll.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.TimeUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.constants.RouteKey;
import com.einyun.app.common.service.RouterUtils;
import com.einyun.app.common.ui.activity.BaseHeadViewModelActivity;
import com.einyun.app.common.ui.dialog.AlertDialog;
import com.einyun.app.common.ui.widget.BaseEditText;
import com.einyun.app.common.utils.IsFastClick;
import com.einyun.app.pms.toll.BR;
import com.einyun.app.pms.toll.R;
import com.einyun.app.pms.toll.databinding.ActivitySetSign2Binding;
import com.einyun.app.pms.toll.databinding.ActivitySetSignBinding;
import com.einyun.app.pms.toll.databinding.SignItemBinding;
import com.einyun.app.pms.toll.model.FeeDetailRequset;
import com.einyun.app.pms.toll.model.GetSignModel;
import com.einyun.app.pms.toll.model.SetSignModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModel;
import com.einyun.app.pms.toll.viewmodel.TollViewModelFactory;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUtils.ACTIVITY_SET_SIGN2)
public class SetSign2Activity extends BaseHeadViewModelActivity<ActivitySetSign2Binding, TollViewModel> {
    @Autowired(name = RouteKey.HOUSE_ID)
    String houseId;
    @Autowired(name = RouteKey.KEY_DIVIDE_ID)
    String divideId;
    @Autowired(name = RouteKey.CLIENT_ID)
    String clientId;
    ArrayList<String> lables = new ArrayList<>();
    private RVBindingAdapter<SignItemBinding, GetSignModel.DataBean.TagListBean> adapterGetSign;
    private FeeDetailRequset mRequset;
    private List<GetSignModel.DataBean.TagListBean> tagList = new ArrayList<>();
    private List<GetSignModel.DataBean.TagListBean> mTagLists = new ArrayList<>();
    boolean isEmpty = true;
    private AlertDialog alertAddDialog;

    @Override
    protected TollViewModel initViewModel() {
        return new ViewModelProvider(this, new TollViewModelFactory()).get(TollViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_sign2;
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

    class ViewHodle {
        TextView tvContent;
        BaseEditText etContent;
        RelativeLayout llItem;
        GetSignModel.DataBean.TagListBean model;
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
        GetSignModel.DataBean.TagListBean tagListBean = new GetSignModel.DataBean.TagListBean();
        tagList.clear();
        tagList.add(tagListBean);

        class Adapter extends BaseAdapter {
            List<GetSignModel.DataBean.TagListBean> tagList;

            public Adapter(List<GetSignModel.DataBean.TagListBean> tagList) {
                this.tagList = tagList;
            }

            @Override
            public int getCount() {
                return tagList == null ? 0 : tagList.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                ViewHodle hodler;
                GetSignModel.DataBean.TagListBean model = tagList.get(position);
                if (convertView != null && convertView instanceof ViewGroup) {
                    hodler = (ViewHodle) convertView.getTag();
                } else {
                    convertView = View.inflate(SetSign2Activity.this, R.layout.sign_item, null);
                    hodler = new ViewHodle();
                    hodler.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                    hodler.etContent = (BaseEditText) convertView.findViewById(R.id.et_content);
                    hodler.llItem = (RelativeLayout) convertView.findViewById(R.id.ll_item);
                    convertView.setTag(hodler);
                }
                if (position == tagList.size() - 1) {
                    hodler.etContent.setVisibility(View.VISIBLE);
                    hodler.tvContent.setVisibility(View.GONE);
                    if (position == 4) {
                        hodler.llItem.setVisibility(View.GONE);
                        hodler.etContent.setVisibility(View.GONE);
                        hodler.tvContent.setVisibility(View.VISIBLE);
                    } else {
                        hodler.llItem.setVisibility(View.VISIBLE);
                    }
                } else {
                    hodler.llItem.setVisibility(View.VISIBLE);
                    hodler.etContent.setVisibility(View.GONE);
                    hodler.tvContent.setVisibility(View.VISIBLE);
                }
                Log.e("model size", tagList.size() + "");
                hodler.model = model;
                Log.e("model", model.getTagValue());
                hodler.etContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                        model.setTagValue(charSequence.toString().trim());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
//                        model.setTagValue(editable.toString().trim());
                    }
                });
                hodler.etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                                    tagList.add(new GetSignModel.DataBean.TagListBean());
                                    if (mTagLists != null) {
                                        for (GetSignModel.DataBean.TagListBean mTagList : mTagLists) {
                                            if (mTagList.getTagValue().equals(textView.getText().toString())) {
                                                model.setChecked(1);
                                                mTagList.setChecked(1);
                                            }
                                        }
                                    }
                                    hodler.model = model;
                                    notifyDataSetChanged();
                                    Log.e(TAG, "onEditorAction: " + textView.getText().toString());

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
                hodler.etContent.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                            String content = hodler.etContent.getText().toString().trim();
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

                                        notifyDataSetChanged();
                                        adapterGetSign.setDataList(mTagLists);
                                    }


                                }
                                return true;
                            }
                        }
                        return false;
                    }
                });
                if (model.getChecked() == 1) {
                    hodler.tvContent.setTextColor(getResources().getColor(R.color.blueTextColor));
                    hodler.tvContent.setBackgroundResource(R.drawable.shape_rect_radius5_blue_solid);
                    model.setChecked(1);
                } else {
                    hodler.tvContent.setTextColor(getResources().getColor(R.color.blackTextColor));
                    model.setChecked(0);
                    hodler.tvContent.setBackgroundResource(R.drawable.shape_rect_radius5_grey);
                }
                hodler.tvContent.setText(model.getTagValue());
                hodler.etContent.setText(model.getTagValue());


                return convertView;

            }

        }
        Adapter adapter = new Adapter(tagList);
        binding.listSetSign.setAdapter(adapter);
        binding.listSetSign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long l) {
                mPosition = position;
                if (alertAddDialog == null) {
                    alertAddDialog = new AlertDialog(SetSign2Activity.this).builder().setTitle(getResources().getString(R.string.tip))
                            .setMsg("确定删除该标签?")
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        ViewHodle viewHodle = (ViewHodle) item.getTag();
//                                        if (tagList.size()==5) {
//                                            tagList.remove(tagList.size()-1);
//                                            tagList.remove(position);
//                                        }else {
//                                            tagList.remove(position);
//                                        }

                                        tagList.remove(mPosition);
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
                                            adapterGetSign.setDataList(mTagLists);
                                        }
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                        binding.listSetSign.removeAllViews();
                                    //binding.listSetSign.setAdapter(new Adapter(tagList));

                                }
                            });
                    alertAddDialog.show();
                } else {
                    if (!alertAddDialog.isShowing()) {
                        alertAddDialog.show();
                    }
                }
//                    adapterSetSign.notifyDataSetChanged();

            }
        });


        adapterGetSign = new RVBindingAdapter<SignItemBinding, GetSignModel.DataBean.TagListBean>(this, com.einyun.app.pms.toll.BR.sign) {

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

    /**
     * 提交按钮
     */
    public void onPassClick() {
        if (IsFastClick.isFastDoubleClick()) {

            lables.clear();
            if (tagList.size() != 0) {
                if (StringUtil.isEmpty(tagList.get(0).getTagValue())) {

//                ToastUtil.show(this, "请确认输入标签");
                    Toast.makeText(this, "请确认输入标签", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            for (GetSignModel.DataBean.TagListBean tagListBean : tagList) {
                if (tagListBean.getTagValue() != null) {

                    if (!StringUtil.isEmpty(tagListBean.getTagValue())) {
                        if (!lables.contains(tagListBean.getTagValue())) {
                            lables.add(tagListBean.getTagValue());
                        }
                    }
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
            showLoading();
            viewModel.repository.setSign(mRequset, new CallBack<SetSignModel>() {
                @Override
                public void call(SetSignModel data) {
                    hideLoading();
//                    setSignModule.postValue(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (data.getCode() == 0) {
                                getData();
                            } else {
//                ToastUtil.show(SetSign2Activity.this, model.getMsg());
                                Toast.makeText(SetSign2Activity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                @Override
                public void onFaild(Throwable throwable) {
                    hideLoading();
                }
            });
//        viewModel.setSign(mRequset).observe(this, model -> {
//
//            if (model.getCode() == 0) {
//                getData();
//            } else {
////                ToastUtil.show(SetSign2Activity.this, model.getMsg());
//                Toast.makeText(this,model.getMsg(),Toast.LENGTH_SHORT).show();
//            }
//
//
//        });
        }
    }

    //    private class PopupItemClickListener  implements {
//
//        public deleteDialog(Context context) {
//            super(context);
//        }
//
//
//    }
    int mPosition;

    @Override
    protected int getColorPrimary() {
        return getResources().getColor(R.color.white);
    }
}
