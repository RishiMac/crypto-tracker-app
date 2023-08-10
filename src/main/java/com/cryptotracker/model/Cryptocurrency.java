package com.cryptotracker.model;

public class Cryptocurrency {
    private String symbol;
    private String name;
    private double investedAmount;

    public Cryptocurrency(String symbol, String name, double investedAmount) {
        this.symbol = symbol;
        this.name = name;
        this.investedAmount = investedAmount;
    }

    // Getters and setters

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }
}
