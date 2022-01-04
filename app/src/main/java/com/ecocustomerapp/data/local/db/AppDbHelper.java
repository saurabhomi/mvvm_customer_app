/*
 *  Copyright (C) 2019 JAJ TECHNOLOGIES PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://jajtechnologies.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.ecocustomerapp.data.local.db;


import com.ecocustomerapp.data.model.api.Entities;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;
    private int trip_data_count;
    private int failed_data_count;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<Boolean> insertEntity(Entities verification) {
        return Observable.fromCallable(() -> {
            mAppDatabase.entityDao().insert(verification);
            Timber.i("Verification inserted");
            return true;
        });
    }

    @Override
    public Observable<Boolean> deleteAllEntity() {
        return Observable.fromCallable(() -> {
            mAppDatabase.entityDao().deleteAllEntity();
            Timber.i("All verification: deleted");
            return true;
        });
    }

    @Override
    public Observable<Entities> getAllEntity() {
        return Observable.fromCallable(() -> {
            Timber.i("All verification retrieved");
            return mAppDatabase.entityDao().loadAll();
        });
    }


}
