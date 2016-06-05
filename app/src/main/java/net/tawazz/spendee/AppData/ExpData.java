package net.tawazz.spendee.AppData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tnyak on 4/06/2016.
 */
public class ExpData {

    private Date date;
    private ArrayList<Items> expenses;
    private float total;

    public ExpData(Date date, ArrayList<Items> expenses) {
        this.date = date;
        this.expenses = expenses;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        float sum = 0;
        for (Items item : expenses) {
            sum += item.getAmount();
        }
        return sum;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ArrayList<Items> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Items> expenses) {
        this.expenses = expenses;
    }
}
