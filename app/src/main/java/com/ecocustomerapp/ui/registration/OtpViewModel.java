package com.ecocustomerapp.ui.registration;

import android.text.TextUtils;
import android.util.Patterns;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.Verification;
import com.ecocustomerapp.data.model.otp.OtpRequestBody;
import com.ecocustomerapp.data.model.otp.OtpResponseBody;
import com.ecocustomerapp.databinding.ActivityOtpBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class OtpViewModel extends BaseViewModel<OtpNavigator, ActivityOtpBinding> {
    public OtpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void getOtp() {
        String mobile = getBinding().getData().mobile;
        String email = getBinding().getData().email;
        if (TextUtils.isEmpty(mobile) || !Patterns.PHONE.matcher(mobile).matches()) {
            getNavigator().showToast(R.string.please_enter_valid_mobile_number);
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getNavigator().showToast(R.string.please_enter_valid_email_address);
            return;
        }
        if (getBinding().btnOtp.getText().equals("Get OTP")) {
            getOtp("91" + mobile, email);
        } else if (getBinding().btnOtp.getText().equals("Get Password")) {
            getPassword(mobile, email);
        }

    }

    public void openLoginActivity() {
        getNavigator().openLoginActivity();
    }

    public void getOtp(String mobile, String email) {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getOtpApiCall(new OtpRequestBody()
                        .setMobile(mobile).setEmail(email).setCustomerType(getBinding().getData().customerType))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().showToast(response.getMessage());
                    getBinding().getData().setMobile(mobile);
                    getBinding().getData().setMobileOtp(response.getData().getOtp_mobile());
                    getBinding().getData().setEmailOtp(response.getData().getOtp_email());
                    if (response.getData().getPassengerType().equals("Individual")) {
                        getNavigator().hideProgress();
                        getNavigator().openRegistrationActivity(response, getBinding().getData());
                    } else if (response.getData().getPassengerType().equals("Corporate")) {
                        getEntity(response);
                    } else {
                        getNavigator().hideProgress();
                        getNavigator().showToast(response.getMessage());
                    }
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }

    private void getEntity(OtpResponseBody response) {

        getCompositeDisposable().add(getDataManager()
                .getEntity(response.getData().getEmail(), response.getData().getMobile(), response.getData().getPassengerType())
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(verification -> insertEntity(verification, response), throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }

    private void insertEntity(Verification verification, OtpResponseBody response) {
        verification.getEntities().getList().add(0, "Select entity");
        getCompositeDisposable()
                .add(getDataManager()
                        .insertEntity(verification.getEntities())
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(aBoolean -> {
                            getNavigator().hideProgress();
                            getNavigator().showToast(response.getMessage());
                            getNavigator().openRegistrationActivity(response, getBinding().getData());
                        }, throwable -> {
                            getNavigator().hideProgress();
                            getNavigator().showToast(throwable.getMessage());
                        }));
    }

    public void onCheckedChanged(boolean checked) {
        getBinding().txtIndividual.setEnabled(!checked);
        getBinding().txtCorporate.setEnabled(checked);
        getBinding().getData().setCustomerType(checked ? "Corporate" : "Individual");
    }

    public void getPassword(String mobile, String email) {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getPassword(new OtpRequestBody()
                        .setMobile(mobile).setEmail(email))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(response.getMessage());
                    getNavigator().openLoginActivity();
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                    Timber.e(throwable);
                }));
    }
}
