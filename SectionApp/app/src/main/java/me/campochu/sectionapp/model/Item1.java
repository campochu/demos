package me.campochu.sectionapp.model;

import me.campochu.sectionapp.Section;

/**
 * Created by ckb on 17/5/22.
 */

public class Item1 implements Section {

    private String hello;

    public Item1(String hello) {
        this.hello = hello;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    @Override
    public int getSectionType() {
        return ITEM_1;
    }
}
