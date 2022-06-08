package com.android.youbike.entity;

public class CardList {
    private String easyCardNum;
    private String cardType;

    public CardList(String easyCardNum, String cardType) {
        this.easyCardNum = easyCardNum;
        this.cardType = cardType;
    }

    public String getEasyCardNum() {
        return easyCardNum;
    }

    public void setEasyCardNum(String easyCardNum) {
        this.easyCardNum = easyCardNum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}