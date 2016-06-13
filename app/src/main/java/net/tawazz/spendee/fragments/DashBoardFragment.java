package net.tawazz.spendee.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import net.tawazz.spendee.AppData.AppData;
import net.tawazz.spendee.R;
import net.tawazz.spendee.helpers.Sync;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends ViewsFragment {


    @BindView(R.id.expenses_graph)
    WebView expensesGraph;
    @BindView(R.id.year_overview_graph)
    WebView yearOverviewGraph;
    @BindView(R.id.incomes_graph)
    WebView incomesGraph;
    @BindView(R.id.tags_graph)
    WebView tagsGraph;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private View view;
    private AppData appData;
    private Calendar date;

    public DashBoardFragment() {
        date = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (onCreateViewCallback != null) {
            this.onCreateViewCallback.onFragmentCreateView();
        }
        view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        appData = (AppData) getActivity().getApplication();
        yearOverviewGraph.getSettings().setJavaScriptEnabled(true);
        expensesGraph.getSettings().setJavaScriptEnabled(true);
        incomesGraph.getSettings().setJavaScriptEnabled(true);
        tagsGraph.getSettings().setJavaScriptEnabled(true);


        yearOverviewGraph.setWebViewClient(new WebViewClient());
        incomesGraph.setWebChromeClient(new WebChromeClient());
        tagsGraph.setWebChromeClient(new WebChromeClient());
        expensesGraph.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        yearOverviewGraph.loadUrl(Sync.YEAR_OVERVIEW_GRAPH_URL + "/" + appData.user.getUserId() + "/" + date.get(Calendar.YEAR));
        expensesGraph.loadUrl(Sync.EXPENSES_GRAPH_URL + "/" + appData.user.getUserId() + "/" + date.get(Calendar.YEAR));
        incomesGraph.loadUrl(Sync.INCOMES_GRAPH_URL + "/" + appData.user.getUserId() + "/" + date.get(Calendar.YEAR));
        tagsGraph.loadUrl(Sync.TAGS_GRAPH_URL + "/" + appData.user.getUserId() + "/" + date.get(Calendar.YEAR));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onCreateViewCallback != null) {
                    onCreateViewCallback.onRefresh();
                    refresh.setRefreshing(false);
                }
            }
        });
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void refresh(){
        yearOverviewGraph.reload();
        expensesGraph.reload();
        incomesGraph.reload();
        tagsGraph.reload();
    }
}
