package com.android.youbike.entity;

import java.io.Serializable;

public class RecordBean implements Serializable {
    private String title;
    private String type;
    private String content;
    private String date;
    private String time;
    private String name;
    private String address;
    private String phone;
    private String card;

    public RecordBean(String title, String type, String content, String date, String time, String address, String name, String phone, String card) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.date = date;
        this.time = time;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.card = card;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

}
