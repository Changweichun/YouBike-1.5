package com.android.youbike.entity;

public class PaymentList {
    private String num;
    private String start_time;
    private String end_time;
    private String bike_num;
    private String start_site;
    private String end_site;
    private String money;

    public PaymentList(String num, String start_time, String end_time, String bike_num, String start_site, String end_site, String money) {
        this.num = num;
        this.start_time = start_time;
        this.end_time = end_time;
        this.bike_num = bike_num;
        this.start_site = start_site;
        this.end_site = end_site;
        this.money = money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getBike_num() {
        return bike_num;
    }

    public void setBike_num(String bike_num) {
        this.bike_num = bike_num;
    }

    public String getStart_site() {
        return start_site;
    }

    public void setStart_site(String start_site) {
        this.start_site = start_site;
    }

    public String getEnd_site() {
        return end_site;
    }

    public void setEnd_site(String end_site) {
        this.end_site = end_site;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}