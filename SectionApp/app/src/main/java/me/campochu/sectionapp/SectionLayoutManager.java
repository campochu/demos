package me.campochu.sectionapp;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * ckb on 2017/5/21.
 */

public class SectionLayoutManager extends GridLayoutManager {

    public SectionLayoutManager(Context context) {
        super(context, 6, VERTICAL, false);
    }

}
