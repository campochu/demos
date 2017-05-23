package me.campochu.sectionapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.campochu.sectionapp.R;
import me.campochu.sectionapp.SectionItemView;
import me.campochu.sectionapp.model.Item1;

/**
 * Created by ckb on 17/5/22.
 */

public class Item1SectionItemView extends SectionItemView<Item1> {

    public static final int CLICK_HELLO = 1;

    private TextView mHelloView;

    public Item1SectionItemView(View itemView) {
        super(itemView);

        mHelloView = (TextView)itemView.findViewById(R.id.hello);

        mHelloView.setOnClickListener(this);
    }

    @Override
    protected void updateImpl(Item1 model, int position) {
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
            return new Item1SectionItemView(inflater.inflate(R.layout.view_item_1, parent, false));
        }
    };

}
