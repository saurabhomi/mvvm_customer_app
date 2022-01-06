package com.ecocustomerapp.ui.fragments.main.local;


import static com.ecocustomerapp.utils.AppConstants.LOCAL;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.CountryStateCityRequest;
import com.ecocustomerapp.data.model.api.Data;
import com.ecocustomerapp.data.model.api.HourlyPackage;
import com.ecocustomerapp.data.model.api.PackageRequest;
import com.ecocustomerapp.databinding.FragmentLocalBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.car.CarFragment;
import com.ecocustomerapp.ui.fragments.main.CityAutoCompleteAdapter;
import com.ecocustomerapp.ui.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class LocalViewModel extends BaseViewModel<LocalNavigator, FragmentLocalBinding> implements CityAutoCompleteAdapter.OnEditingListener {


    public LocalViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private String dateTime;


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
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getCity(new CountryStateCityRequest().setCountry("India"))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(cityResponse -> {
                    getNavigator().hideProgress();
                    if (!getBinding().edtCity.getText().toString().trim().isEmpty()) {
                        getBinding().imgClear.setVisibility(View.VISIBLE);
                    } else {
                        getBinding().imgClear.setVisibility(View.GONE);
                    }
                    getBinding().edtCity.setThreshold(1);
                    getBinding().edtCity.setAdapter(new CityAutoCompleteAdapter(getNavigator().getContext(), R.layout.item_autocomplete, cityResponse.getDataList(), this));
                    getBinding().edtCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Data data = ((Data) parent.getItemAtPosition(position));
                            getBinding().edtCity.setSelection(0);
                            getBinding().getBooking().setCity(getBinding().edtCity.getText().toString());
                            getBinding().getBooking().setCityGeoName(data.getCityGeoName());
                            getBinding().imgClear.setVisibility(View.VISIBLE);
                            getLocalPackage(getNavigator().getContext());
                        }
                    });
                    getBinding().edtCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (hasFocus) {
                                getBinding().edtCity.showDropDown();
                            }
                        }
                    });
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }


    private PackageRequest getRequest() {

        PackageRequest request= new PackageRequest()
                .setCity(getBinding().getBooking().getCityGeoName())
                .setBookingType(getBinding().getBooking().getBookingType())
                .setPassengerType(getDataManager().getPassengerType())
                .setPassengerId(getDataManager().getPassengerId());
        if (getDataManager().getCustomerType().equals("Booker")){
            request.setCustomerID(getDataManager().getCustomerId());
        }
        return request;
    }

    void getLocalPackage(Context context) {
        getBinding().spinnerPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HourlyPackage aPackage = (HourlyPackage) getBinding().spinnerPackage.getSelectedItem();
                getBinding().txtPackage.setText(aPackage.getName());
                getBinding().getBooking().setHourlyPackage(aPackage.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        getCompositeDisposable()
                .add(getDataManager()
                        .getHourlyPackage(getRequest())
                        .doOnSuccess(BaseModel::getResponseStatus)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(packageResponse -> {
                            Spinner spinner = getBinding().spinnerPackage;
                            HourlyAdapter spinnerArrayAdapter = new HourlyAdapter
                                    (packageResponse.getHourlyPackages(), context, "local"); //selected item will look like a spinner set from XML
                            spinner.setAdapter(spinnerArrayAdapter);
                        }, throwable -> {
                            getNavigator().showToast(throwable.getMessage());
                        }));
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

                LocalViewModel.this.dateTime = dateTime;
                getBinding().txtDate.setText(date);
                getBinding().txtTime.setText(time);
                getBinding().getBooking().setCalendarTime(dateTime);
            }
        });
        builder.display();
    }

    public void selectCar() {
        if (validate()) {
            getNavigator().replaceFragment(CarFragment.newInstance(getBinding().getBooking().setTabPosition(0)), CarFragment.class.getName());
        }
    }

    private boolean validate() {
        if (getBinding().getBooking().getBookingType().equals("Booker") && (getBinding().getBooking().getEntity() == null || getBinding().getBooking().getEntity().equals("Select entity"))) {
            getNavigator().showToast("Please select entity");
            return false;
        }
        if (getBinding().getBooking().getCity() == null) {
            getNavigator().showToast("Please select city");
            return false;
        }
        if (getBinding().getBooking().getLocalPackage() == null || getBinding().getBooking().getLocalPackage().equals("Select")) {
            getNavigator().showToast("Please select local package");
            return false;
        }
        if (getBinding().getBooking().getDate() == null || getBinding().getBooking().getTime() == null) {
            getNavigator().showToast("Please select date & time");
            return false;
        } else {
            String city = getBinding().getBooking().getCityGeoName();
            getBinding().getBooking().setPickUpCity(city);
            getBinding().getBooking().setDropCity(city);
//

            getBinding().getBooking().setPickUpDateTime(dateTime + ":00");
            getBinding().getBooking().setPackageType(LOCAL);
            getBinding().getBooking().setTripType(LOCAL);
            getBinding().getBooking().setOriginAddress("");
            getBinding().getBooking().setDestination_address("");
        }
        return true;
    }

    @Override
    public void onEdit() {

    }

    public void clearText() {
        getBinding().edtCity.setText("");
        getBinding().imgClear.setVisibility(View.GONE);
    }

    public void setEntityVisibility(boolean check) {
        if (check) {
            getBinding().getBooking().setBookingType(getDataManager().getCustomerType());
            if (getDataManager().getCustomerType().equals("Booker")) {
                getBinding().getRoot().getRootView().findViewById(R.id.card_local_entity).setVisibility(View.VISIBLE);
            }
        } else {
            getBinding().getBooking().setBookingType("Individual");
            getBinding().getRoot().getRootView().findViewById(R.id.card_local_entity).setVisibility(View.GONE);
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