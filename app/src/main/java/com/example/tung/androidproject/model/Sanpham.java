package com.example.tung.androidproject.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int masp;
    public String tensp;
    public Integer giasp;
    public String hinhanhsp;
    public String motasp;
    public int maloaisp;
    public int mahangsx;

    public Sanpham(int masp, String tensp, Integer giasp, String hinhanhsp, String motasp, int maloaisp, int mahangsx) {
        this.masp = masp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanhsp = hinhanhsp;
        this.motasp = motasp;
        this.maloaisp = maloaisp;
        this.mahangsx = mahangsx;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public int getMaloaisp() {
        return maloaisp;
    }

    public void setMaloaisp(int maloaisp) {
        this.maloaisp = maloaisp;
    }

    public int getMahangsx() {
        return mahangsx;
    }

    public void setMahangsx(int mahangsx) {
        this.mahangsx = mahangsx;
    }
}
