package com.sict.shopdt.Trangchu;

public class Sanpham {
    private String anhsp;
    private String tensp;
    private int giasp;
    private int ID_Sanpham;
    public String getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(String anhsp) {
        this.anhsp = anhsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getID_Sanpham() {
        return ID_Sanpham;
    }

    public void setID_Sanpham(int ID_Sanpham) {
        this.ID_Sanpham = ID_Sanpham;
    }


    public Sanpham(String anhsp, String tensp, int giasp, int ID_Sanpham) {
        this.anhsp = anhsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.ID_Sanpham = ID_Sanpham;
    }




    public Sanpham() {
    }

}
