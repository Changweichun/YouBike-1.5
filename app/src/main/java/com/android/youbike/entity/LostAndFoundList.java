package com.android.youbike.entity;

public class LostAndFoundList {
    private String num;
    private String name;
    private String date;
    private String site_name;
    private String category;

    public LostAndFoundList(String num, String name, String date, String site_name, String category) {
        this.num = num;
        this.name = name;
        this.date = date;
        this.site_name = site_name;
        this.category = category;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}