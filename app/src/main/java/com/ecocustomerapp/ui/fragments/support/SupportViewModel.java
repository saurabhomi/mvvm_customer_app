package com.ecocustomerapp.ui.fragments.support;

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
}
