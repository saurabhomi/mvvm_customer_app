package com.ecocustomerapp.ui.fragments.confirmation;


import android.view.View;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.databinding.FragmentReceiptBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.fragments.cancel.CancelFragment;
import com.ecocustomerapp.ui.fragments.support.SupportFragmentFragment;
import com.ecocustomerapp.ui.fragments.tracking.TrackingFragment;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

public class ReceiptViewModel extends BaseViewModel<ReceiptNavigator, FragmentReceiptBinding> {
    public ReceiptViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void onBottomMenuClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                getNavigator().replaceFragment(CancelFragment.newInstance(getBinding().getBooking(), true, ReceiptFragment.class.getSimpleName()));
                break;
            case R.id.txt_track:
                getNavigator().replaceFragment(TrackingFragment.newInstance(getBinding().getBooking(), true));
                break;
            case R.id.txt_support:
                getNavigator().replaceFragment(SupportFragmentFragment.newInstance(true,ReceiptFragment.class.getSimpleName(),getBinding().getBooking(),getBinding().getAPackage(),true));
                break;
        }

    }


}
