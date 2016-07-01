package com.thoughtworks.pos.domains;

/**
 * Created by fht_6 on 2016/6/30.
 */
public class Vip {
    private  String barcode;
    private  String name;
    private  int point;
    private  boolean isVip;

    public Vip( String barcode,String name,int point, boolean isVip) {
        this.barcode = barcode;
        this.name = name;
        this.isVip = isVip;
        this.point = point;
    }

    public String getBarcode() { return barcode; }

    public String getName(){ return  this.name;}

    public void setPoint(int point) {this.point = point; }

    public int getPoint(){ return  this.point;}

    public boolean getVip(){ return  this.isVip;}
}
