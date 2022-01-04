package com.ecocustomerapp.ui.password;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.ImageView;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.databinding.ActivityForgetPasswordBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import timber.log.Timber;

public class ForgetPasswordViewModel extends BaseViewModel<ForgetPasswordNavigator, ActivityForgetPasswordBinding> {
    public ForgetPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean validate() {
        String otp = getBinding().edtEmailMobile.getText().toString();
        String password = getBinding().edtPassword.getText().toString();
        String cnf_password = getBinding().edtCnfPassword.getText().toString();
        if (TextUtils.isEmpty(otp) || !otp.equals(getBinding().getData().getOtp_email())) {
            getNavigator().showToast("Please enter correct email otp");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            getNavigator().showToast("Please enter password");
            return false;
        }
        if (TextUtils.isEmpty(cnf_password)) {
            getNavigator().showToast("Please re-enter password");
            return false;
        }
        if (!password.equals(cnf_password)) {
            getNavigator().showToast("password and confirm password must be same");
            return false;
        }
        return true;
    }

    public void resend() {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .forgetPasswordOtp(getBinding().getData().getEmail())
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(response.getMessage());
                    getBinding().setData(response.getData());
                }, throwable -> {
                    getNavigator().hideProgress();
                    Timber.e(throwable);
                }));
    }


    public void changePassword() {
        if (validate()) {
            getCompositeDisposable().add(getDataManager()
                    .changePassword(getBinding().getData().getPassengerId(), getBinding().edtPassword.getText().toString())
                    .doOnSuccess(BaseModel::getResponseStatus)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(baseModel -> {
                        getNavigator().hideProgress();
                        getNavigator().showToast(baseModel.getMessage());
                        getNavigator().openActivity();
                    }, throwable -> {
                        getNavigator().hideProgress();
                        getNavigator().showToast(throwable.getMessage());
                    }));
        }
    }

    public void onShowPasswordClick(ImageView imageView) {

        assert (R.id.img_show_password == imageView.getId());

        // See here
        Integer integer = (Integer) imageView.getTag();
        integer = integer == null ? 0 : integer;

        switch (integer) {
            case R.drawable.ic_invisible:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.drawable.ic_visible:
                imageView.setImageResource(R.drawable.ic_invisible);
                imageView.setTag(R.drawable.ic_invisible);
                getBinding().edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            default:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
        }
    }

    public void onShowConfirmPasswordClick(ImageView imageView) {

        assert (R.id.img_show_confirm_password == imageView.getId());

        // See here
        Integer integer = (Integer) imageView.getTag();
        integer = integer == null ? 0 : integer;

        switch (integer) {
            case R.drawable.ic_invisible:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtCnfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case R.drawable.ic_visible:
                imageView.setImageResource(R.drawable.ic_invisible);
                imageView.setTag(R.drawable.ic_invisible);
                getBinding().edtCnfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            default:
                imageView.setImageResource(R.drawable.ic_visible);
                imageView.setTag(R.drawable.ic_visible);
                getBinding().edtCnfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
        }
    }
}
