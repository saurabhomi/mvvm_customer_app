package com.ecocustomerapp.di.component;

import com.ecocustomerapp.di.module.ActivityModule;
import com.ecocustomerapp.di.scope.ActivityScope;
import com.ecocustomerapp.ui.main.MainActivity;
import com.ecocustomerapp.ui.login.LoginActivity;
import com.ecocustomerapp.ui.password.ForgetPassword;
import com.ecocustomerapp.ui.registration.OtpActivity;
import com.ecocustomerapp.ui.registration.RegistrationActivity;
import com.ecocustomerapp.ui.reset.ResetActivity;
import com.ecocustomerapp.ui.splash.SplashActivity;
import com.ecocustomerapp.ui.verification.VerificationActivity;

import dagger.Component;

/*
 * Author: rotbolt
 */

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(OtpActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(RegistrationActivity registrationActivity);

    void inject(VerificationActivity verificationActivity);

    void inject(ResetActivity resetActivity);

    void inject(ForgetPassword forgetPassword);

}
