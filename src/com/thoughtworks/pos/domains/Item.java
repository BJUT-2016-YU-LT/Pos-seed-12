package com.thoughtworks.pos.domains;


/**
 * Created by Administrator on 2014/12/28.
 */
public class Item {
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private double discount;
    private double vipDiscount;
    private boolean promotion;

    public Item() {}

    public Item(String barcode, String name, String unit, double price, boolean promotion) {
        this.setBarcode(barcode);
        this.setName(name);
        this.setUnit(unit);
        this.setPrice(price);
        this.setPromotion(promotion);
    }

    public Item(String barcode, String name, String unit, double price,double discount, boolean promotion) {
        this(barcode, name, unit, price, promotion);
        this.setDiscount(discount);
    }

    public Item(String barcode, String name, String unit, double price,double discount, boolean promotion, double vipDiscount) {
        this(barcode, name, unit, price, discount, promotion);
        this.setVipDiscount(vipDiscount);
    }

    public Item(String barcode, String name, String unit, double price,boolean promotion, double vipDiscount) {
        this(barcode, name, unit, price,promotion);
        this.setVipDiscount(vipDiscount);
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public String getBarcode() { return barcode; }

    public double getDiscount() { return discount == 0.00? 1.00 : discount; }

    public boolean getPromotion(){
        return promotion;
    }

    public  double getVipDiscount(){ return  vipDiscount == 0.00? 1.00 : vipDiscount;}

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) { this.price = price;}

    public void setDiscount(double discount) {this.discount = discount;}

    public void setPromotion(boolean promotion){this.promotion = promotion;}

    public  void setVipDiscount(double vipDiscount){this.vipDiscount = vipDiscount;}

}
