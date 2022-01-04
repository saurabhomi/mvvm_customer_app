package com.ecocustomerapp.di.module;


import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.ecocustomerapp.ViewModelProviderFactory;
import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.login.LoginViewModel;
import com.ecocustomerapp.ui.main.MainViewModel;
import com.ecocustomerapp.ui.password.ForgetPasswordViewModel;
import com.ecocustomerapp.ui.registration.OtpViewModel;
import com.ecocustomerapp.ui.registration.RegistrationViewModel;
import com.ecocustomerapp.ui.reset.ResetViewModel;
import com.ecocustomerapp.ui.splash.SplashViewModel;
import com.ecocustomerapp.ui.verification.VerificationViewModel;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/*
 * Author: rotbolt
 */

@Module
public class ActivityModule {
    private final BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }


    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LoginViewModel.class);
    }

    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<SplashViewModel> supplier = () -> new SplashViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<SplashViewModel> factory = new ViewModelProviderFactory<>(SplashViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SplashViewModel.class);
    }

    @Provides
    OtpViewModel provideOtpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<OtpViewModel> supplier = () -> new OtpViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<OtpViewModel> factory = new ViewModelProviderFactory<>(OtpViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(OtpViewModel.class);
    }

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    RegistrationViewModel provideRegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<RegistrationViewModel> supplier = () -> new RegistrationViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<RegistrationViewModel> factory = new ViewModelProviderFactory<>(RegistrationViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(RegistrationViewModel.class);
    }

    @Provides
    VerificationViewModel provideVerificationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<VerificationViewModel> supplier = () -> new VerificationViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<VerificationViewModel> factory = new ViewModelProviderFactory<>(VerificationViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(VerificationViewModel.class);
    }

    @Provides
    ResetViewModel provideResetViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ResetViewModel> supplier = () -> new ResetViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ResetViewModel> factory = new ViewModelProviderFactory<>(ResetViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ResetViewModel.class);
    }

    @Provides
    ForgetPasswordViewModel providePasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        Supplier<ForgetPasswordViewModel> supplier = () -> new ForgetPasswordViewModel(dataManager, schedulerProvider);
        ViewModelProviderFactory<ForgetPasswordViewModel> factory = new ViewModelProviderFactory<>(ForgetPasswordViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ForgetPasswordViewModel.class);
    }

}
