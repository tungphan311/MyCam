package com.example.tung.androidproject.model;

public class Diachi {
    public int madc;
    public String hoten;
    public String sodt;
    public String diachicuthe;
    public String mauser;

    public Diachi(int madc, String hoten, String sodt, String diachicuthe, String mauser) {
        this.madc = madc;
        this.hoten = hoten;
        this.sodt = sodt;
        this.diachicuthe = diachicuthe;
        this.mauser = mauser;
    }

    public int getMadc() {
        return madc;
    }

    public void setMadc(int madc) {
        this.madc = madc;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSodt() {
        return sodt;
    }

    public void setSodt(String sodt) {
        this.sodt = sodt;
    }

    public String getDiachicuthe() {
        return diachicuthe;
    }

    public void setDiachicuthe(String diachicuthe) {
        this.diachicuthe = diachicuthe;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }
}
