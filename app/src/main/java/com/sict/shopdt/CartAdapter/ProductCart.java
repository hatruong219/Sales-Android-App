package com.sict.shopdt.CartAdapter;

public class ProductCart {
    private String NameProduct;
    private String NameBrand;
    private int PriceProduct;
    private String ImageProduct;
    private int Quantity;
    private int ID_Sanpham;

    public ProductCart(String nameProduct, String nameBrand, int priceProduct, String imageProduct, int quantity, int id_Sanpham) {
        NameProduct = nameProduct;
        NameBrand = nameBrand;
        PriceProduct = priceProduct;
        ImageProduct = imageProduct;
        Quantity = quantity;
        ID_Sanpham = id_Sanpham;
    }
    public ProductCart(){

    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getNameBrand() {
        return NameBrand;
    }

    public void setNameBrand(String nameBrand) {
        NameBrand = nameBrand;
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
    public int getID_Sanpham() {
        return ID_Sanpham;
    }

    public void setID_Sanpham(int ID_Sanpham) {
        this.ID_Sanpham = ID_Sanpham;
    }




    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
