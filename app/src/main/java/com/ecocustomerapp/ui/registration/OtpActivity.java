package com.ecocustomerapp.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.otp.OtpResponseBody;
import com.ecocustomerapp.databinding.ActivityOtpBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.login.LoginActivity;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpViewModel> implements OtpNavigator {

    private ActivityOtpBinding otpBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, OtpActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding = getViewDataBinding();
        otpBinding.setData(new Registration());
        mViewModel.setNavigator(this);
        mViewModel.setBinding(otpBinding);
        mViewModel.onCheckedChanged(otpBinding.swc.isChecked());
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showProgress() {
        showLoading();

    }

    @Override
    public void hideProgress() {
        hideLoading();
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void openRegistrationActivity(OtpResponseBody responseBody, Registration registration) {
        Intent intent = RegistrationActivity.newIntent(OtpActivity.this, responseBody, registration);
        startActivity(intent);
    }

    @Override
    public void showToast(int message) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckChange(boolean check) {

    }
}