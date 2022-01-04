package com.ecocustomerapp.ui.fragments.profile;

import com.ecocustomerapp.ui.fragments.main.main.MainFragment;

public interface ProfileNavigator {
    void showProgress();

    void hideProgress();

    void showToast(String message);

    void replaceNextFragment(MainFragment newInstance);
}
