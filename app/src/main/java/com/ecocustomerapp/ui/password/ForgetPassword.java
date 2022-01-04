package com.ecocustomerapp.ui.password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.otp.OtpData;
import com.ecocustomerapp.databinding.ActivityForgetPasswordBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.login.LoginActivity;

public class ForgetPassword extends BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordViewModel> implements ForgetPasswordNavigator {

    private ActivityForgetPasswordBinding mActivityBinding;

    public static Intent newIntent(Context context, OtpData data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        return new Intent(context, ForgetPassword.class).putExtra("bundle", bundle);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = getViewDataBinding();
        mViewModel.setBinding(mActivityBinding);
        mViewModel.setNavigator(this);
        if (getIntent() != null && getIntent().getBundleExtra("bundle") != null) {
            OtpData otpData = (OtpData) getIntent().getBundleExtra("bundle").getSerializable("data");
            mActivityBinding.setData(otpData);
        }
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
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgress() {
        hideLoading();
    }

    @Override
    public void openActivity() {
        startActivity(LoginActivity.newIntent(this));
        finishAffinity();
    }
}