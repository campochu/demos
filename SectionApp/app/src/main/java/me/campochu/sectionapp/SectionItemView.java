package me.campochu.sectionapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * ckb on 2017/5/21.
 */

public abstract class SectionItemView<T extends Section>
    extends RecyclerView.ViewHolder implements Section, OnClickListener {

    public static final int CLICK_ITEM = 0;

    protected SectionListener<T> mListener;

    protected T mViewModle;

    public SectionItemView(View itemView) {
        super(itemView);
    }

    public final void update(T model) {
        mViewModle = model;
        updateImpl(model);
    }

    protected abstract void updateImpl(T model);

    public final T getModel() {
        return mViewModle;
    }

    public interface SectionListener<T> {
        void onClickListener(int flag, T model);
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) {
            return;
        }
        fireClick(v);
    }

    protected abstract void fireClick(View v);

    public final void setSectionListener(SectionListener<T> listener) {
        mListener = listener;
    }

    public static interface Creator {
        SectionItemView create(LayoutInflater inflater, ViewGroup parent);
    }

}
