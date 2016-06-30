package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.*;
import com.thoughtworks.pos.services.services.InputParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2015/1/2.
 */
public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingCartException {
        InputParser inputParser = new InputParser(new File(args[0]), new File(args[1]), new File(args[2]));
        ShoppingChart shoppingChart = inputParser.parser();
        Vip User = inputParser.BuildVip();
        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart, User);
        System.out.print(shoppingList);
    }
}
