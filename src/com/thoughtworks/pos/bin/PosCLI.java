package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.*;
import com.thoughtworks.pos.domains.*;
import com.thoughtworks.pos.services.services.*;

import java.io.*;

/**
 * Created by Administrator on 2015/1/2.
 */
public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingCartException {
        InputParser inputParser = new InputParser(new File(args[0]), new File(args[1]));
        ShoppingChart shoppingChart = inputParser.parser();

        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart);
        System.out.print(shoppingList);
    }
}
