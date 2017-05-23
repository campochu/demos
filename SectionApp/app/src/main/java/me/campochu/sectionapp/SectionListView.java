package me.campochu.sectionapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * ckb on 2017/5/21.
 */

public class SectionListView extends RecyclerView {
    public SectionListView(Context context) {
        super(context);
    }

    public SectionListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SectionListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            super.setLayoutManager(layout);
        } else {
            throw new ClassCastException("You should only use a GridLayoutManager with GridRecyclerView.");
        }
    }

}
