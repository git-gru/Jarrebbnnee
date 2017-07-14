package com.jarrebbnnee;

/**
 * Created by vi6 on 23-Mar-17.
 */

public class POJO_Drawer {
    String name, id;
    int imgRes;

    public POJO_Drawer(String name, int imgRes) {
        this.name = name;
        this.imgRes = imgRes;
    }

    public POJO_Drawer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

}
