package com.ecocustomerapp.ui.registration;

public interface RegistrationNavigator {
    void showToast(String message);

    void showProgress();

    void hideProgress();

    void openMainActivity();
}
