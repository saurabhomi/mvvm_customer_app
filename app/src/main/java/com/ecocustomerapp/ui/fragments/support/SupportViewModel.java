package com.ecocustomerapp.ui.fragments.support;

import android.content.Intent;
import android.net.Uri;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.databinding.FragmentSupportBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

public class SupportViewModel extends BaseViewModel<SupportFragmentNavigator, FragmentSupportBinding> {
    public SupportViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void onClickCall(){
        getNavigator().makeCall();
    }

    public void onClickEmail(){
        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "cars@ecorentacar.com"));
        getNavigator().getContext().startActivity(intent);
    }
}
