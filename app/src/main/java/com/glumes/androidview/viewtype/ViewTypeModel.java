package com.glumes.androidview.viewtype;

/**
 * @author glumes
 */

public class ViewTypeModel {

    public String title;

    public int type;

    public ViewTypeModel(String title) {
        this.title = title;
    }

    public ViewTypeModel(String title, int type) {
        this.title = title;
        this.type = type;
    }
}
