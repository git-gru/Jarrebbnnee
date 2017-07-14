package com.jarrebbnnee;

/**
 * Created by vi6 on 08-Mar-17.
 */

public class DemoPojo {
    String productName, productPrice, productBrand, date;
    Integer imageId;

    public DemoPojo(String productName, Integer imageId) {
        this.productName = productName;
        this.imageId = imageId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public DemoPojo(String productName, String productPrice) {

        this.productName = productName;
        this.productPrice = productPrice;
    }

    public DemoPojo(String productBrand, String date, String productPrice) {
        this.productBrand = productBrand;
        this.date = date;
        this.productPrice = productPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }


}
