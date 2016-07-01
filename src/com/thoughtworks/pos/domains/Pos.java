package com.thoughtworks.pos.domains;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.services.services.ReportDataGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2014/12/28.
 */
public class Pos {
    public Pos(){ }

    public String getShoppingList(ShoppingChart shoppingChart, Vip user) throws EmptyShoppingCartException {
        Report report = new ReportDataGenerator(shoppingChart).generate();
        ArrayList<Present> PresentList = new ArrayList<Present>();
        PresentList.add(new Present("10元代金券",100));
        PresentList.add(new Present("32G容量U盘",500));
        PresentList.add(new Present("10000mA充电宝",1000));
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        StringBuilder shoppingListBuilder = new StringBuilder()
                .append("***商店购物清单***\n")
                .append("姓名：").append(user.getName());
                if(user.getVip() == true)
                    shoppingListBuilder.append("     会员编号：").append(user.getBarcode()).append("     会员积分：").append(user.getPoint()).append("分");
                shoppingListBuilder.append("\n打印时间：").append(df.format(new Date())).append("\n")
                .append("----------------------\n");
        for (ItemGroup itemGroup : report.getItemGroupies()) {
            shoppingListBuilder.append(
                    new StringBuilder()
                            .append("名称：").append(itemGroup.groupName()).append("，")
                            .append("数量：").append(itemGroup.groupSize()).append(itemGroup.groupUnit()).append("，")
                            .append("单价：").append(String.format("%.2f", itemGroup.groupPrice())).append("(元)").append("，"));
            if(itemGroup.groupVipDiscount() == 1.00 && itemGroup.promotion() == true)
                shoppingListBuilder.append(
                        new StringBuilder()
                            .append("小计：").append(String.format("%.2f", itemGroup.subTotal(user) - itemGroup.groupPrice())).append("(元)").append("\n")
                            .toString());
            else shoppingListBuilder.append(
                    new StringBuilder()
                            .append("小计：").append(String.format("%.2f", itemGroup.subTotal(user))).append("(元)").append("\n")
                            .toString());
        }
        StringBuilder subStringBuilder = shoppingListBuilder;
        if (report.getPromotion() == true) {
            subStringBuilder
                    .append("----------------------\n")
                    .append("挥泪赠送商品：\n");
            for (ItemGroup itemGroup : report.getItemGroupies())
                if (itemGroup.promotion() == true && itemGroup.groupVipDiscount() == 1.00)
                    subStringBuilder.append(
                            new StringBuilder()
                                    .append("名称：").append(itemGroup.groupName()).append("，")
                                    .append("数量：1").append(itemGroup.groupUnit()).append("\n"));
        }
        subStringBuilder
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", report.getTotal(user))).append("(元)").append("\n");
        if ( report.getSaving(user) != 0)
            subStringBuilder
                    .append("节省：").append(String.format("%.2f", report.getSaving(user))).append("(元)").append("\n");
        if(user.getVip() == true) {
            if ( user.getPoint() >= 0 && user.getPoint() <= 200) {
                subStringBuilder
                        .append("获得积分：").append(String.format("%d", (int) report.getTotal(user) / 5)).append("分").append("\n");
                user.setPoint(user.getPoint() + (int) report.getTotal(user) / 5);
            }
            else if (user.getPoint() > 200 && user.getPoint() <= 500) {
                subStringBuilder
                        .append("获得积分：").append(String.format("%d", (int) report.getTotal(user) / 5 * 3)).append("分").append("\n");
                user.setPoint(user.getPoint() + (int) report.getTotal(user) / 5 * 3);
            }
            else if (user.getPoint() > 500) {
                subStringBuilder
                        .append("获得积分：").append(String.format("%d", (int) report.getTotal(user) / 5 * 5)).append("分").append("\n");
                user.setPoint(user.getPoint() + (int) report.getTotal(user) / 5 * 5);
            }
            subStringBuilder
                    .append("----------------------\n")
                    .append("积分可换取以下物品：\n");
            for(Present p : PresentList)
            {
                if(p.getPoint() <= user.getPoint())
                    subStringBuilder
                            .append(p.getName()).append("：").append(p.getPoint()).append("分").append("\n");
            }
        }
        return subStringBuilder
                    .append("**********************\n")
                    .toString();
    }
}