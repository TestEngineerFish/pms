package com.einyun.app.common.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("AppCompatCustomView")
public class BaseEditText extends EditText {

    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setFilters(InputFilter[] filters) {
        Log.e("filters", String.valueOf(filters.length));
        InputFilter emojiFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        if (filters == null || filters.length == 0) {
            super.setFilters(new InputFilter[]{emojiFilter});
        } else {
            InputFilter[] inputFilters = new InputFilter[filters.length + 1];
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
            inputFilters[filters.length] = emojiFilter;
            super.setFilters(inputFilters);
        }

    }
}