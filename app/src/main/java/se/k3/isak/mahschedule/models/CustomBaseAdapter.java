package se.k3.isak.mahschedule.models;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.k3.isak.mahschedule.R;

/**
 * Created by isak on 2015-04-05.
 */
public class CustomBaseAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> list;
    private LayoutInflater layoutInflater;

    public CustomBaseAdapter(Activity activity, ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class ViewHolder {
        public TextView textItem;
        public ViewHolder(View base) {
           textItem = (TextView) base.findViewById(R.id.search_list_item);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(convertView == null) {
            v = layoutInflater.inflate(R.layout.search_list_item, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.textItem.setText(list.get(position));
        return v;
    }
}
