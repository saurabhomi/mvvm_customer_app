package com.ecocustomerapp.ui.reset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.databinding.ActivityResetBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.main.MainActivity;

public class ResetActivity extends BaseActivity<ActivityResetBinding, ResetViewModel> implements ResetNavigator {

    private ActivityResetBinding mActivityResetBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ResetActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityResetBinding = getViewDataBinding();
        mViewModel.setBinding(mActivityResetBinding);
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
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgress() {
        hideLoading();
    }

    @Override
    public void openActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finishAffinity();
    }
}