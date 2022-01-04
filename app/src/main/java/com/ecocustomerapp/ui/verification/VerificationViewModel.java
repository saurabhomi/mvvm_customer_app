package com.ecocustomerapp.ui.verification;

import android.text.TextUtils;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.databinding.ActivityVerificationBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.CommonUtils;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class VerificationViewModel extends BaseViewModel<VerificationNavigator, ActivityVerificationBinding> {
    public VerificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onOtpClick() {
        if (isEmailValid()) {
            getOtp(getBinding().edtEmail.getText().toString());
        } else {
            getNavigator().showToast("Please enter a valid email");
        }
    }

    public boolean isEmailValid() {
        // validate email and password
        if (TextUtils.isEmpty(getBinding().edtEmail.getText().toString())) {
            return false;
        }
        return CommonUtils.isEmailValid(getBinding().edtEmail.getText().toString());
    }


    public void getOtp(String email) {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .forgetPasswordOtp(email)
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(response.getMessage());
                    getNavigator().setPassword(response.getData());
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                    Timber.e(throwable);
                }));
    }
}
