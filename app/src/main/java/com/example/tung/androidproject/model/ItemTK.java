package com.example.tung.androidproject.model;

public class ItemTK {
    public int icon;
    public String tenChucNang;

    public ItemTK(int icon, String tenChucNang) {
        this.icon = icon;
        this.tenChucNang = tenChucNang;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }
}
