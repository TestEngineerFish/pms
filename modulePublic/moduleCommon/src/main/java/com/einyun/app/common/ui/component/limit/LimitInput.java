package com.einyun.app.common.ui.component.limit;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.einyun.app.base.util.StringUtil;
import com.einyun.app.base.util.ToastUtil;
import com.einyun.app.common.R;


/**
 * Created by fanyu on 2017/6/12.
 */

public class LimitInput extends RelativeLayout implements TextWatcher {
    private Context context;

    private EditText editText;

    private TextView textView;
    private String hint;
    private int MAX_COUNT = 300;

    public LimitInput(Context context) {
        this(context, null, 0);
    }

    public LimitInput(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = inflate(context, R.layout.text_input_layout, null);
        addView(view);

        editText = (EditText) view.findViewById(R.id.et_limit_input);
        textView = (TextView) view.findViewById(R.id.tv_limit_input);
        editText.addTextChangedListener(this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LimitInput);
        hint = array.getString(R.styleable.LimitInput_hintText);
        MAX_COUNT = array.getInteger(R.styleable.LimitInput_maxNumber, MAX_COUNT);
        array.recycle();
        textView.setText("0/" + MAX_COUNT);
        if (StringUtil.isNullStr(hint)) {
            editText.setHint(hint);
        }
    }

    public void addTextWatcher(TextWatcher watcher){
        editText.addTextChangedListener(watcher);
    }

    /**
     * 获取输入字符
     *
     * @return String
     */
    public String getString() {
        return editText.getText().toString().trim();
    }

    public void setText(String text){
        editText.setText(text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int editEnd = editText.getSelectionEnd();

        // 先去掉监听器，否则会出现栈溢出
        editText.removeTextChangedListener(this);

        // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
        // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
        if (s.toString().length() > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
            int length = s.toString().length();
            s.delete(editEnd - (length-MAX_COUNT), editEnd);
            editText.setSelection(editEnd - (length-MAX_COUNT));//设置光标在最后
            textView.setText(MAX_COUNT + "/" + MAX_COUNT);
            textView.setTextColor(getResources().getColor(R.color.red));
            ToastUtil.show(context, "请勿超过" + MAX_COUNT + "个字符");
        }else if (s.toString().length() == MAX_COUNT){
            textView.setText(s.toString().length() + "/" + MAX_COUNT);
            textView.setTextColor(getResources().getColor(R.color.red));
        }else {
            textView.setText(s.toString().length() + "/" + MAX_COUNT);
            textView.setTextColor(getResources().getColor(R.color.normal_main_text_icon_color));
        }

        // 恢复监听器
        editText.addTextChangedListener(this);

    }
}
