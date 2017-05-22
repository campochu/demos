package me.campochu.sectionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * ckb on 2017/5/21.
 */

public abstract class SectionViewFactroy implements Section {

    protected LayoutInflater mInflater;

    public SectionViewFactroy(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public final SectionItemView create(int section) {
        return createImpl(mInflater, section);
    }

    protected abstract SectionItemView createImpl(LayoutInflater inflater, int section);

    protected abstract int getSpan(int section);

    @Override
    public int getSectionType() {
        return FACTROY;
    }
}
