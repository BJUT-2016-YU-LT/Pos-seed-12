package com.thoughtworks.pos.domains;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.services.services.ReportDataGenerator;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Pos {
    public Pos(){
    }

    public String getShoppingList(ShoppingChart shoppingChart) throws EmptyShoppingCartException {
     double x;
        double sum=0;
        Report report = new ReportDataGenerator(shoppingChart).generate();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        StringBuilder shoppingListBuilder = new StringBuilder()
                .append("***商店购物清单***\n")
                .append("打印时间：").append(df.format(new Date())).append("\n")
                .append("----------------------\n");
        for (ItemGroup itemGroup : report.getItemGroupies()) {
            shoppingListBuilder.append(
                    new StringBuilder()

                            .append("名称：").append(itemGroup.groupName()).append("，")
                            .append("数量：").append(itemGroup.groupSize()).append(itemGroup.groupUnit()).append("，")
                            .append("单价：").append(String.format("%.2f", itemGroup.groupPrice())).append("(元)").append("，"));
            boolean promotion = itemGroup.promotion();
            if(promotion == true){
                shoppingListBuilder.append(
                        new StringBuilder()
                            .append("小计：").append(String.format("%.2f", itemGroup.subTotal()-itemGroup.groupPrice())).append("(元)").append("\n")
                            .toString());
                x=itemGroup.groupPrice();
            sum+=x;}
            else{  shoppingListBuilder.append(
                    new StringBuilder()
                            .append("小计：").append(String.format("%.2f", itemGroup.subTotal())).append("(元)").append("\n")
                            .toString());}
        }
        StringBuilder subStringBuilder = shoppingListBuilder;
        boolean promotion = report.getPromotion();
        if (promotion == true) {
            subStringBuilder
                    .append("----------------------\n")
                    .append("挥泪赠送商品：\n");
            for (ItemGroup itemGroup : report.getItemGroupies()) {
                if (itemGroup.promotion() == true)
                    subStringBuilder.append(
                            new StringBuilder()
                                    .append("名称：").append(itemGroup.groupName()).append("，")
                                    .append("数量：").append("1").append(itemGroup.groupUnit()).append("\n"));

            }
        }
            double saving = report.getSaving();
            if (saving == 0) {
                return subStringBuilder
                        .append("----------------------\n")
                        .append("总计：").append(String.format("%.2f", report.getTotal()-sum)).append("(元)").append("\n")
                        .append("**********************\n")
                        .toString();
            }
            return subStringBuilder
                    .append("----------------------\n")
                    .append("总计：").append(String.format("%.2f", report.getTotal()-sum)).append("(元)").append("\n")
                    .append("节省：").append(String.format("%.2f", saving+sum)).append("(元)").append("\n")
                    .append("**********************\n")
                    .toString();


    }
}