package com.einyun.app.common.ui.component.searchhistory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.einyun.app.base.adapter.RVBindingAdapter;
import com.einyun.app.base.adapter.RVPageListAdapter;
import com.einyun.app.base.event.ItemClickListener;
import com.einyun.app.base.util.StringUtil;
import com.einyun.app.common.R;
import com.einyun.app.common.utils.RecyclerViewAnimUtil;
import com.einyun.app.library.resource.workorder.model.PlanWorkOrder;

import java.util.List;

/**
 * 分页搜索
 * @param <D>
 * @param <M>
 */
public class PageSearchFragment<D extends ViewDataBinding, M> extends DialogFragment implements TextWatcher {
    private RecyclerView recycleView;
    private EditText etSearch;
    private View cancel;
    //    private SearchHistoryRepository repository;
//    private List<String> histories;
//    private int type;
    private PageSearchListener<D,M> listener;
    private Context context;
    private int br_id;
    private PagedList<M> list;
    RVPageListAdapter<D, M> adapter;
    String hint;

    public PageSearchFragment(Context context, int br_id, PageSearchListener<D,M> listener) {
        this.context = context;
        this.listener = listener;
        this.br_id = br_id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams window = getDialog().getWindow().getAttributes();
        window.windowAnimations = R.style.BottomDialogAnimation;
        getDialog().getWindow().setAttributes(window);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_history, container, false);
//        histories = new ArrayList<>();
//        repository = new SearchHistoryRepository();
        recycleView = view.findViewById(R.id.list);
        etSearch = view.findViewById(R.id.et_search);
        cancel = view.findViewById(R.id.cancel);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getAction() == KeyEvent.KEYCODE_ENTER)) {
//                    String search = etSearch.getText().toString();
//                    if (listener != null) {
//                        listener.searchClick(search);
//                    }
//                    dismiss();
                }
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                dismiss();
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);

        if (adapter == null) {
            adapter = new RVPageListAdapter<D, M>(context, br_id, mDiffCallback) {
                @Override
                public void onBindItem(D binding, M model) {
                    listener.onItem(binding,model);
                }

                @Override
                public int getLayoutId() {
                    return listener.getLayoutId();
                }
            };
        }

        adapter.setOnItemClick(new ItemClickListener<M>() {
            @Override
            public void onItemClicked(View veiw, M data) {
                listener.onItemClick(data);
                dismiss();
            }
        });
        recycleView.setAdapter(adapter);
        RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(recycleView);
        recycleView.addItemDecoration(new SpacesItemDecoration(30));
        etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etSearch.addTextChangedListener(this);
        if (StringUtil.isNullStr(hint)) {
            etSearch.setHint(hint);
        }
//        repository.loadAllSearchHistory(type).observe(getActivity(), strings -> {
//            if (strings == null) {
//                strings = new ArrayList<>();
//            }
//            histories = strings;
//            adapter.clear();
//            adapter.addAll(strings);
//        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
        getDialog().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        listener.search(s.toString()).observe(this, list -> {
            this.list = list;
            adapter.submitList(this.list);
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

    //DiffUtil.ItemCallback,标准写法
    private DiffUtil.ItemCallback<M> mDiffCallback = new DiffUtil.ItemCallback<M>() {

        @Override
        public boolean areItemsTheSame(@NonNull M oldItem, @NonNull M newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull M oldItem, @NonNull M newItem) {
            return oldItem == newItem;
        }
    };

}
