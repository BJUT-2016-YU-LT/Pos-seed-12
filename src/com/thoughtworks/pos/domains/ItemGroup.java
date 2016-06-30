package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by Administrator on 2014/12/31.
 */
public class ItemGroup {
    private List<Item> items;

    public ItemGroup(List<Item> items) { this.items = items; }

    public String groupName() { return items.get(0).getName(); }

    public int groupSize() { return items.size(); }

    public String groupUnit() { return items.get(0).getUnit(); }

    public double groupPrice() { return items.get(0).getPrice(); }

    public double groupVipDiscount(){ return items.get(0).getVipDiscount(); }

    public double subTotal(Vip user) {
        double result = 0.00;
        for (Item item : items)
            result += item.getPrice() * ( item.getDiscount() * (user.getVip() == true ? item.getVipDiscount() : 1.00) );
        return result;
    }

    public double saving(Vip user) {
        double result = 0.00;
        for (Item item : items)
            result += item.getPrice() * ( 1.00 - ( item.getDiscount() * (user.getVip() == true ? item.getVipDiscount() : 1.00) ) );
        return result;
    }

    public boolean promotion(){
        if (items.get(0).getPromotion()==true && items.size() > 2 && items.get(0).getVipDiscount() == 1.00)
            return true;
        return false;
    }
}
