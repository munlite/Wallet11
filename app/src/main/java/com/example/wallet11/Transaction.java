package com.example.wallet11;

import java.util.Date;

public class Transaction {
    private int ID;
    private int Amount;
    private String Currency;
    private String Comment;
    private Date Date;
    private int ID_Wallet_First;
    private int ID_Wallet_Second;

    public int getID_Wallet_First() {
        return ID_Wallet_First;
    }

    public void setID_Wallet_First(int ID_Wallet_First) {
        this.ID_Wallet_First = ID_Wallet_First;
    }

    public int getID_Wallet_Second() {
        return ID_Wallet_Second;
    }

    public void setID_Wallet_Second(int ID_Wallet_Second) {
        this.ID_Wallet_Second = ID_Wallet_Second;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }


}
