package com.ecocustomerapp.ui.fragments.main.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertDialog;

import com.ecocustomerapp.R;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.data.model.BaseModel;
import com.ecocustomerapp.databinding.FragmentMainBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.registration.OtpActivity;
import com.ecocustomerapp.utils.rx.SchedulerProvider;
import com.google.android.material.tabs.TabLayout;

public class MainFragmentViewModel extends BaseViewModel<MainFragmentNavigator, FragmentMainBinding> {
    public MainFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    void setViewPager(MainPagerAdapter mPagerAdapter, Context context) {
        getBinding().setOnCheckChange(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getNavigator().onCheckChange(isChecked);
            }
        });
        mPagerAdapter.setCount(3);
        mPagerAdapter.setRequest(getNavigator().getBookingRequest());


        getBinding().tabs.addTab(getBinding().tabs.newTab().setCustomView(R.layout.tab_airport));
        getBinding().tabs.addTab(getBinding().tabs.newTab().setCustomView(R.layout.tab_hourly));
        getBinding().tabs.addTab(getBinding().tabs.newTab().setCustomView(R.layout.tab_outstation));
//
        getBinding().viewpager.setOffscreenPageLimit(getBinding().tabs.getTabCount());

        getBinding().viewpager.setAdapter(mPagerAdapter);
        getBinding().tabs.selectTab(getBinding().tabs.getTabAt(getNavigator().getBookingRequest().getTabPosition()));
        getBinding().viewpager.setCurrentItem(getNavigator().getBookingRequest().getTabPosition());

        getBinding().viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(getBinding().tabs));

        getBinding().tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getBinding().viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    void setPassengerType(boolean isChecked, Context context) {
        if (isChecked && getDataManager().getCustomerType().equals("Individual")) {
            showAlert(context);
        } else if (isChecked) {
            getDataManager().setPassengerType(getDataManager().getCustomerType());
        } else {
            getDataManager().setPassengerType("Individual");
        }
    }


    private void showAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogTheme));
        //Setting message manually and performing action on button click
        builder.setMessage("You are not a corporate customer, Do you want to update your profile as corporate customer?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getNavigator().getContext(), OtpActivity.class).putExtra("name", "");
                        getNavigator().getContext().startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        getNavigator().onCheckChange(false);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Update Profile");
        alert.show();
    }




}