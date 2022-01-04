package com.ecocustomerapp.ui.base;

import androidx.annotation.Nullable;

import com.ecocustomerapp.di.transactor.SafeFragmentTransaction;


final class CustomNonConfigInstance {

    final SafeFragmentTransaction safeFragmentTransaction;
    final Object other;

    CustomNonConfigInstance(@Nullable final SafeFragmentTransaction safeFragmentTransaction,
                            @Nullable final Object other) {
        this.safeFragmentTransaction = safeFragmentTransaction;
        this.other = other;
    }
}