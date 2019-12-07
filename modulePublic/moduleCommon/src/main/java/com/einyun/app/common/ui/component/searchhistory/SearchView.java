//package com.einyun.app.common.ui.component.searchhistory;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.einyun.app.common.R;
//
//
//public class SearchView extends LinearLayout {
//    private AppCompatActivity mContext;
//    //    private LlSearchHistoryBinding binding;
////    private DialogSearchHistoryBinding dialogBinding;
//    private SearchFragment dialog;
//    private int mType;
//
//    private SearchListener listener;
//    private View search;
//
//
//    public SearchView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }
//
//    private void init(Context context, AttributeSet attrs) {
//        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SearchHistory);
//        mType = array.getInteger(R.styleable.SearchHistory_type, 0);
//        array.recycle();
//
//        LayoutInflater.from(context).inflate(R.layout.ll_search_history, this);
//        search = findViewById(R.id.search);
//        //菜单按钮的布局
////        binding = DataBindingUtil.getBinding(inflate);
//        search.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mContext == null) {
//                    return;
//                }
//                if (dialog == null) {
//                    dialog = new SearchFragment(mType,listener);
//                    dialog.show(mContext.getSupportFragmentManager(), "dialog");
//                } else {
//                    dialog.show(mContext.getSupportFragmentManager(), "dialog");
//                }
//            }
//        });
//
//    }
//
//    public void setListener(AppCompatActivity activity, SearchListener listener) {
//        this.mContext = activity;
//        this.listener = listener;
//    }
//
//
//}
