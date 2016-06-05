package net.tawazz.spendee.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tnyak on 4/06/2016.
 */
public class Items {
    public static final String EXPENSE = "EXPENSE";
    public static final String INCOME = "INCOME";
    public static final String ITEM_NAME = "NAME";
    public static final String ITEM_AMOUNT = "AMOUNT";
    public static final String ITEM_DATE = "DATE";
    public static final String ITEM_TAGS = "TAGS";
    private String itemName;
    private float amount;
    private ArrayList<String> tags;
    private Date date;
    private String type;

    public Items(String name, float amnt, ArrayList<String> tags, Date D, String type) {
        itemName = name;
        amount = amnt;
        this.tags = tags;
        date = D;
        this.type = type;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();

        try {
            obj.put(ITEM_NAME, itemName);
            obj.put(ITEM_AMOUNT, amount);
            obj.put(ITEM_DATE, date.toString());
            JSONArray tagsArray = new JSONArray();
            tagsArray.put(tags);
            obj.put(ITEM_TAGS, tagsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
