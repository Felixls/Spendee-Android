package net.tawazz.spendee.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import net.tawazz.spendee.AppData.Tags;
import net.tawazz.spendee.R;
import net.tawazz.spendee.adapters.TagsAdapter;
import net.tawazz.spendee.helpers.AppHelper;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static net.tawazz.spendee.helpers.AppHelper.MONTHS;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    @BindView(R.id.tags_list)
    ListView tagsList;

    private View view;
    private int position;
    private static AddFragment instance;
    private TagSelectedListener tagSelectedListener;

    private ArrayList<Tags> selectedTags;

    public interface TagSelectedListener {

        public void onItemSelected(ArrayList<Tags> tags);

    }

    public void setPosition(int position) {
        this.position = position;
    }

    public enum addType {
        EXPENSE, INCOME
    }

    public AddFragment() {
        selectedTags = new ArrayList<>();
    }

    public static AddFragment getInstance(int pos) {

        instance = new AddFragment();
        instance.setPosition(pos);

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {

        if(position == addType.EXPENSE.ordinal()) {
            ArrayList<Tags> tags = AppHelper.getExpTags();

            TagsAdapter tagsAdapter = new TagsAdapter(this.getContext(), tags);
            tagsList.setAdapter(tagsAdapter);
            tagsList.setOnItemClickListener(itemClickedListener(tags));
        }else  if(position == addType.INCOME.ordinal()){
            final ArrayList<Tags> tags = AppHelper.getIncTags();
            TagsAdapter tagsAdapter = new TagsAdapter(this.getContext(), tags);
            tagsList.setAdapter(tagsAdapter);
            tagsList.setOnItemClickListener(itemClickedListener(tags));
        }


    }

    private AdapterView.OnItemClickListener itemClickedListener(final ArrayList<Tags> tags ){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tags tag = tags.get(position);
                if(selectedTags.contains(tag)){
                    selectedTags.remove(tag);
                }else{
                    selectedTags.add(tag);
                }

                tagSelectedListener.onItemSelected(selectedTags);
            }
        };
    }
    public void setTagSelectedListener(TagSelectedListener tagSelectedListener) {
        this.tagSelectedListener = tagSelectedListener;
    }
}
