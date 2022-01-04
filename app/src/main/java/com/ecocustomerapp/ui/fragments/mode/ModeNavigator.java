package com.ecocustomerapp.ui.fragments.mode;

import android.content.Context;

import androidx.fragment.app.Fragment;

public interface ModeNavigator {
    void replaceFragment(Fragment fragment, String TAG);
    Context getContext();
}
