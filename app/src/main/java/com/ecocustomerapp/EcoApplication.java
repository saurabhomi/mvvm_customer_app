/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ecocustomerapp;

import android.app.Application;

import com.ecocustomerapp.di.component.AppComponent;
import com.ecocustomerapp.di.component.DaggerAppComponent;
import com.ecocustomerapp.utils.AppLogger;
import com.ecocustomerapp.utils.CustomFontFamily;

import org.acra.ACRA;
import org.acra.annotation.AcraDialog;
import org.acra.annotation.AcraMailSender;

import javax.inject.Inject;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;


/**
 * Created by amitshekhar on 07/07/17.
 */

@AcraMailSender(mailTo = "saurabhsriomi@gmail.com")
public class EcoApplication extends Application {
    CustomFontFamily customFontFamily;
    public AppComponent appComponent;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();

        appComponent.inject(this);

        AppLogger.init();

//        AndroidNetworking.initialize(getApplicationContext());
//        if (BuildConfig.DEBUG) {
//            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
//        }

        customFontFamily=CustomFontFamily.getInstance();
        // add your custom fonts here with your own custom name.
        customFontFamily.addFont("thin","Poppins-Thin.ttf");
        customFontFamily.addFont("lite","Poppins-Light.ttf");
        customFontFamily.addFont("regular","Poppins-Regular.ttf");
        customFontFamily.addFont("semi","Poppins-SemiBold.ttf");
        customFontFamily.addFont("medium","Poppins-Medium.ttf");
        customFontFamily.addFont("bold","Poppins-Bold.ttf");




//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(new CalligraphyInterceptor(
//                        mCalligraphyConfig))
//                .build());
    }
}
