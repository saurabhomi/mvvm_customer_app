package com.ecocustomerapp.di.module;

import android.app.Service;

import com.ecocustomerapp.data.manager.AppDataManager;
import com.ecocustomerapp.data.manager.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service service;

    public ServiceModule(Service service) {
        this.service = service;
    }

}
