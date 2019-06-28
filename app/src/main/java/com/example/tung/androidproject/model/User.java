package com.example.tung.androidproject.model;

import java.io.Serializable;

public class User implements Serializable {

    public int mauser;
    public String sodienthoai;
    public String matkhau;
    public String hoten;
    public String gioitinh;
    public int namsinh;
    public String diachi;
    public String email;

    public User(int mauser, String sodienthoai, String matkhau, String hoten, String gioitinh, int namsinh, String diachi, String email) {
        this.mauser = mauser;
        this.sodienthoai = sodienthoai;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.namsinh = namsinh;
        this.diachi = diachi;
        this.email = email;
    }

    public int getMauser() {
        return mauser;
    }

    public void setMauser(int mauser) {
        this.mauser = mauser;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
