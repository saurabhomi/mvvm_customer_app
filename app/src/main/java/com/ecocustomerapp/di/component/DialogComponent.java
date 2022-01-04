package com.ecocustomerapp.di.component;

import com.ecocustomerapp.di.module.DialogModule;
import com.ecocustomerapp.di.scope.DialogScope;

import dagger.Component;

/*
 * Author: rotbolt
 */

@DialogScope
@Component(modules = DialogModule.class, dependencies = AppComponent.class)
public interface DialogComponent {


}
