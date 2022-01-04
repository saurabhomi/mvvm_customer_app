package com.ecocustomerapp.ui.fragments.main;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Data;

import java.util.ArrayList;
import java.util.List;

public class CityAutoCompleteAdapter extends ArrayAdapter<Data> {

    private final Context context;
    private final int resourceId;
    private final List<Data> items;
    private final List<Data> tempItems;
    private final List<Data> suggestions;
    private OnEditingListener listener;

    public CityAutoCompleteAdapter(@NonNull Context context, int resourceId, List<Data> items, OnEditingListener listener) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(resourceId, parent, false);
            }
            Data data = getItem(position);
            TextView name = view.findViewById(R.id.text);
            String first = data.getCityGeoName();
            name.setText(first);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return DataFilter;
    }

    private final Filter DataFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Data data = (Data) resultValue;
            String first = data.getCityGeoName();
            return first;
        }


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Data terminal : tempItems) {
                    if (terminal.getCityGeoName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(terminal);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Data> tempValues = (ArrayList<Data>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Data data : tempValues) {
                    add(data);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

    public interface OnEditingListener {
        void onEdit();
    }
}