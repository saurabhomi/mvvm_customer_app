package com.ecocustomerapp.ui.fragments.main.airport;

import static com.ecocustomerapp.utils.AppConstants.AIRPORT;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.databinding.ObservableField;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.TerminalData;
import com.ecocustomerapp.databinding.FragmentAirportBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.car.CarFragment;
import com.ecocustomerapp.ui.fragments.main.TerminalAutoCompleteAdapter;
import com.ecocustomerapp.ui.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class AirportViewModel extends BaseViewModel<AirportNavigator, FragmentAirportBinding> implements TerminalAutoCompleteAdapter.OnEditingListener {

    public ObservableField<String> drop = new ObservableField<>();

    private int check = 0;
    private String dateTime;

    public AirportViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public void getEntity(Context context) {

        getBinding().spinnerEntity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string = getBinding().spinnerEntity.getSelectedItem().toString();
                getBinding().edtEntity.setText(string);
                getBlockTime(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        getCompositeDisposable()
                .add(getDataManager()
                        .getAllEntity()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(entities -> {
                            Spinner spinner = getBinding().spinnerEntity;
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (context, R.layout.simple_spinner_layout, entities.getList()); //selected item will look like a spinner set from XML
                            spinnerArrayAdapter.setDropDownViewResource(R.layout
                                    .simple_spinner_dropdown_item);
                            spinner.setAdapter(spinnerArrayAdapter);
                        }, throwable -> {
                            getNavigator().showToast(throwable.getMessage());
                        }));


    }

    void setTripTypeSpinner() {
        Spinner spinner = getBinding().spinnerTrip;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getNavigator().getContext(), R.layout.simple_spinner_layout, getNavigator().getContext().getResources().getStringArray(R.array.trip_type)); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        getBinding().spinnerTrip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string = getBinding().spinnerTrip.getSelectedItem().toString();
                if (string.equals("Select Trip Type") && getBinding().getBooking().getTripType() != null && !getBinding().getBooking().getTripType().isEmpty()) {
                    getBinding().edtType.setText(getBinding().getBooking().getTripType());
                } else {
                    getBinding().edtType.setText(string);
                    getBinding().getBooking().setTripType(string);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    public void getTerminal() {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getTerminal()
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(terminals -> {
                    getNavigator().hideProgress();
                    if (!getBinding().edtTerminal.getText().toString().trim().isEmpty()) {
                        getBinding().imgClear.setVisibility(View.VISIBLE);
                    } else {
                        getBinding().imgClear.setVisibility(View.GONE);
                    }
                    getBinding().edtTerminal.setThreshold(1);
                    getBinding().edtTerminal.setAdapter(new TerminalAutoCompleteAdapter(getNavigator().getContext(), R.layout.item_autocomplete, terminals.getTerminals(), this));
                    getBinding().edtTerminal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TerminalData data = ((TerminalData) parent.getItemAtPosition(position));
                            getBinding().edtTerminal.setSelection(0);
                            getBinding().getBooking().setCity_terminal(getBinding().edtTerminal.getText().toString());
                            getBinding().imgClear.setVisibility(View.VISIBLE);
                        }
                    });
                    getBinding().edtTerminal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                getBinding().edtTerminal.showDropDown();
                            }
                        }
                    });
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }

    public void selectCar() {
        if (validate()) {
            getNavigator().replaceFragment(CarFragment.newInstance(getBinding().getBooking().setTabPosition(0)), CarFragment.class.getName());
        }
    }

    public void dateTimePicker() {
        long time = getBinding().getBooking().getCalendarTime()!=0?getBinding().getBooking().getCalendarTime():System.currentTimeMillis() + TimeUnit.HOURS.toMillis(Long.parseLong(getDataManager().getBlockTime()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SingleDateAndTimePickerDialog.Builder builder = new SingleDateAndTimePickerDialog.Builder(getBinding().getRoot().getContext());
        builder.setTimeZone(Calendar.getInstance().getTimeZone());
        builder.minutesStep(15);
        builder.mainColor(getBinding().getRoot().getResources().getColor(R.color.colorPrimary));
        builder.minDateRange(calendar.getTime());
        builder.closeListener(new SingleDateAndTimePickerDialog.CloseListener() {
            @Override
            public void onClose() {

            }
        });
        builder.title("Select date and time");
        builder.listener(new SingleDateAndTimePickerDialog.Listener() {
            @Override
            public void onDateSelected(String date, String time, String dateTime) {

                AirportViewModel.this.dateTime = dateTime;
                getBinding().edtDate.setText(date);
                getBinding().edtTime.setText(time);
                getBinding().getBooking().setCalendarTime(dateTime);
            }
        });
        builder.display();
    }

    private boolean validate() {
        if (getBinding().getBooking().getBookingType().equals("Booker") && (getBinding().getBooking().getEntity() == null || getBinding().getBooking().getEntity().equals("Select entity"))) {
            getNavigator().showToast("Please select entity");
            return false;
        }
        if (getBinding().getBooking().getCity_terminal() == null) {
            getNavigator().showToast("Please select city | terminal");
            return false;
        }
        if (getBinding().getBooking().getTripType() == null || getBinding().getBooking().getTripType().equals("From Airport / To Airport")) {
            getNavigator().showToast("Please select trip type");
            return false;
        }
        if (getBinding().getBooking().getDate() == null || getBinding().getBooking().getTime() == null) {
            getNavigator().showToast("Please select date & time");
            return false;
        } else {
            String[] city_terminal = getBinding().getBooking().getCity_terminal().split("\\|");
            if (getBinding().getBooking().fromAirport()) {
                getBinding().getBooking().setDropCity(city_terminal[0]);
                getBinding().getBooking().setPickUpCity(city_terminal[1].trim());
            } else {
                getBinding().getBooking().setPickUpCity(city_terminal[0]);
                getBinding().getBooking().setDropCity(city_terminal[1].trim());
            }

            if (dateTime == null || dateTime.isEmpty()) {
                dateTime = getBinding().edtDate.getText().toString() + " " + getBinding().edtTime.getText().toString();
            }
            getBinding().getBooking().setCity(city_terminal[0].trim());
            getBinding().getBooking().setPickUpDateTime(dateTime + ":00");

            getBinding().getBooking().setPackageType(AIRPORT);
            getBinding().getBooking().setOriginAddress("");
            getBinding().getBooking().setDestination_address("");
        }
        return true;
    }

    @Override
    public void onEdit() {
        getTerminal();
    }

    public void clearText() {
        getBinding().edtTerminal.setText("");
        getBinding().imgClear.setVisibility(View.GONE);
    }

    public void setEntityVisibility(boolean check) {
        if (check) {
            getBinding().getBooking().setBookingType(getDataManager().getCustomerType());
            if (getDataManager().getCustomerType().equals("Booker")) {
                getBinding().getRoot().getRootView().findViewById(R.id.card_entity).setVisibility(View.VISIBLE);
            }
        } else {
            getBinding().getBooking().setBookingType("Individual");
            getBinding().getRoot().getRootView().findViewById(R.id.card_entity).setVisibility(View.GONE);
        }
        setBookingType(check);
    }

    void setBookingType(boolean check) {
        if (check) {
            getBinding().getBooking().setBookingType(getDataManager().getCustomerType());
        } else {
            getBinding().getBooking().setBookingType("Individual");
        }
    }

    void getBlockTime(String customerId) {
        if (customerId.equals("Select entity")) {
            return;
        }
        getNavigator().showProgress();
        getCompositeDisposable()
                .add(getDataManager()
                        .getBlockTime(customerId)
                        .doOnSuccess(BaseModel::getResponseStatus)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(baseModel -> {
                            getNavigator().hideProgress();
                            getDataManager().setBlockTime(baseModel.getBlockTime());
                        }, throwable -> {
                            getNavigator().hideProgress();
                            getDataManager().setBlockTime("0");
                        }));

    }
}