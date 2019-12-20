package com.einyun.app.common.ui.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 设置图片间隔
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private final int DEFAULT_SPACE=18;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(){
        space=DEFAULT_SPACE;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
    }
}
