package com.ecocustomerapp.ui.fragments.main.outstation;

import static com.ecocustomerapp.utils.AppConstants.OUTSTATION;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.CountryStateCityRequest;
import com.ecocustomerapp.data.model.api.Data;
import com.ecocustomerapp.databinding.FragmentOutstationBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.car.CarFragment;
import com.ecocustomerapp.ui.fragments.main.CityAutoCompleteAdapter;
import com.ecocustomerapp.ui.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class OutstationViewModel extends BaseViewModel<OutstationNavigator, FragmentOutstationBinding> implements CityAutoCompleteAdapter.OnEditingListener {

    public OutstationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    private String pick_date;
    private String drop_date;


    public void clearText(View view) {
        switch (view.getId()) {
            case R.id.img_origin_clear:
                getBinding().edtCityOrigin.setText("");
                getBinding().imgOriginClear.setVisibility(View.GONE);
                break;
            case R.id.img_destination_clear:
                getBinding().edtCityDestination.setText("");
                getBinding().imgDestinationClear.setVisibility(View.GONE);
                break;
        }

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

    public void getCities() {
        getBinding().crdDrop.setVisibility(getBinding().rdGroup.getCheckedRadioButtonId() == getBinding().oneWay.getId() ? View.GONE : View.VISIBLE);
        getBinding().getBooking().setOneway(getBinding().rdGroup.getCheckedRadioButtonId() == getBinding().oneWay.getId());
        getBinding().setOnCheckChange(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getBinding().getBooking().setOneway(i == getBinding().oneWay.getId());
                getBinding().crdDrop.setVisibility(i == getBinding().oneWay.getId() ? View.GONE : View.VISIBLE);
            }
        });
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getCity(new CountryStateCityRequest().setCountry("India"))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(cityResponse -> {
                    getNavigator().hideProgress();
                    if (!getBinding().edtCityOrigin.getText().toString().trim().isEmpty()) {
                        getBinding().imgOriginClear.setVisibility(View.VISIBLE);
                    } else {
                        getBinding().imgOriginClear.setVisibility(View.GONE);
                    }

                    if (!getBinding().edtCityDestination.getText().toString().trim().isEmpty()) {
                        getBinding().imgDestinationClear.setVisibility(View.VISIBLE);
                    } else {
                        getBinding().imgDestinationClear.setVisibility(View.GONE);
                    }
                    getBinding().edtCityOrigin.setThreshold(1);
                    getBinding().edtCityDestination.setThreshold(1);
                    getBinding().edtCityOrigin.setAdapter(new CityAutoCompleteAdapter(getNavigator().getContext(), R.layout.item_autocomplete, cityResponse.getDataList(), this));
                    getBinding().edtCityDestination.setAdapter(new CityAutoCompleteAdapter(getNavigator().getContext(), R.layout.item_autocomplete, cityResponse.getDataList(), this));
                    getBinding().edtCityOrigin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Data data = ((Data) parent.getItemAtPosition(position));
                            getBinding().edtCityOrigin.setSelection(0);
                            getBinding().getBooking().setCity(getBinding().edtCityOrigin.getText().toString());
                            getBinding().getBooking().setCityGeoName(data.getCityGeoName());
                            getBinding().imgOriginClear.setVisibility(View.VISIBLE);
                        }
                    });
                    getBinding().edtCityDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Data data = ((Data) parent.getItemAtPosition(position));
                            getBinding().edtCityDestination.setSelection(0);
                            getBinding().getBooking().setDropCity(data.getCityGeoName());
                            getBinding().imgDestinationClear.setVisibility(View.VISIBLE);
                        }
                    });
                    getBinding().edtCityOrigin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                getBinding().edtCityOrigin.showDropDown();
                            }
                        }
                    });
                    getBinding().edtCityDestination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                getBinding().edtCityDestination.showDropDown();
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

    public void dateTimePicker(TextView view) {
        long time = getBinding().getBooking().getCalendarTime() != 0 ? getBinding().getBooking().getCalendarTime() : System.currentTimeMillis() + TimeUnit.HOURS.toMillis(Long.parseLong(getDataManager().getBlockTime()));
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
                view.setText(dateTime);
                switch (view.getId()) {
                    case R.id.txt_pick_date_time_:
                        pick_date = dateTime;
                        getBinding().getBooking().setCalendarTime(dateTime);
                        break;
                    case R.id.txt_drop_date_time:
                        drop_date = dateTime;
                        break;
                }
            }
        });
        builder.display();
    }

    private boolean validate() {
        if (getBinding().getBooking().getBookingType().equals("Booker") && (getBinding().getBooking().getEntity() == null || getBinding().getBooking().getEntity().equals("Select entity"))) {
            getNavigator().showToast("Please select entity");
            return false;
        }
        if (getBinding().getBooking().getCity() == null) {
            getNavigator().showToast("Please select source city");
            return false;
        }

        if (getBinding().getBooking().getDropCity() == null) {
            getNavigator().showToast("Please select destination city");
            return false;
        }
        if (getBinding().getBooking().getDate() == null) {
            getNavigator().showToast("Please select pickup date & time");
            return false;
        }
        if (getBinding().getBooking().getTime() == null && !getBinding().getBooking().isOneway()) {
            getNavigator().showToast("Please select drop date & time");
            return false;
        } else {

            getBinding().getBooking().setPickUpCity(getBinding().getBooking().getCityGeoName());
            getBinding().getBooking().setPickUpDateTime(pick_date + ":00");
            getBinding().getBooking().setDropOffDateTime(drop_date + ":00");
            getBinding().getBooking().setPackageType(OUTSTATION);
            getBinding().getBooking().setTripType(OUTSTATION);
            getBinding().getBooking().setOriginAddress("");
            getBinding().getBooking().setDestination_address("");
        }
        return true;
    }

    @Override
    public void onEdit() {

    }

    public void setEntityVisibility(boolean check) {
        if (check) {
            getBinding().getBooking().setBookingType(getDataManager().getCustomerType());
            if (getDataManager().getCustomerType().equals("Booker")) {
                getBinding().getRoot().getRootView().findViewById(R.id.card_outstation_entity).setVisibility(View.VISIBLE);
            }
        } else {
            getBinding().getBooking().setBookingType("Individual");
            getBinding().getRoot().getRootView().findViewById(R.id.card_outstation_entity).setVisibility(View.GONE);
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