package com.ecocustomerapp.ui.fragments.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Data;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private final Context mContext;
    private int id;
    private final String hint;
    List<Data> countries;
    private OnItemClickListener onItemClickListener;


    public CountryAdapter(List<Data> countries, Context context, String hint) {
        countries.add(0, new Data().setName("Select airport/terminal"));
        this.id = id;
        this.mContext = context;
        this.countries = countries;
        this.hint = hint;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int i) {
        return countries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getSelectedView(parent, position);
    }

    private View getSelectedView(ViewGroup parent, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_no_selection, parent, false);
        TextView t = view.findViewById(R.id.txt);
        t.setText(countries.get(position).getName());
        return view;
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_list_item, null);
            holder = new ViewHolder();
            holder.mTextView = convertView.findViewById(R.id.text);
            holder.root_f = convertView.findViewById(R.id.root);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.mTextView.setText(countries.get(position).getName());


        return convertView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {


        void onItemClick(View view, String state);


    }

    private class ViewHolder {
        private TextView mTextView;
        private LinearLayout root_f;
    }


}