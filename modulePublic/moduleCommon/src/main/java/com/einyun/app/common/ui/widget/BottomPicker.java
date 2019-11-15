package com.einyun.app.common.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.einyun.app.common.R;
import com.einyun.app.common.model.BottomPickerModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 底部选择菜单
 */
public class BottomPicker {

    public interface OnItemPickListener {
        void onPick(int position, String label);
    }

    public interface OnItemDoublePickListener {
        void onPick(int position1, int position2);
    }

    public static Dialog buildBottomPicker(Context context,
                                           List<String> selection,
                                           OnItemPickListener listener) {
        return buildBottomPicker(context, selection, 0, listener);
    }

    public static Dialog buildBottomPicker(Context context,
                                           List<String> selection,
                                           int defaultSelection,
                                           OnItemPickListener listener) {

        View view = View.inflate(context, R.layout.dialog_bottom_picker, null);
        WheelView wheelview = view.findViewById(R.id.wheelview);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);

        AtomicInteger selectedPos = new AtomicInteger(defaultSelection);
        wheelview.setCyclic(false);
        wheelview.setAdapter(new ArrayWheelAdapter(selection));
        wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectedPos.set(index);
            }
        });
        wheelview.setCurrentItem(defaultSelection);

        Dialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvConfirm.setOnClickListener(v -> {
            int selected = selectedPos.get();
            if(selected >= selection.size()){
                selected = selection.size() - 1;
            }
            listener.onPick(selected, selection.get(selected));
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());
        return dialog;
    }

    public static Dialog buildBottomEditPicker(Context context,
                                           List<String> selection,
                                           int defaultSelection,
                                           OnItemPickListener listener) {

        View view = View.inflate(context, R.layout.dialog_bottom_picker_edit, null);
        WheelView wheelview = view.findViewById(R.id.wheelview);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        EditText editText = view.findViewById(R.id.et_title);

        AtomicInteger selectedPos = new AtomicInteger(defaultSelection);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                s.toString();
                List<String> afterList = new ArrayList<>();
                for (int i = 0; i < selection.size(); i++) {
                    if (selection.get(i).contains(s.toString())) {
                        afterList.add(selection.get(i));
                    }
                }
                wheelview.setAdapter(new ArrayWheelAdapter(afterList));
                wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        for (int i = 0; i < selection.size(); i++) {
                            if (selection.get(i).equals( afterList.get(index) )) {
                                selectedPos.set(i);
                            }
                        }
                    }
                });
            }
        });


        wheelview.setCyclic(false);
        wheelview.setAdapter(new ArrayWheelAdapter(selection));
        wheelview.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectedPos.set(index);
            }
        });
        wheelview.setCurrentItem(defaultSelection);

        Dialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvConfirm.setOnClickListener(v -> {
            int selected = selectedPos.get();
            listener.onPick(selected, selection.get(selected));
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());
        return dialog;
    }

    public static Dialog buildBottomPicker(Context context,
                                           List<BottomPickerModel> bottomPickerModelList,
                                           int defaultSelection1,
                                           int defaultSelection2,
                                           OnItemDoublePickListener listener) {

        View view = View.inflate(context, R.layout.dialog_bottom_picker_double, null);
        WheelView wheelview1 = view.findViewById(R.id.wheelview1);
        WheelView wheelview2 = view.findViewById(R.id.wheelview2);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);

        List<String> dataList = new ArrayList<>();
        List<String> dataList2= new ArrayList<>();


        for (int i = 0; i < bottomPickerModelList.size(); i++) {
            dataList.add(bottomPickerModelList.get(i).getData());
        }

        AtomicInteger selectedPos1 = new AtomicInteger(defaultSelection1);
        AtomicInteger selectedPos2 = new AtomicInteger(defaultSelection2);

        wheelview1.setCyclic(false);
        wheelview1.setAdapter(new ArrayWheelAdapter(dataList));
        wheelview1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectedPos1.set(index);
                dataList2.clear();
                dataList2.addAll(bottomPickerModelList.get(selectedPos1.get()).getDataList());
                if (dataList2.size()<selectedPos2.get()){
                    selectedPos2.set(0);
                    wheelview2.setCurrentItem(selectedPos2.get());
                }
                wheelview2.setAdapter(new ArrayWheelAdapter(dataList2));

            }
        });
        wheelview1.setCurrentItem(defaultSelection1);

        if (bottomPickerModelList.size()!=0) {
            dataList2.addAll(bottomPickerModelList.get(defaultSelection1).getDataList());
        }


        wheelview2.setCyclic(false);
        wheelview2.setAdapter(new ArrayWheelAdapter(dataList2));
        wheelview2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectedPos2.set(index);
            }
        });
        wheelview2.setCurrentItem(defaultSelection2);

        Dialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);

        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tvConfirm.setOnClickListener(v -> {
            int selected1 = selectedPos1.get();
            int selected2 = selectedPos2.get();
            listener.onPick(selected1 ,selected2);
            dialog.dismiss();
        });

        tvCancel.setOnClickListener(v -> dialog.dismiss());
        return dialog;
    }

}
