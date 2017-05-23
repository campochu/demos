package me.campochu.sectionapp;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ckb on 2017/5/21.
 */

public class SectionAdapter extends RecyclerView.Adapter {

    private SectionViewFactroy mViewFactroy;

    private List<Section> mItems = new ArrayList<>();

    public SectionAdapter(SectionViewFactroy viewFactroy) {
        mViewFactroy = viewFactroy;
    }

    public void setItems(List<? extends Section> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<Section> getItems() {
        return Collections.unmodifiableList(mItems);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewFactroy.create(viewType, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Section model = mItems.get(position);
        if (model != null && holder instanceof SectionItemView) {
            ((SectionItemView) holder).update(model, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getSectionType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public final int getSpan(int position) {
        return mViewFactroy.getSpan(getItemViewType(position));
    }

}
