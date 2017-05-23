package me.campochu.sectionapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.campochu.sectionapp.R;
import me.campochu.sectionapp.SectionItemView;
import me.campochu.sectionapp.model.Item2;

/**
 * Created by ckb on 17/5/22.
 */

public class Item2SectionItemView extends SectionItemView<Item2> {

    public static final int CLICK_HELLO = 1;

    private TextView mHelloView;

    public Item2SectionItemView(View itemView) {
        super(itemView);

        mHelloView = (TextView) itemView.findViewById(R.id.hello2);

        mHelloView.setOnClickListener(this);
    }

    @Override
    protected void updateImpl(Item2 model, int position) {
        mHelloView.setText(model.getHello());
    }

    @Override
    protected void fireClick(View v) {
        if (v == mHelloView) {
            mListener.onClickListener(CLICK_HELLO, mViewModle);
        }
    }

    @Override
    public int getSectionType() {
        return mViewModle.getSectionType();
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public SectionItemView create(LayoutInflater inflater, ViewGroup parent) {
            return new Item2SectionItemView(inflater.inflate(R.layout.view_item_2, parent, false));
        }
    };

}