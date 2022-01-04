package com.ecocustomerapp.ui.registration;

import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.otp.OtpResponseBody;

public interface OtpNavigator {

    void handleError(Throwable throwable);

    void showProgress();

    void hideProgress();

    void openLoginActivity();

    void openRegistrationActivity(OtpResponseBody responseBody, Registration registration);

    void showToast(int message);

    void showToast(String message);

    void onCheckChange(boolean check);
}
