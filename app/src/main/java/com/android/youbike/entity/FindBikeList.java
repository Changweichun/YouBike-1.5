package com.android.youbike.entity;

public class FindBikeList {
    private String num;
    private String bikeNum;
    private String phone;

    public FindBikeList(String num, String bikeNum, String phone) {
        this.num = num;
        this.bikeNum = bikeNum;
        this.phone = phone;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBikeNum() {
        return bikeNum;
    }

    public void setBikeNum(String bikeNum) {
        this.bikeNum = bikeNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}