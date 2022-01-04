package com.ecocustomerapp.ui.registration;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.otp.OtpRequestBody;
import com.ecocustomerapp.databinding.ActivityRegistrationBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator, ActivityRegistrationBinding> {

    public RegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void register() {
        if (validate()) {
            registration();
        }
    }

    private void setError(String error, EditText editText) {
        editText.setError(error);
        getNavigator().showToast(error);

    }

    private Registration getRequest(Registration user) {
        return user.setAppVersion("1.0")
                .setGender("N/A")
                .setToken("n/a")
                .setDeviceId("54554545")
                .setDeviceType("Android")
                .setPushToken("kjkfldjdfhkjdgd")
                .setGuestFirstName(user.getGuest_name().trim().split(" ")[0])
                .setGuestLastName(user.getGuest_name().trim().split(" ")[1]);
    }

    public void getOtp(OtpRequestBody body, boolean mobile) {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getOtpApiCall(body)
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(response.getMessage());
                    if (mobile) {
                        getBinding().getData().setMobileOtp(response.getData().getOtp_mobile());
                    } else {
                        getBinding().getData().setEmailOtp(response.getData().getOtp_email());
                    }
                }, throwable -> {
                    getNavigator().hideProgress();
                    Timber.e(throwable);
                }));
    }

    public void getEntity(Context context) {
        getBinding().spinnerEntity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string = getBinding().spinnerEntity.getSelectedItem().toString();
                getBinding().edtEntity.setText(string);
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

    public void resend(View view) {
        switch (view.getId()) {
            case R.id.txt_mobile_resend:
                getOtp(new OtpRequestBody().setMobile(getBinding().getData().mobile).setCustomerType(getBinding().getData().customerType), true);
                break;
            case R.id.txt_email_resend:
                getOtp(new OtpRequestBody().setEmail(getBinding().getData().email).setCustomerType(getBinding().getData().customerType), false);
                break;
            default:
                break;
        }

    }

    public void registration() {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .createPassenger(getRequest(getBinding().getData()))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(loginResponseData -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(loginResponseData.getMessage());
                    getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
                    getDataManager().updateUserInfo(loginResponseData.getPassenger());
                    getNavigator().openMainActivity();
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }


    public void onShowPasswordClick(ImageView imageView) {

        assert (R.id.img_show_password == imageView.getId());

        // See here
        Integer integer = (Integer) imageView.getTag();
        integer = integer == null ? 0 : integer;

        switch (integer) {
            case R.drawable.ic_invisible:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.drawable.ic_visible:
                imageView.setImageResource(R.drawable.ic_invisible);
                imageView.setTag(R.drawable.ic_invisible);
                getBinding().edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            default:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
        }
    }

    public void onShowConfirmPasswordClick(ImageView imageView) {

        assert (R.id.img_show_confirm_password == imageView.getId());

        // See here
        Integer integer = (Integer) imageView.getTag();
        integer = integer == null ? 0 : integer;

        switch (integer) {
            case R.drawable.ic_invisible:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtCnfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.drawable.ic_visible:
                imageView.setImageResource(R.drawable.ic_invisible);
                imageView.setTag(R.drawable.ic_invisible);
                getBinding().edtCnfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            default:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtCnfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
        }
    }

    public boolean validate() {
        Registration user = getBinding().getData();
        // validate email and password
        if (TextUtils.isEmpty(user.guest_name)) {
            setError("Please enter your full name", getBinding().edtName);
            return false;
        }
        if (getBinding().getData().isCorporate() && (TextUtils.isEmpty(user.customerId) || user.customerId.equals("Select entity"))) {
            setError("Please select entity", getBinding().edtName);
            return false;
        }
        if (TextUtils.isEmpty(user.password)) {
            setError("Please enter password.", getBinding().edtPassword);
            return false;
        }
        if (TextUtils.isEmpty(user.confirmPassword)) {
            setError("Please re-enter password.", getBinding().edtCnfPassword);
            return false;
        }
        if (!user.confirmPassword.equals(user.password)) {
            setError("Password & confirm password should be same", getBinding().edtCnfPassword);
            return false;
        }
        if (TextUtils.isEmpty(user.mobileOtp) || !getBinding().edtOtpMobile.getText().toString().trim().equals(getBinding().getData().getMobileOtp())) {
            setError("Please enter correct mobile OTP!", getBinding().edtOtpMobile);
            return false;
        }
        if (TextUtils.isEmpty(user.emailOtp) || !getBinding().edtOtpEmail.getText().toString().trim().equals(getBinding().getData().getEmailOtp())) {
            setError("Please enter correct email OTP!", getBinding().edtOtpEmail);
            return false;
        }
        if (!getBinding().getData().isCorporate()){
            getBinding().getData().setCustomerId("Individual");
        }
        return true;
    }
}