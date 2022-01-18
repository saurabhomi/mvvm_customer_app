package com.ecocustomerapp.ui.fragments.booking;

import android.widget.RadioGroup;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.databinding.FragmentBookingBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import java.util.Calendar;

public class BookingViewModel extends BaseViewModel<BookingNavigator, FragmentBookingBinding> {

    public BookingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private boolean forElse;

    public void modify() {
        dateTimePicker();
    }

    public void setUp() {
        getBinding().rdYes.setEnabled(getBinding().getBooking().getBookingType().equals("Individual") || getBinding().getBooking().getBookingType().equals("Booker"));
        if (getBinding().getBooking().forElse()) {
            forElse = true;
            getBinding().rdYes.setChecked(true);
            setElseEnable(true);
        } else {
            forElse = false;
            getBinding().rdNo.setChecked(true);
            setElseEnable(false);
        }
        getBinding().setOnCheckChange(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setElseEnable(checkedId == R.id.rd_yes);
                forElse = checkedId == R.id.rd_yes;
            }
        });

        if (getBinding().getBooking().fromAirport()) {
            getBinding().getBooking().setOriginAddress(getBinding().getBooking().getPickUpCity());
            getBinding().txtPickupAddress.setEnabled(false);
        } else if (getBinding().getBooking().toAirport()) {
            getBinding().getBooking().setDestination_address(getBinding().getBooking().getDropCity());
            getBinding().txtDropAddress.setEnabled(false);
        }
    }

    private void setElseEnable(boolean enable) {
        getBinding().elseName.setEnabled(enable);
        getBinding().elseEmail.setEnabled(enable);
        getBinding().elseMobile.setEnabled(enable);

        if (enable && forElse) {
            getBinding().elseName.setText(getBinding().getBooking().getElse_name());
            getBinding().elseEmail.setText(getBinding().getBooking().getElse_email());
            getBinding().elseMobile.setText(getBinding().getBooking().getElse_mobile());
        } else if (enable) {
            getBinding().elseName.setText("");
            getBinding().elseEmail.setText("");
            getBinding().elseMobile.setText("");
        } else {
            getBinding().elseName.setText("");
            getBinding().elseEmail.setText("");
            getBinding().elseMobile.setText("");
            getBinding().elseName.setText(getBinding().getBooking().getSalutation());
            getBinding().elseEmail.setText(getBinding().getBooking().getEmailAddress());
            getBinding().elseMobile.setText(getBinding().getBooking().getMobile());
        }


    }


    public void proceed() {
        if (validate(getNavigator().getRequest())) {

            if (getDataManager().getCustomerType().equals("Booker") || forElse) {
                getBinding().getBooking().setBookerId(getDataManager().getBookerId());
                getPassengerId();
            } else {
                getNavigator().proceedToPay(getBinding().getBooking());
            }
        }
    }

    private boolean validate(BookingRequest request) {

        boolean isPickupRequired = request.getTripType().equals("To airport") || request.getTripType().equals("Local") || request.getTripType().equals("Outstation");

        boolean isDropRequired = request.getTripType().equals("From airport") || request.getTripType().equals("Local") || request.getTripType().equals("Outstation");

        if (isPickupRequired && (request.getOriginAddress() == null || request.getOriginAddress().trim().isEmpty())) {
            getNavigator().showToast("Please enter pickup address");
            return false;
        }
        if (isDropRequired && (request.getDestination_address() == null || request.getDestination_address().trim().isEmpty())) {
            getNavigator().showToast("Please enter drop address");
            return false;
        }

        if (request.getTripType().contains("airport") && (request.getFlight_details() == null || request.getFlight_details().trim().isEmpty())) {
            getNavigator().showToast("Please enter flight details");
            return false;
        }
        if (getDataManager().getShowCaseCode() && (request.getCaseCode() == null || request.getCaseCode().trim().isEmpty())) {
            getNavigator().showToast("Please enter " + getDataManager().getCaseCodeName());
            return false;
        } else {
            if (getBinding().getBooking().fromAirport()) {
                getBinding().getBooking().setDropAddress(getBinding().getBooking().getDestination_point()+getBinding().getBooking().getDestination_address()  +  ", " + getBinding().getBooking().getDropCity());
                getBinding().getBooking().setPickUpAddress(getBinding().getBooking().getPickUpCity());
                getBinding().getBooking().setFlightDetails(getBinding().txtFlightDetails.getText().toString());
            } else if (getBinding().getBooking().toAirport()) {
                getBinding().getBooking().setPickUpAddress(getBinding().getBooking().getOrigin_point()+getBinding().getBooking().getOriginAddress() +  ", " + getBinding().getBooking().getPickUpCity());
                getBinding().getBooking().setDropAddress(getBinding().getBooking().getDropCity());
                getBinding().getBooking().setFlightDetails(getBinding().txtFlightDetails.getText().toString());
            }

            if (getBinding().getBooking().getTripType().equals("Local") || getBinding().getBooking().getTripType().equals("Outstation")) {
                getBinding().getBooking().setPickUpAddress(getBinding().getBooking().getOrigin_point()+ getBinding().getBooking().getOriginAddress()  + ", " + getBinding().getBooking().getPickUpCity());
                getBinding().getBooking().setDropAddress(getBinding().getBooking().getDestination_point() +getBinding().getBooking().getDestination_address() );
                getBinding().getBooking().setFlightDetails("Test");

            }
        }

        if (!request.getTripType().contains("airport")) {
            getBinding().getBooking().setFlight_details("test");
        }

        if (forElse) {
            getBinding().getBooking().setSomeoneElse("Yes");
        } else {
            getBinding().getBooking().setSomeoneElse("No");
        }

        if (getBinding().getBooking().getMobile().length() >= 11) {
            getBinding().getBooking().setMobile(getBinding().getBooking().getMobile().substring(2));
        }

        getBinding().getBooking().setPassengerType(getDataManager().getPassengerType());
        getBinding().getBooking().setCaseCode("test");

        return true;
    }


    public void dateTimePicker() {

        long time = getBinding().getBooking().getCalendarTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SingleDateAndTimePickerDialog.Builder builder = new SingleDateAndTimePickerDialog.Builder(getBinding().getRoot().getContext());
        builder.setTimeZone(Calendar.getInstance().getTimeZone());
        builder.minutesStep(15);
        builder.mainColor(getBinding().getRoot().getResources().getColor(R.color.colorPrimary));
        builder.minDateRange(calendar.getTime());
        builder.closeListener(new SingleDateAndTimePickerDialog.CloseListener() {
            @Override
            public void onClose() {

            }
        });
        builder.title("Select date and time");
        builder.listener(new SingleDateAndTimePickerDialog.Listener() {
            @Override
            public void onDateSelected(String date, String time, String dateTime) {

                getBinding().getBooking().setPickUpDateTime(dateTime + ":00");
                getBinding().txtDepartureTime.setText(getBinding().getBooking().getDepartureTime());
            }
        });
        builder.display();
    }


    private void getPassengerId() {
        getNavigator().showProgress();
        getCompositeDisposable()
                .add(getDataManager()
                        .getPassenger(getBinding().getBooking().getElse_name(), getBinding()
                                .getBooking()
                                .getElse_email(), getBinding().getBooking().getElse_mobile())
                        .doOnSuccess(BaseModel::getResponseStatus)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(loginResponseData -> {

                            if (getDataManager().getCustomerType().equals("Individual") && forElse) {
                                if (loginResponseData.getPassenger().getId().equals("0")){
                                    getBinding().getBooking().setPassengerId(loginResponseData.getPassenger().getId());
                                    getBinding().getBooking().setBookerId(getDataManager().getPassengerId());
                                }else {
                                    getBinding().getBooking().setPassengerId(loginResponseData.getPassenger().getId());
                                    getBinding().getBooking().setBookerId(loginResponseData.getPassenger().getId());
                                }
                                getBinding().getBooking().setName(getDataManager().getUserName());

                            } else if (getDataManager().getCustomerType().equals("Booker")) {
                                if (loginResponseData.getPassenger().getId().equals("0")) {
                                    getBinding().getBooking().setPassengerId(loginResponseData.getPassenger().getId());
                                    getBinding().getBooking().setBookerId(getDataManager().getBookerId());
                                } else {
                                    getBinding().getBooking().setPassengerId(loginResponseData.getPassenger().getId());
                                    getBinding().getBooking().setBookerId(getDataManager().getBookerId());
                                }
                                getBinding().getBooking().setName(getDataManager().getUserName());

                            }
                            getNavigator().hideProgress();
                            getNavigator().proceedToPay(getBinding().getBooking());
                        }, throwable -> {
                            getNavigator().hideProgress();
                            getNavigator().showToast(throwable.getMessage());
                        }));

    }
}