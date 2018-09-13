package com.developer.kirill.myfinance;

public class Balance {
    private int balance;
    public int totalE;
    public int totalI;
    public static Balance balanceObject = new Balance();

    public Balance() {

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int inc, int exp) {
        balance = inc - exp;
    }

    public void setTotalE(int value) {
        totalE = value;
    }

    public void setTotalI(int value) {
        totalI = value;
    }

    public int getTotalE() {
        return totalE;
    }

    public int getTotalI() {
        return totalI;
    }
}
