package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.domains.*;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
/**
 * Created by Administrator on 2015/1/2.
 */
public class InputParser {
    private File indexFile;
    private File itemsFile;
    private File vipFile;
    private final ObjectMapper objectMapper;

    public InputParser(File indexFile, File itemsFile, File vipFile) {
        this.indexFile = indexFile;
        this.itemsFile = itemsFile;
        this.vipFile = vipFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException { return BuildShoppingChart(getBoughtItemBarCodes(),getItemIndexes()); }

    private ShoppingChart BuildShoppingChart(String[] barCodes, HashMap<String, Item> itemIndexes) {
        ShoppingChart shoppingChart = new ShoppingChart();
        for (String barcode : barCodes) {
            Item mappedItem = itemIndexes.get(barcode);
            Item item = new Item(barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), mappedItem.getDiscount(), mappedItem.getPromotion(), mappedItem.getVipDiscount());
            shoppingChart.add(item);
        }
        return shoppingChart;
    }

    private String[] getBoughtItemBarCodes() throws IOException {
        String User = FileUtils.readFileToString(itemsFile);
        JSONObject jsonObject = JSONObject.fromObject(User);
        return objectMapper.readValue(jsonObject.getJSONArray("items").toString(), String[].class);
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(indexFile);
        TypeReference<HashMap<String,Item>> typeRef = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }

    public Vip BuildVip() throws IOException {
        Vip vip;
        String VipListStr = FileUtils.readFileToString(vipFile);
        String User = FileUtils.readFileToString(itemsFile);
        JSONObject user = JSONObject.fromObject(User);
        JSONObject viplist = JSONObject.fromObject(VipListStr);
        User = user.get("user").toString();
        viplist = viplist.getJSONObject(User);
        if(viplist.isEmpty())
            vip = new Vip("Unknown","UnknownUser", 0, false);
        else vip = new Vip(User,viplist.get("name").toString(), Integer.parseInt(viplist.get("point").toString()), Boolean.valueOf(viplist.get("isVip").toString()));
        return vip;
    }

}
