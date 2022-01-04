package com.ecocustomerapp.ui.fragments.car;

import androidx.fragment.app.Fragment;

import com.ecocustomerapp.data.model.api.Package;

import java.util.List;

public interface CarNavigator {
    void showProgress();
    void hideProgress();
    void showToast(String message);
    void updateCars(List<Package> packages);
    void replaceNextFragment(Fragment fragment);
}
