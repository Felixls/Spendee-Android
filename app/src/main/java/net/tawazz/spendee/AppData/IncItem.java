package net.tawazz.spendee.AppData;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tnyak on 4/06/2016.
 */
public class IncItem extends Items {
    public IncItem(String name, float amnt, ArrayList<String> tags, Date D) {
        super(name, amnt, tags, D, Items.INCOME);
    }
}
