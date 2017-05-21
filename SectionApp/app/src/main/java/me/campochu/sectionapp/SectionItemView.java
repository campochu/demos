package me.campochu.sectionapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ckb on 2017/5/21.
 */

public abstract class SectionItemView<T extends Section> extends RecyclerView.ViewHolder {

    public SectionItemView(View itemView) {
        super(itemView);
    }

}
