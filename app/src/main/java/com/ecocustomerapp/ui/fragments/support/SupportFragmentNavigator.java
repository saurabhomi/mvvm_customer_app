package com.ecocustomerapp.ui.fragments.support;

import android.content.Context;

public interface SupportFragmentNavigator {

    void replaceFragment();

    void makeCall();

    Context getContext();
}
