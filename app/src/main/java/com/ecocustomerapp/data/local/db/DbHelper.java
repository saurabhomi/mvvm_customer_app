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

import io.reactivex.Observable;

/**
 * Created by Saurabh Srivastava on 29.05.19
 */

public interface DbHelper {

    /*queries for entity*/

    Observable<Boolean> insertEntity(Entities entities);

    Observable<Boolean> deleteAllEntity();

    Observable<Entities> getAllEntity();


}
