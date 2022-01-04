package com.ecocustomerapp.ui.fragments.cancel;

import androidx.fragment.app.Fragment;

import com.ecocustomerapp.data.model.api.Package;

import java.util.List;

public interface CancelNavigator {

    void showProgress();
    void hideProgress();
    void showToast(String message);
    void replaceNextFragment(Fragment fragment);
}
