package com.ecocustomerapp.ui.fragments.tracking;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.DriverDetails;
import com.ecocustomerapp.databinding.FragmentTrackingBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.maps.MapObservableProvider;
import com.ecocustomerapp.utils.rx.SchedulerProvider;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import timber.log.Timber;

public class TrackingViewModel extends BaseViewModel<TrackingNavigator, FragmentTrackingBinding> {
    private MapObservableProvider mapObservableProvider;
    private GoogleMap googleMap;
    private Marker marker;
    private Marker pickUpMarker;

    public TrackingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    protected void showMap() {
        mapObservableProvider = new MapObservableProvider(getNavigator().getMapFragment());
        getCompositeDisposable().add(mapObservableProvider.getMapReadyObservable().subscribe(googleMap -> {
            this.googleMap = googleMap;
            setMarkerOnMap();
            Timber.d("map ready");
        }, throwable -> getNavigator().handleError(throwable, R.string.something_wrong)));
    }

    private void setMarkerOnMap() {
        getCompositeDisposable()
                .add(Observable.zip(mapObservableProvider.getMarkerObservable(getDataManager().getCurrentLocation(), "My position"), mapObservableProvider.getMarkerObservable(getDataManager().getCurrentLocation(), "Pickup Point"), new BiFunction<Marker, Marker, LatLngBounds>() {
                    @Override
                    public @NotNull LatLngBounds apply(@NotNull Marker marker, @NotNull Marker marker2) throws Exception {
                        TrackingViewModel.this.marker = marker;
                        TrackingViewModel.this.pickUpMarker = marker2;
                        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                        boundsBuilder.include(marker.getPosition());
                        boundsBuilder.include(marker2.getPosition());
                        return boundsBuilder.build();
                    }
                })
                        .subscribe(bounds -> {
                            setBounds(bounds);
                        }, throwable ->
                                getNavigator().handleError(throwable, R.string.something_wrong)));
    }

    private void setBounds(LatLngBounds bounds) {
        getCompositeDisposable().add(mapObservableProvider
                .getBoundsObservable(bounds, getNavigator().getMapFragment().requireView().getMeasuredWidth(), getNavigator().getMapFragment().getView().getMeasuredHeight())
                .subscribe(
                        bounds1 -> getNavigator().showToast(bounds1.toString()),
                        throwable -> getNavigator().handleError(throwable, R.string.something_wrong)));
    }

    void onLocationChange(Location location) {
        super.currentLocation = location;
        if (googleMap != null && marker != null) {

            getCompositeDisposable()
                    .add(mapObservableProvider.getMarkerAnimatorObservable(marker, location)
                            .subscribe(marker1 -> {
                                TrackingViewModel.this.marker = marker1;
                                LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                                boundsBuilder.include(marker1.getPosition());
                                boundsBuilder.include(pickUpMarker.getPosition());
                                setBounds(boundsBuilder.build());
                            }, Timber::d));

        }
    }

    void getDriverDetails() {
        getNavigator().showProgress(true);
        getCompositeDisposable().add(getDataManager()
                .getDriverDetails(getNavigator().getBookingId())
                .doOnSuccess(BaseModel::getResponseStatus)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(driverData -> {
                    if(driverData.getDriverDetails()!=null){
                        setDetails(driverData.getDriverDetails(), getBinding().getRoot().getContext());
                    }
                    getNavigator().showProgress(false);
                    getNavigator().showToast(driverData.getMessage());
                }, throwable -> {
                    getBinding().imgDriver.setVisibility(View.GONE);
                    getNavigator().showProgress(false);
                    getNavigator().showToast(throwable.getMessage());
                }));
    }

    private void setDetails(DriverDetails details, Context context) {
        if (details.getImage() != null && !details.getImage().trim().equalsIgnoreCase("")) {
            byte[] imageByteArray = Base64.decode(details.getImage(), Base64.DEFAULT);
            Glide.with(context).asBitmap().load(imageByteArray).placeholder(R.drawable.sedan).into(getBinding().imgDriver);
        }
        String first = "OTP:";
        String next = "<font color=\"#56A527\"> 2345 </font>";
        getBinding().txtOtp.setText(Html.fromHtml(first + next));
        getBinding().txtModel.setText(details.getType());
        getBinding().txtNumber.setText(details.getCar_no());
        getBinding().txtName.setText(details.getName());

        getBinding().crdCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + details.getMobile())));
            }
        });
    }

}
