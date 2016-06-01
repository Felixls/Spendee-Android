package net.tawazz.spendee.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.tawazz.spendee.R;

public class ExpFragment extends Fragment {
    private TextView textView;
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
        textView = (TextView) view.findViewById(R.id.textView);

        return view;
    }

    public void setText(String text) {
        textView.setText(text);
    }

}
