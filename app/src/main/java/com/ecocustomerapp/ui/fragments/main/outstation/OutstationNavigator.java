package com.ecocustomerapp.ui.fragments.main.outstation;

import android.content.Context;

import androidx.fragment.app.Fragment;

public interface OutstationNavigator {
    void showProgress();

    void hideProgress();

    void showToast(String message);

    Context getContext();

    void replaceFragment(Fragment newInstance, String name);
}
