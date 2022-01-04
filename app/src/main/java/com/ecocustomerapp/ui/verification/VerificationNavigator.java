package com.ecocustomerapp.ui.verification;

import com.ecocustomerapp.data.model.otp.OtpData;

public interface VerificationNavigator {

    void showProgress();

    void hideProgress();

    void showToast(String s);

    void setPassword(OtpData otpData);
}
