package com.ecocustomerapp.ui.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.otp.OtpData;
import com.ecocustomerapp.databinding.ActivityRegistrationBinding;
import com.ecocustomerapp.databinding.ActivityVerificationBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.login.LoginActivity;
import com.ecocustomerapp.ui.password.ForgetPassword;

public class VerificationActivity extends BaseActivity<ActivityVerificationBinding, VerificationViewModel> implements VerificationNavigator {

    ActivityVerificationBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, VerificationActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        mViewModel.setBinding(binding);
        mViewModel.setNavigator(this);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
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
    public void showToast(String s) {
        Toast.makeText(this, String.valueOf(s), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPassword(OtpData otpData) {
        startActivity(ForgetPassword.newIntent(this,otpData));
    }
}