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
import net.tawazz.spendee.AppData.ExpData;
import net.tawazz.spendee.AppData.Items;
import net.tawazz.spendee.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by tnyak on 4/06/2016.
 */
public class ExpAdapter extends RecyclerView.Adapter<ExpAdapter.Holder> {

    private Context context;
    private ArrayList<ExpData> items;

    public ExpAdapter(ArrayList<ExpData> items) {
        this.items = items;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exp_view, parent, false);

        context = parent.getContext();
        Holder vh = new Holder(v);
        vh.itemsLayout = (LinearLayout) v.findViewById(R.id.items);
        vh.mainDate = (TextView) v.findViewById(R.id.mainDate);

        return vh;
    }

    @Override
    public void onBindViewHolder(Holder holder, int pos) {

        ExpData itemData = items.get(pos);
        SimpleDateFormat sdf = new SimpleDateFormat("cccc, MMMM d yyyy ");
        holder.mainDate.setText(sdf.format(itemData.getDate()));
        holder.itemsLayout.removeAllViews();

        for(Items item: itemData.getExpenses()) {
            holder.itemsLayout.addView(itemsLayout(item.getItemName(), item.getAmount()));
        }
        holder.itemsLayout.addView(itemsLayout("Total",itemData.getTotal()));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public LinearLayout itemsLayout(String itemName, float itemAmount) {

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

        TextView amount = new TextView(context);
        amount.setText(Util.currencyFormat(itemAmount));
        amount.setPadding(padding, padding, padding, padding);
        amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        amount.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3f));
        amount.setGravity(Gravity.RIGHT);

        layout.addView(name);
        layout.addView(amount);

        return layout;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public TextView mainDate;
        public LinearLayout itemsLayout;

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
