package ru.inno.market.model;

public enum PromoCodes {
    HAPPY_NEW_YEAR(0.1),
    FIRST_ORDER(0.2),
    VDUD(0.11),
    HAPPY_HOUR(0.05),
    LOVE_DAY(0.14);

    private double discount;

    PromoCodes(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
