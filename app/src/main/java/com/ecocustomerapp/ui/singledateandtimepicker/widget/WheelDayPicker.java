package com.ecocustomerapp.ui.singledateandtimepicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;


import com.ecocustomerapp.R;
import com.ecocustomerapp.ui.singledateandtimepicker.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.ecocustomerapp.ui.singledateandtimepicker.widget.SingleDateAndTimeConstants.DAYS_PADDING;

public class WheelDayPicker extends WheelPicker<String> {

    private static final String DAY_FORMAT_PATTERN = "EEE d MMM";

    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat customDateFormat;

    private OnDaySelectedListener onDaySelectedListener;

    public WheelDayPicker(Context context) {
        super(context);
    }

    public WheelDayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        simpleDateFormat = new SimpleDateFormat(DAY_FORMAT_PATTERN, getCurrentLocale());
        simpleDateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
    }

    @Override
    public void setCustomLocale(Locale customLocale) {
        super.setCustomLocale(customLocale);
        simpleDateFormat = new SimpleDateFormat(DAY_FORMAT_PATTERN, getCurrentLocale());
        simpleDateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
    }

    @Override
    protected String initDefault() {
        return getTodayText();
    }

    @NonNull
    private String getTodayText() {
        return getLocalizedString(R.string.picker_today);
    }

    public void setTodayText(String todayText) {
        int index = adapter.getData().indexOf(getTodayText());
        if (index != -1) {
            adapter.getData().set(index, todayText);
            notifyDatasetChanged();
        }
    }

    @Override
    protected void onItemSelected(int position, String item) {
        if (onDaySelectedListener != null) {
            final Date date = convertItemToDate(position);
            onDaySelectedListener.onDaySelected(this, position, item, date);
        }
    }

    @Override
    protected List<String> generateAdapterValues() {
        final List<String> days = new ArrayList<>();

        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(Calendar.getInstance().getTimeZone());
        instance.add(Calendar.DATE, -1 * DAYS_PADDING - 1);
        for (int i = (-1) * DAYS_PADDING; i < 0; ++i) {
            instance.add(Calendar.DAY_OF_MONTH, 1);
            days.add(getFormattedValue(instance.getTime()));
        }

        //today
        days.add(getTodayText());

        instance = Calendar.getInstance();
        instance.setTimeZone(Calendar.getInstance().getTimeZone());

        for (int i = 0; i < DAYS_PADDING; ++i) {
            instance.add(Calendar.DATE, 1);
            days.add(getFormattedValue(instance.getTime()));
        }

        return days;
    }

    protected String getFormattedValue(Object value) {
        return getDateFormat().format(value);
    }

    public WheelDayPicker setDayFormatter(SimpleDateFormat simpleDateFormat) {
        simpleDateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        this.customDateFormat = simpleDateFormat;
        updateAdapter();
        return this;
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    public Date getCurrentDate() {
        return convertItemToDate(super.getCurrentItemPosition());
    }

    private SimpleDateFormat getDateFormat() {
        if (customDateFormat != null) {
            return customDateFormat;
        }
        return simpleDateFormat;
    }

    private Date convertItemToDate(int itemPosition) {
        Date date = null;
        final String itemText = adapter.getItemText(itemPosition);
        final Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTimeZone(Calendar.getInstance().getTimeZone());

        final int todayPosition = adapter.getData().indexOf(getTodayText());

        if (getTodayText().equals(itemText)) {
            date = todayCalendar.getTime();
        } else {
            try {
                date = getDateFormat().parse(itemText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (date != null) {
            //try to know the year
            final Calendar dateCalendar = DateHelper.getCalendarOfDate(date);

            todayCalendar.add(Calendar.DATE, (itemPosition - todayPosition));

            dateCalendar.set(Calendar.YEAR, todayCalendar.get(Calendar.YEAR));
            date = dateCalendar.getTime();
        }

        return date;
    }

    public interface OnDaySelectedListener {
        void onDaySelected(WheelDayPicker picker, int position, String name, Date date);
    }
}