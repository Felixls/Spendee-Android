package net.tawazz.spendee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import net.tawazz.spendee.AppData.Tags;
import net.tawazz.spendee.R;
import net.tawazz.spendee.fragments.AddFragment;

import java.util.ArrayList;

/**
 * Created by tawanda on 11/06/2016.
 */
public class TagsAdapter extends ArrayAdapter<Tags> {

    public TagsAdapter(Context context, ArrayList<Tags> objects) {
        super(context, R.layout.tags, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tagsView = convertView;
        Tags tag = getItem(position);

        ViewHolder vh = null;
        if(tagsView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            tagsView = inflater.inflate(R.layout.tags, parent, false);

            vh = new ViewHolder();
            vh.icon = (IconTextView) tagsView.findViewById(R.id.tags_icon);
            vh.tagName = (TextView) tagsView.findViewById(R.id.tag_name);

            tagsView.setTag(vh);

        }else{
            vh = (ViewHolder) tagsView.getTag();
        }

        vh.icon.setText(tag.getTagIcon());
        vh.tagName.setText(tag.getTagName());


        return tagsView;
    }

    @Override
    public Tags getItem(int position) {
        return super.getItem(position);
    }

    public static class ViewHolder{
        IconTextView icon;
        TextView tagName;

    }
}
