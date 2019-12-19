package com.einyun.app.common.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceLeft=0;
    private int spaceRight=0;
    private int spaceTop=0;
    private int spaceBottom=0;

    public SpacesItemDecoration(int spaceLeft,int spaceRight,int spaceTop,int spaceBottom ) {
        this.spaceLeft = spaceLeft;
        this.spaceRight=spaceRight;
        this.spaceTop=spaceTop;
        this.spaceBottom=spaceBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = spaceLeft;
        outRect.right = spaceRight;
        outRect.top = spaceTop;
        outRect.bottom = spaceBottom;
    }
}
