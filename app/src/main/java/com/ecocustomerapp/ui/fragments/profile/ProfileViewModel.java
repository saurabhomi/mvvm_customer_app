package com.ecocustomerapp.ui.fragments.profile;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.otp.OtpRequestBody;
import com.ecocustomerapp.databinding.FragmentProfileBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator, FragmentProfileBinding> {
    private String otp;

    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void sendOTP(TextView textView) {
        String email = getBinding().edtOfficialEmail.getText().toString();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getNavigator().showToast(textView.getContext().getString(R.string.please_enter_valid_email_address));
        } else {
            textView.setText(R.string.resend);
            getOtp(email);
        }
    }

    public void cancel(TextView textView) {

    }

    void disable() {
        getBinding().edtFName.setEnabled(false);
        getBinding().edtLName.setEnabled(false);
        getBinding().edtGender.setEnabled(false);
        getBinding().edtMobile.setEnabled(false);
        getBinding().edtEmail.setEnabled(false);
    }

    public void update() {
//        if (TextUtils.isEmpty(getBinding().getUser().email_otp) || !getBinding().getUser().email_otp.equals(otp)) {
//            getNavigator().showToast("Please enter correct email OTP!");
//        } else {
//            registration();
//        }
    }

    public void registration() {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .createPassenger(getBinding()
                        .getUser()
                        .setEmail(getBinding().getUser().email))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(loginResponseData -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(loginResponseData.getMessage());
                    getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
                    getDataManager().updateUserInfo(loginResponseData.getPassenger());
                    getNavigator().replaceNextFragment(MainFragment.newInstance(new BookingRequest()));
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }

    public void getOtp(String email) {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getOtpApiCall(new OtpRequestBody()
                        .setMobile("").setEmail(email))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    otp = response.getData().getOtp_email();
                    getNavigator().hideProgress();
                    getNavigator().showToast(response.getMessage());
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                    Timber.e(throwable);
                }));
    }
}
