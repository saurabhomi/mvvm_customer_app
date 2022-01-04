package com.ecocustomerapp.ui.fragments.car;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.data.model.api.Package;
import com.ecocustomerapp.data.model.api.PackageRequest;
import com.ecocustomerapp.databinding.FragmentCarBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.booking.BookingFragment;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;
import com.ecocustomerapp.utils.AppConstants;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.HashSet;
import java.util.List;

public class CarViewModel extends BaseViewModel<CarNavigator, FragmentCarBinding> {
    private final MutableLiveData<List<Package>> liveData;

    public CarViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        liveData = new MutableLiveData<>();

    }

    public void modify() {
        getNavigator().replaceNextFragment(MainFragment.newInstance(getBinding().getBooking()));
    }

    private PackageRequest getRequest(String type) {
        PackageRequest request = new PackageRequest()
                .setCity(getBinding().getBooking().getCity())
                .setPassengerId(getDataManager().getPassengerId())
                .setPassengerType(getDataManager().getPassengerType())
                .setBookingType(getBinding().getBooking().getBookingType());

        if (getDataManager().getCustomerType().equals("Booker")) {
            request.setCustomerID(getDataManager().getCustomerId());
        }
        if (type.equals(AppConstants.OUTSTATION)) {

            request.setPickUpDateTime(getBinding().getBooking().getPickUpDateTime())
                    .setDropOffDateTime(getBinding().getBooking().getDropOffDateTime());
        }
        return request;
    }


    public void getCar() {
        getNavigator().showProgress();
        getCompositeDisposable().add(getDataManager()
                .getPackage(getBinding().getBooking().getPackageType(), getRequest(getBinding().getBooking().getPackageType()))
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(packageResponse -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(packageResponse.getMessage());
                    liveData.setValue(packageResponse.getPackageList());
                }, throwable -> {
                    getNavigator().hideProgress();
                    getNavigator().showToast(throwable.getMessage());
                }));
    }


    void getPaymentMethod(BookingRequest request, Package aPackage) {
        getNavigator().showProgress();
        getCompositeDisposable()
                .add(getDataManager()
                        .getPaymentMode(getDataManager()
                                .getCustomerId())
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(loginResponseData -> {
                            getNavigator().hideProgress();
                            getDataManager().setMod(new HashSet<String>(loginResponseData.getPassenger().getModeOfPayment()));
                            getNavigator().replaceNextFragment(BookingFragment.newInstance(request, aPackage));
                        }, throwable -> {
                            getNavigator().hideProgress();
                            getNavigator().showToast(throwable.getMessage());
                        }));
    }

    public LiveData<List<Package>> getCarData() {
        return liveData;
    }


    // TODO: Implement the ViewModel
}