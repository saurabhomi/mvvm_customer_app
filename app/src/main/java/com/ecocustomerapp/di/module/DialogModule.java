package com.ecocustomerapp.di.module;

import com.ecocustomerapp.ui.base.BaseDialog;

import dagger.Module;

/*
 * Author: rotbolt
 */

@Module
public class DialogModule {

    private final BaseDialog dialog;

    public DialogModule(BaseDialog dialog) {
        this.dialog = dialog;
    }


}
