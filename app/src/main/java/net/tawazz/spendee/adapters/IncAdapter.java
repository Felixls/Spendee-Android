package net.tawazz.spendee.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tawazz.androidutil.Util;
import net.tawazz.spendee.AppData.IncData;
import net.tawazz.spendee.AppData.Items;
import net.tawazz.spendee.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tnyak on 4/06/2016.
 */
public class IncAdapter extends RecyclerView.Adapter<IncAdapter.Holder> {

    private Context context;
    private ArrayList<IncData> items;

    public IncAdapter(ArrayList<IncData> items) {
        this.items = items;

        Collections.sort(this.items, new Comparator<IncData>() {
            @Override
            public int compare(IncData lhs, IncData rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inc_view, parent, false);

        context = parent.getContext();
        Holder vh = new Holder(v);
        vh.itemsLayout = (LinearLayout) v.findViewById(R.id.items);
        vh.mainDate = (TextView) v.findViewById(R.id.mainDate);

        return vh;
    }

    @Override
    public void onBindViewHolder(Holder holder, int pos) {

        IncData itemData = items.get(pos);
        String sufix = Util.getDayOfMonthSuffix(itemData.getDate().getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("cccc, MMMM d'" + sufix + "' yyyy ");
        holder.mainDate.setText(sdf.format(itemData.getDate()));
        holder.itemsLayout.removeAllViews();

        for (Items item : itemData.getIncomes()) {
            holder.itemsLayout.addView(itemsLayout(item.getItemName(), item.getAmount()));
        }
        holder.itemsLayout.addView(itemsLayoutTotal("Total", itemData.getTotal()));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public LinearLayout itemsLayout(String itemName, float itemAmount, int txtColor) {

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setWeightSum(10);
        layout.setLayoutParams(params);

        TextView name = new TextView(context);
        name.setText(itemName);
        int padding = Util.dpTopx(10, context);
        name.setPadding(padding, padding, padding, padding);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 7f));
        name.setTextColor(context.getResources().getColor(txtColor));

        TextView amount = new TextView(context);
        amount.setText(Util.currencyFormat(itemAmount));
        amount.setPadding(padding, padding, padding, padding);
        amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        amount.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f));
        amount.setGravity(Gravity.RIGHT);
        amount.setTextColor(context.getResources().getColor(txtColor));

        layout.addView(name);
        layout.addView(amount);

        return layout;
    }

    public LinearLayout itemsLayoutTotal(String name, float itemAmount) {
        return itemsLayout(name, itemAmount, R.color.green);
    }

    public LinearLayout itemsLayout(String itemName, float itemAmount) {
        return itemsLayout(itemName, itemAmount, R.color.primaryText);
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public TextView mainDate;
        public LinearLayout itemsLayout;

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
