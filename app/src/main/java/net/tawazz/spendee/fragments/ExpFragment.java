package net.tawazz.spendee.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.tawazz.spendee.AppData.ExpData;
import net.tawazz.spendee.R;
import net.tawazz.spendee.adapters.ExpAdapter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class ExpFragment extends ViewsFragment {
    private RecyclerView listView;
    private SwipeRefreshLayout refreshLayout;
    private TextView errorView;
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
        view = inflater.inflate(R.layout.fragment_custom, container, false);
        listView = (RecyclerView) view.findViewById(R.id.exp_list);
        errorView = (TextView) view.findViewById(R.id.error);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        init();

        if (onCreateViewCallback != null) {
            this.onCreateViewCallback.onFragmentCreateView();
            refreshLayout.setRefreshing(true);
            this.onCreateViewCallback.onRefresh();
            refreshLayout.setRefreshing(true);
        }

        return view;
    }

    private void init() {

        expAdapter = new ExpAdapter(new ArrayList<ExpData>());
        listView.setAdapter(expAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onCreateViewCallback != null) {
                    onCreateViewCallback.onRefresh();
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public void setExpenses(ArrayList<ExpData> expenses) {
        if (expenses.isEmpty()) {
            listView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        } else {
            errorView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            expAdapter = new ExpAdapter(expenses);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(expAdapter);
            listView.setAdapter(alphaAdapter);
            listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }




    }

    public void refresh(){
        refreshLayout.setRefreshing(true);
        onCreateViewCallback.onRefresh();
        refreshLayout.setRefreshing(false);
    }

}
