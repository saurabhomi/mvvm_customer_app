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
import com.ecocustomerapp.data.model.api.TerminalData;

import java.util.ArrayList;
import java.util.List;

public class TerminalAutoCompleteAdapter extends ArrayAdapter<TerminalData> {
    private final Context context;
    private final int resourceId;
    private final List<TerminalData> items;
    private final List<TerminalData> tempItems;
    private final List<TerminalData> suggestions;
    private OnEditingListener listener;

    public TerminalAutoCompleteAdapter(@NonNull Context context, int resourceId, List<TerminalData> items, OnEditingListener listener) {
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
            TerminalData terminal = getItem(position);
            TextView name = view.findViewById(R.id.text);
            String first = terminal.getName();
            String next = "<font color=\"#ec3137\"> | </font>";
            String last = terminal.getTerminalName();
            name.setText(Html.fromHtml(first + next + last));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public TerminalData getItem(int position) {
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
            TerminalData terminal = (TerminalData) resultValue;
            String first = terminal.getName();
            String next = "<font color=\"#ec3137\"> | </font>";
            String last = terminal.getTerminalName();
            return Html.fromHtml(first + next + last);
        }


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (TerminalData terminal : tempItems) {
                    if (terminal.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
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
            ArrayList<TerminalData> tempValues = (ArrayList<TerminalData>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (TerminalData terminal : tempValues) {
                    add(terminal);
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