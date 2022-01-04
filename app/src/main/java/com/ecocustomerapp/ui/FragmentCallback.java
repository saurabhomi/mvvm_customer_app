package com.ecocustomerapp.ui;

import androidx.fragment.app.Fragment;


public interface FragmentCallback {
    void onFragmentAttached(Fragment fragment, boolean isBack);

    void onFragmentDetached(String tag);

    void replaceFragment(Fragment fragment, String TAG);

    void goBack(Fragment fragment, String TAG);

    void setBack(boolean isBack);

}