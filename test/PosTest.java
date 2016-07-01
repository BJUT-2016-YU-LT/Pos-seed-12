import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2014/12/28.
 */
public class PosTest {
    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    @Test
    public void testGetCorrectShoppingListForSingleItem() throws Exception {
        // given
        Item cokeCola = new Item("ITEM000000", "可口可乐", "瓶", 3.00, false);
        Vip vip = new Vip("USER0001","张三", 200, true);
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(cokeCola);

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart,vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        +"打印时间：" + df.format(new Date())
                        +"\n----------------------\n"
                        + "名称：可口可乐，数量：1瓶，单价：3.00(元)，小计：3.00(元)\n"
                        + "----------------------\n"
                        + "总计：3.00(元)\n"
                        + "获得积分：0分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testGetCorrectShoppingListForMultipleItemsWithSameType() throws Exception {
        // given
        Vip vip = new Vip("USER0001","张三", 200, true);
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00, true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00, true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00, true));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart,vip);


        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        +"打印时间：" + df.format(new Date())
                        +"\n----------------------\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        +"名称：可口可乐，数量：1瓶\n"
                        +"----------------------\n"
                        + "总计：6.00(元)\n"
                        + "节省：3.00(元)\n"
                        + "获得积分：1分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testGetCorrectShoppingListForMultipleItemsWithMultipleTypes() throws Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 200, true);
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, false));
        shoppingChart.add(new Item("ITEM000001", "可口可乐", "瓶", 3.00, false));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        + "打印时间：" +  df.format(new Date())
                        + "\n----------------------\n"
                        + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：2.00(元)\n"
                        + "名称：可口可乐，数量：1瓶，单价：3.00(元)，小计：3.00(元)\n"
                        + "----------------------\n"
                        + "总计：5.00(元)\n"
                        + "获得积分：1分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testGetCorrectShoppingListWhenHavingPromotionAndVipDiscount() throws Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 200, true);
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, true, 0.9));
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, true, 0.9));
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, true, 0.9));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        + "打印时间：" +  df.format(new Date())
                        + "\n----------------------\n"
                        + "名称：雪碧，数量：3瓶，单价：2.00(元)，小计：5.40(元)\n"
                        + "----------------------\n"
                        + "总计：5.40(元)\n"
                        + "节省：0.60(元)\n"
                        + "获得积分：1分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testGetCorrectShoppingListWhenHavingDiscountAndVipDiscount() throws Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 200, true);
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, 0.8, false, 0.9));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        + "打印时间：" +  df.format(new Date())
                        + "\n----------------------\n"
                        + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：1.44(元)\n"
                        + "----------------------\n"
                        + "总计：1.44(元)\n"
                        + "节省：0.56(元)\n"
                        + "获得积分：0分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testGetCorrectShoppingListWhenIsNotVip() throws Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 0, false);
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, 0.8, false, 0.9));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三\n"
                        + "打印时间：" +  df.format(new Date())
                        + "\n----------------------\n"
                        + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：1.60(元)\n"
                        + "----------------------\n"
                        + "总计：1.60(元)\n"
                        + "节省：0.40(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testGetCorrectShoppingListWhenDifferentItemHaveSameItemName() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 200, true);
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, true));
        shoppingChart.add(new Item("ITEM000002", "雪碧", "瓶", 3.00, true));
        shoppingChart.add(new Item("ITEM000003", "雪碧", "瓶", 4.00, true));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        +"打印时间：" + df.format(new Date())
                        +"\n----------------------\n"
                        + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：2.00(元)\n"
                        + "名称：雪碧，数量：1瓶，单价：3.00(元)，小计：3.00(元)\n"
                        + "名称：雪碧，数量：1瓶，单价：4.00(元)，小计：4.00(元)\n"
                        + "----------------------\n"
                        + "总计：9.00(元)\n"
                        + "节省：0.40(元)\n"
                        + "获得积分：1分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test(expected = EmptyShoppingCartException.class)
    public void testThrowExceptionWhenNoItemsInShoppingCart() throws EmptyShoppingCartException{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 200, true);

        // when
        Pos pos = new Pos();
        pos.getShoppingList(shoppingChart,vip);
    }
    @Test
    public void testShouldSupportDiscountWhenHavingOneFavourableItem() throws EmptyShoppingCartException {
        // given
        ShoppingChart shoppingChart = new ShoppingChart();

        Vip vip = new Vip("USER0001","张三", 200, true);
        shoppingChart.add(new Item("ITEM000000", "雪碧", "瓶", 2.00, 0.8, false));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        +"打印时间：" + df.format(new Date())
                        +"\n----------------------\n"
                        + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：1.60(元)\n"
                        + "----------------------\n"
                        + "总计：1.60(元)\n"
                        + "节省：0.40(元)\n"
                        + "获得积分：0分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testShouldSupportDiscountWhenHavingFavourableItemss() throws Exception {
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        Vip vip = new Vip("USER0001","张三", 200, true);
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,1, true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,1, true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,1, true));
        shoppingChart.add(new Item("ITEM000001", "雪碧", "瓶", 2.00, 0.8, false));
        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart, vip);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "姓名：张三     会员编号：USER0001     会员积分：200分\n"
                        +"打印时间：" + df.format(new Date())
                        +"\n----------------------\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：1.60(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        +"名称：可口可乐，数量：1瓶\n"
                        +"----------------------\n"
                        + "总计：7.60(元)\n"
                        + "节省：3.40(元)\n"
                        + "获得积分：1分\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}