package net.tawazz.spendee.AppData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tnyak on 4/06/2016.
 */
public class IncData {

    private Date date;
    private ArrayList<Items> incomes;
    private float total;

    public IncData(Date date, ArrayList<Items> incomes) {
        this.date = date;
        this.incomes = incomes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        float sum = 0;
        for (Items item : incomes) {
            sum += item.getAmount();
        }
        return sum;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ArrayList<Items> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<Items> incomes) {
        this.incomes = incomes;
    }
}
