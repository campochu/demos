package me.campochu.sectionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * ckb on 2017/5/21.
 */

public abstract class SectionViewFactroy {

    protected LayoutInflater mInflater;

    public SectionViewFactroy(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public abstract SectionItemView create(int section, ViewGroup viewGroup);

    public abstract int getSpan(int section);

}
