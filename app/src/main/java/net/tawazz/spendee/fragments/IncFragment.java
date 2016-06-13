package net.tawazz.spendee.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import net.tawazz.spendee.AppData.IncData;
import net.tawazz.spendee.R;
import net.tawazz.spendee.adapters.IncAdapter;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncFragment extends ViewsFragment {

    private View view;
    private RecyclerView listView;
    private IncAdapter incAdapter;
    private SwipeRefreshLayout refreshLayout;
    private TextView errorView;

    public IncFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (onCreateViewCallback != null) {
            this.onCreateViewCallback.onFragmentCreateView();
        }
        view = inflater.inflate(R.layout.fragment_custom, container, false);
        listView = (RecyclerView) view.findViewById(R.id.exp_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        errorView = (TextView) view.findViewById(R.id.error);

        init();

        if (onCreateViewCallback != null) {
            this.onCreateViewCallback.onFragmentCreateView();
        }

        return view;
    }

    private void init() {

        incAdapter = new IncAdapter(new ArrayList<IncData>());
        listView.setAdapter(incAdapter);
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


    public void setIncomes(ArrayList<IncData> incomes) {

        if (incomes.isEmpty()) {
            listView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        } else {
            errorView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            incAdapter = new IncAdapter(incomes);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(incAdapter);
            listView.setAdapter(alphaAdapter);
            listView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }
    }
}
