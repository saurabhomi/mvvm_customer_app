package com.ecocustomerapp.ui.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.Registration;
import com.ecocustomerapp.data.model.otp.OtpResponseBody;
import com.ecocustomerapp.databinding.ActivityRegistrationBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.login.LoginActivity;
import com.ecocustomerapp.ui.main.MainActivity;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel> implements RegistrationNavigator {

    ActivityRegistrationBinding activityRegistrationBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);

    }

    @Override
    public void onBackPressed() {
        startActivity(LoginActivity.newIntent(this));
        finishAffinity();
    }

    public static Intent newIntent(Context context, OtpResponseBody body, Registration user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("otp", body);
        bundle.putSerializable("user", user);
        return new Intent(context, RegistrationActivity.class).putExtra("bundle", bundle);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegistrationBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        mViewModel.setBinding(activityRegistrationBinding);
        if (getIntent() != null && getIntent().getBundleExtra("bundle") != null) {
            OtpResponseBody body = (OtpResponseBody) getIntent().getBundleExtra("bundle").getSerializable("otp");
            Registration user = (Registration) getIntent().getBundleExtra("bundle").getSerializable("user");
            activityRegistrationBinding.setOtp(body);
            activityRegistrationBinding.setData(user);
        }
        mViewModel.getEntity(this);


    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(RegistrationActivity.this);
        startActivity(intent);
        finishAffinity();
    }
}