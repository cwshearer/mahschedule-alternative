package se.k3.isak.mahschedule.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

    ArrayList<String> mList;
    LayoutInflater layoutInflater;

    public CustomBaseAdapter(Activity mActivity, ArrayList<String> mList) {
        this.mList = mList;
        this.layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class ViewHolder {
        public TextView mTextItem;
        public ViewHolder(View base) {
           mTextItem = (TextView) base.findViewById(R.id.search_list_item);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
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
        holder.mTextItem.setText(mList.get(position));
        return v;
    }
}
