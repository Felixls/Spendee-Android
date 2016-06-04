package net.tawazz.spendee.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.tawazz.spendee.AppData.Items;
import net.tawazz.spendee.R;
import net.tawazz.spendee.adapters.ExpAdapter;

import java.util.ArrayList;

public class ExpFragment extends ViewsFragment {
    private RecyclerView listView;
    private ExpAdapter expAdapter;
    private View view;

    public ExpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exp, container, false);
        listView = (RecyclerView) view.findViewById(R.id.exp_list);

        init();

        if (onCreateViewCallback != null) {
            this.onCreateViewCallback.onFragmentCreateView();
        }

        return view;
    }

    private void init() {


    }

    public void setExpenses(ArrayList<Items> expenses) {
        expAdapter = new ExpAdapter(expenses);
        listView.setAdapter(expAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

}
