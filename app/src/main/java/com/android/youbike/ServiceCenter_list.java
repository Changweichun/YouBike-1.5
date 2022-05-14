package com.android.youbike;

public class ServiceCenter_list {
    private String title, address, content, phone;
    public ServiceCenter_list(String title, String address, String content, String phone){
        this.title = title;
        this.address=address;
        this.content = content;
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
