package com.jarrebbnnee;

/**
 * Created by vi6 on 22-Mar-17.
 */

public class POJO_product_list {
    String pd_refer_id, pd_name, pd_price, images;

    public POJO_product_list(String pd_refer_id, String pd_name, String pd_price, String images) {
        this.pd_refer_id = pd_refer_id;
        this.pd_name = pd_name;
        this.pd_price = pd_price;
        this.images = images;
    }

    public String getPd_refer_id() {
        return pd_refer_id;
    }

    public void setPd_refer_id(String pd_refer_id) {
        this.pd_refer_id = pd_refer_id;
    }

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }

    public String getPd_price() {
        return pd_price;
    }

    public void setPd_price(String pd_price) {
        this.pd_price = pd_price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
