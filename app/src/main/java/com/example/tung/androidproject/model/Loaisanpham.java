package com.example.tung.androidproject.model;

public class Loaisanpham {
    public int maloaisp;
    public String tenloaisp;
    public String hinhanhloaisp;

    public Loaisanpham(int maloaisp, String tenloaisp, String hinhanhloaisp) {
        this.maloaisp = maloaisp;
        this.tenloaisp = tenloaisp;
        this.hinhanhloaisp = hinhanhloaisp;
    }

    public int getMaloaisp() {
        return maloaisp;
    }

    public void setMaloaisp(int maloaisp) {
        this.maloaisp = maloaisp;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public String getHinhanhloaisp() {
        return hinhanhloaisp;
    }

    public void setHinhanhloaisp(String hinhanhloaisp) {
        this.hinhanhloaisp = hinhanhloaisp;
    }
}
