package com.example.weather;

public class weather {
    public  String ngay;
    public String trang_thai;
    public String url_icon;
    public String min;
    public String max;

    public weather(String ngay, String trang_thai, String url_icon, String min, String max) {
        this.ngay = ngay;
        this.trang_thai = trang_thai;
        this.url_icon = url_icon;
        this.min = min;
        this.max = max;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getUrl_icon() {
        return url_icon;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
