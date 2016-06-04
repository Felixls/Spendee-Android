package net.tawazz.spendee.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by tnyak on 4/06/2016.
 */
public class ViewsFragment extends Fragment {

    protected onCreateViewListener onCreateViewCallback;

    public void setOnCreateViewListener(onCreateViewListener listener) {
        this.onCreateViewCallback = listener;
    }

    public interface onCreateViewListener {
        void onFragmentCreateView();
    }
}
