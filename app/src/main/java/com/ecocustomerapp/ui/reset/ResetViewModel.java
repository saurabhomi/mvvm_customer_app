package com.ecocustomerapp.ui.reset;

import androidx.databinding.ObservableField;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.databinding.ActivityResetBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

public class ResetViewModel extends BaseViewModel<ResetNavigator, ActivityResetBinding> {

    public ResetViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> confirm_password = new ObservableField<>();

    private boolean validate() {
        if (password.get() == null || password.get().isEmpty()) {
            getNavigator().showToast("Please enter a new password");
            return false;
        }
        if (confirm_password.get() == null || confirm_password.get().isEmpty()) {
            getNavigator().showToast("Please enter confirm password");
            return false;
        }
        if (!password.get().equals(confirm_password.get())) {
            getNavigator().showToast("Password and confirm password not matched");
        }
        return password.get().equals(confirm_password.get());
    }

    public void changePassword() {
        if (validate()) {
            getCompositeDisposable().add(getDataManager()
                    .changePassword(getDataManager().getPassengerId(), password.get())
                    .doOnSuccess(BaseModel::getResponseStatus)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(baseModel -> {
                        getNavigator().hideProgress();
                        getNavigator().showToast(baseModel.getMessage());
                        getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER);
                        getNavigator().openActivity();
                    }, throwable -> {
                        getNavigator().hideProgress();
                        getNavigator().showToast(throwable.getMessage());
                    }));
        }
    }
}
