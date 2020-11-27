package com.sict.shopdt.ListProductAdapter;

public class Product {
    private String NameProduct;
    private int PriceProduct;
    private String ImageProduct;
    private int StarProduct;
    private int ID_Sanpham;

    public int getCommentProduct() {
        return CommentProduct;
    }

    public void setCommentProduct(int commentProduct) {
        CommentProduct = commentProduct;
    }

    private int CommentProduct;

    public int getID_Sanpham() {
        return ID_Sanpham;
    }

    public void setID_Sanpham(int ID_Sanpham) {
        this.ID_Sanpham = ID_Sanpham;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public int getPriceProduct() {
        return PriceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        PriceProduct = priceProduct;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }

    public int getStarProduct() {
        return StarProduct;
    }

    public void setStarProduct(int starProduct) {
        StarProduct = starProduct;
    }

    public Product(String nameProduct, int priceProduct, String imageProduct, int starProduct, int id_Sanpham, int commentProduct) {
        this.NameProduct = nameProduct;
        this.PriceProduct = priceProduct;
        this.ImageProduct = imageProduct;
        this.StarProduct = starProduct;
        this.CommentProduct = commentProduct;
        this.ID_Sanpham = id_Sanpham;
    }
    public Product(){

    }
}
