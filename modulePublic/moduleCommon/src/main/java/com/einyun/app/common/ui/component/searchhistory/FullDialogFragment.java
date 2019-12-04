package com.einyun.app.common.ui.component.searchhistory;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.einyun.app.base.db.entity.SearchHistory;
import com.einyun.app.base.util.ScreenUtils;
import com.einyun.app.common.R;
import com.einyun.app.common.ui.component.searchhistory.repository.SearchHistoryRepository;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class FullDialogFragment extends DialogFragment {
    private ListView list;
    private EditText etSearch;
    private View cancel;
    private SearchHistoryRepository repository;
    private List<String> histories;
    private ArrayAdapter<String> adapter;
    private int type;
    private SearchListener listener;

    public FullDialogFragment(int type, SearchListener listener) {
        this.type = type;
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_history, container, false);
        histories = new ArrayList<>();
        repository = new SearchHistoryRepository();
        list = view.findViewById(R.id.list);
        etSearch = view.findViewById(R.id.et_search);
        cancel = view.findViewById(R.id.cancel);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getAction() == KeyEvent.KEYCODE_ENTER)) {
                    String search = etSearch.getText().toString();
                    repository.insertSearchHistory(new SearchHistory(search, type));
                    if (listener != null) {
                        listener.search(search);
                    }
                    dismiss();
                }
                return false;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        adapter = new ArrayAdapter<String>(getContext(), R.layout.item_search_history, histories);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.search(histories.get(position));
                dismiss();
            }
        });
        etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        repository.loadAllSearchHistory(type).observe(getActivity(), strings -> {
            if (strings == null) {
                strings = new ArrayList<>();
            }
            histories = strings;
            adapter.clear();
            adapter.addAll(strings);
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //状态栏覆盖在contentView上面，设置透明使contentView的背景透出来
        getDialog().getWindow().setStatusBarColor(getResources().getColor(R.color.activity_bg_color));
    }

}
