package com.ecocustomerapp.ui.fragments.review;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.databinding.FragmentFinalBookingBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

public class FinalBookingViewModel extends BaseViewModel<FinalBookingNavigator, FragmentFinalBookingBinding> {

    private String mod;

    public FinalBookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void book() {
        if (validate()) {
            getBinding().getBooking().setSalutation(getBinding().getBooking().getElse_name());
            getBinding().getBooking().setEmailAddress(getBinding().getBooking().getElse_email());
            getBinding().getBooking().setMobile(getBinding().getBooking().getElse_mobile());
            createBooking();
        }
    }

    public void showSpinner() {
        getBinding().spinnerMod.performClick();
    }

    void setModSpinner(Context context) {
        List<String> list = new ArrayList<>();
        list.add("Payment mode");
        list.addAll(getDataManager().getMod());
        Spinner spinner = getBinding().spinnerMod;
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        getBinding().spinnerMod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mod = getBinding().spinnerMod.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void setGuestDetails() {
        if (getBinding().getBooking().forElse()) {
            getBinding().txtName.setText(getBinding().getBooking().getElse_name());
            getBinding().txtEmail.setText(getBinding().getBooking().getElse_email());
            getBinding().txtMobile.setText(getBinding().getBooking().getElse_mobile());
        }
    }

    private boolean validate() {

        if (!getBinding().chk.isChecked()) {
            getNavigator().showToast("Please accept term of services and privacy policy");
            return false;
        }
        if (mod == null || mod.trim().isEmpty() || mod.equals("Payment mode")) {
            getNavigator().showToast("Please select payment mode");
            return false;
        }


        return true;
    }

    public void createBooking() {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .createBooking(getBinding().getBooking())
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(bookingResponse -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(bookingResponse.getMessage());
                    getBinding().btnBook.setVisibility(View.GONE);
                    getNavigator().showBooking(bookingResponse);
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }

}