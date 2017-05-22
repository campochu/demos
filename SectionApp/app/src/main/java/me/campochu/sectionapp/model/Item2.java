package me.campochu.sectionapp.model;

import me.campochu.sectionapp.Section;

/**
 * Created by ckb on 17/5/22.
 */

public class Item2 implements Section {

    private String hello2;

    public Item2(String hello) {
        this.hello2 = hello;
    }

    public String getHello() {
        return hello2;
    }

    public void setHello(String hello) {
        this.hello2 = hello;
    }

    @Override
    public int getSectionType() {
        return ITEM_2;
    }

}
