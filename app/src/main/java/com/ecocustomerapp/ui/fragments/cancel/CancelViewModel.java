package com.ecocustomerapp.ui.fragments.cancel;

import android.widget.EditText;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.databinding.FragmentCancelBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragment;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

public class CancelViewModel extends BaseViewModel<CancelNavigator, FragmentCancelBinding> {
    public CancelViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void cancelBooking(EditText editText, String booking_id) {
        if (editText.getText().toString().trim().isEmpty()) {
            getNavigator().showToast("Please write the reason to cancel this booking.");
            return;
        }
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getCancellation(booking_id, editText.getText().toString())
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(baseModel -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(baseModel.getMessage());
                    getNavigator().replaceNextFragment(BookingsFragment.newInstance());
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }
}
