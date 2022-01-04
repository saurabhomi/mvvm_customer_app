package com.ecocustomerapp.ui.password;

public interface ForgetPasswordNavigator {

    void showProgress();

    void showToast(String message);

    void hideProgress();

    void openActivity();
}
