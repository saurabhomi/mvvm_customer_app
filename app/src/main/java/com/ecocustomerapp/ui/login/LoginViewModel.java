

package com.ecocustomerapp.ui.login;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.Login.LoginBody;
import com.ecocustomerapp.data.model.Login.LoginResponseData;
import com.ecocustomerapp.data.model.api.Verification;
import com.ecocustomerapp.databinding.ActivityLoginBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.CommonUtils;
import com.ecocustomerapp.utils.rx.SchedulerProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Saurabh Srivastava on 03/07/21.
 */

public class LoginViewModel extends BaseViewModel<LoginNavigator, ActivityLoginBinding> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public boolean isEmailAndPasswordValid(String email, String password) {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false;
        }
        return !TextUtils.isEmpty(password);
    }

    private LoginBody getRequest(String email, String password) {
        return new LoginBody()
//                .setApp_version("1.0")
//                .setDevice_id("bhjdsfj")
//                .setDevice_model("bhjdsfj")
                .setDevice_type("Android")
                .setEmail(email)
                .setPassword(password)
                .setPush_token(getDataManager().getFireBaseToken());
    }

    public void login(String email, String password) {
        getCompositeDisposable().add(getDataManager()
                .loginApiCall(getRequest(email, password))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::getEntity, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
//                    getNavigator().showToast("IOException java.io.IOException: Cleartext HTTP traffic to http://20.193.234.188/ not permitted");

                }));
    }

    public void onGmailClick() {
        getNavigator().showProgress();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getNavigator().hideProgress();
                getNavigator().showToast("INTERNAL ERROR: Production Mode");
            }
        }, 2000);
    }


    private void getEntity(LoginResponseData data) {
        getCompositeDisposable().add(getDataManager()
                .getEntity(data.getPassenger().getEmail(), data.getPassenger().getMobile(),data.getPassenger().getCustomer_type())
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(verification -> deleteEntity(verification, data), throwable -> {
                    getNavigator().hideProgress();
                    onSuccessfulLogin(data);
                }));
    }


    private void deleteEntity(Verification verification, LoginResponseData data) {
        getCompositeDisposable()
                .add(getDataManager()
                        .deleteAllEntity()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(aBoolean -> {
                            insertEntity(verification, data);
                        }, throwable -> {
                            getNavigator().hideProgress();
                        }));

    }

    private void insertEntity(Verification verification, LoginResponseData data) {
        verification.getEntities().getList().add(0, "Select entity");
        getCompositeDisposable()
                .add(getDataManager()
                        .insertEntity(verification.getEntities())
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(aBoolean -> {
                            onSuccessfulLogin(data);
                        }, throwable -> {
                            getNavigator().hideProgress();
                            getNavigator().showToast(throwable.getMessage());
                        }));
    }

    private void onSuccessfulLogin(LoginResponseData data) {
        getNavigator().hideProgress();
        getNavigator().showToast(data.getMessage());
        getDataManager().setCurrentUserLoggedInMode(data.getPassenger().getChangePassword() ? DataManager.LoggedInMode.LOGGED_IN_MODE_CHANGE_PASSWORD : DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
        getDataManager().updateUserInfo(data.getPassenger());
        getNavigator().openActivity(data);
    }


    public void onLoginClick() {
        getNavigator().login();
    }


    void startSeeding(String email, String password) {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .isFireBaseTokenEmpty()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                seedFirebaseToken(email, password);
                            } else {
                                login(email, password);
                            }
                        }
                        , throwable -> {
                            getNavigator().hideProgress();
                            getNavigator().handleError(throwable);
                        }));
    }

    private void seedFirebaseToken(String email, String password) {
        getDataManager().seedFireBaseToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    login(email, password);
                } else {
                    getNavigator().showToast("Something went wrong");
                }
            }
        });

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

    public void onRegisterClick() {
        getNavigator().register("Get OTP");
    }

    public void forgotPassword() {
        getNavigator().verification();
    }
}
