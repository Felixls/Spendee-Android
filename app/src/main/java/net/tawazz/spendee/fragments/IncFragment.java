package net.tawazz.spendee.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.tawazz.spendee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncFragment extends ViewsFragment {


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
        return inflater.inflate(R.layout.fragment_inc, container, false);
    }

}
