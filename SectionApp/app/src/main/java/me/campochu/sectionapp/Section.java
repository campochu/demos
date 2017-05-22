package me.campochu.sectionapp;

/**
 * ckb on 2017/5/21.
 */

public interface Section {

    int SPAN_SHIFT = 3;
    int SPAN_MASK = 7;
    int SPAN1 = 1;
    int SPAN2 = 2;
    int SPAN3 = 3;
    int SPAN4 = 4;
    int SPAN5 = 5;
    int SPAN6 = 6;

    int FACTROY = 0;

    int ITEM_1 = 1 << SPAN_SHIFT | SPAN2;

    int getSectionType();

}
