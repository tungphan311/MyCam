package com.example.tung.androidproject.util;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.model.ItemTK;

import java.util.ArrayList;

public class Constran {
    public static String connectionErrorMessage = "Vui lòng kiểm tra kết nối Internet";

    public static String localhost = "eshopping.site";
    public static String API_URL = "http://" + localhost + "/server";
    public static String getLoaiSP_URL = "http://" + localhost + "/server/getloaisanpham.php";
    public static String getSPMoiNhat_URL = "http://" + localhost + "/server/getsanphammoinhat.php";
    public static String timkiem_URL = "http://" + localhost + "/server/timkiemsp.php?key=";

    public static String search_URL = "http://" + localhost + "/server/search.php?key=";

    public static String getPhone_URL = "http://" + localhost + "/server/getphone.php?page=";
    public static String getLaptop_URL = "http://" + localhost + "/server/getlaptop.php?page=";
    public static String getTablet_URL = "http://" + localhost + "/server/gettablet.php?page=";
    public static String getPhukien_URL = "http://" + localhost + "/server/getphukien.php?page=";

    public static String getUser_URL = "http://" + localhost + "/server/getuser.php";
    public static String insertUser_URL = "http://" + localhost + "/server/insert_user.php";
    public static String insertDiachi_URL = "http://" + localhost + "/server/insert_diachi.php";
    public static String getDiachi_URL = "http://" + localhost + "/server/getdiachi.php";

    public static String finduserbyid_URL = "http://" + localhost + "/server/finduserbyid.php";

    //Chức năng trong tài khoản
    public static ArrayList<ItemTK> getListChucnang() {
        ArrayList<ItemTK> list = new ArrayList<>();
        list.add(new ItemTK(R.drawable.ic_tk_donhangcuatoi, "Đơn hàng của tôi"));
        list.add(new ItemTK(R.drawable.ic_tk_caidat, "Cài đặt"));
        return list;
    }

    //Chức năng trong ứng dụng
    public static ArrayList<ItemTK> getListCaidat() {
        ArrayList<ItemTK> list = new ArrayList<>();
        list.add(new ItemTK(R.drawable.ic_tk_chinhsach, "Chính sách"));
        list.add(new ItemTK(R.drawable.ic_tk_trogiup, "Trợ giúp"));
        list.add(new ItemTK(R.drawable.icons8_about_50, "Giới thiệu"));
        return list;
    }

    public static String donhang_URL = "http://" + localhost + "/server/insert_donhang.php";
    public static String chitietdonhang_URL = "http://" + localhost + "/server/chitietdonhang.php";
    public static String updatepassword_URL = "http://" + localhost + "/server/updatepassword.php";
    public static String updateinfo_URL = "http://" + localhost + "/server/updateinfo.php";
    public static String getmycart_URL = "http://" + localhost + "/server/getmycart.php?mauser=";
}