package com.thoughtworks.pos.domains;

import java.util.List;

/**
 * Created by Administrator on 2014/12/31.
 */
public class Report{
    private List<ItemGroup> itemGroupies;

    public Report(List<ItemGroup> itemGroupies){ this.itemGroupies = itemGroupies; }

    public List<ItemGroup> getItemGroupies() { return itemGroupies; }

    public double getTotal(Vip user){
        double result = 0.00;
        for (ItemGroup itemGroup : itemGroupies) {
            if (itemGroup.promotion() == true)
                result -= itemGroup.groupPrice();
            result += itemGroup.subTotal(user);
        }
        return result;
    }

    public double getSaving(Vip user){
        double result = 0.00;
        for (ItemGroup itemGroup : itemGroupies) {
            if(itemGroup.promotion() == true)
                result += itemGroup.groupPrice();
            result += itemGroup.saving(user);
        }
        return result;
    }

    public boolean getPromotion(){
        for (ItemGroup itemGroup : itemGroupies)
            if (itemGroup.promotion() == true)
                return true;
        return false;
    }
}
