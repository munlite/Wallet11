package com.example.wallet11;

public class Wallet {
    private String Name;
    private String Currency;
    private int Amount;

    public Wallet( String name, String currency, int amount ) {
        Name = name;
        Currency = currency;
        Amount = amount;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }




}
