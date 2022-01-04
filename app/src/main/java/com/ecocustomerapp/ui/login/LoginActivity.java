package com.ecocustomerapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.Login.LoginResponseData;
import com.ecocustomerapp.databinding.ActivityLoginBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.main.MainActivity;
import com.ecocustomerapp.ui.registration.OtpActivity;
import com.ecocustomerapp.ui.reset.ResetActivity;
import com.ecocustomerapp.ui.verification.VerificationActivity;

/**
 * Created by Saurabh Srivastava on 08/07/17.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    private ActivityLoginBinding mActivityLoginBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void login() {
        String email = mActivityLoginBinding.edtUserName.getText().toString().trim();
        String password = mActivityLoginBinding.edtPassword.getText().toString().trim();
        if (mViewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            mViewModel.startSeeding(email, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress() {
        showLoading();
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(this, String.valueOf(message), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgress() {
        hideLoading();
    }

    @Override
    public void openActivity(LoginResponseData data) {
        Intent intent = data.getPassenger().getChangePassword()? ResetActivity.newIntent(this):MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void register(String name) {
        Intent intent = new Intent(this, OtpActivity.class).putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void verification() {
        startActivity(VerificationActivity.newIntent(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mViewModel.setBinding(mActivityLoginBinding);
        mViewModel.setNavigator(this);
        mActivityLoginBinding.edtUserName.requestFocus();
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
