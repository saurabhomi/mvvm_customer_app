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

package com.ecocustomerapp.ui.login;

import com.ecocustomerapp.data.model.Login.LoginResponseData;

/**
 * Created by amitshekhar on 08/07/17.
 */

public interface LoginNavigator {

    void handleError(Throwable throwable);

    void login();

    void showProgress();

    void showToast(String message);

    void hideProgress();

    void openActivity(LoginResponseData data);

    void register(String name);

    void verification();
}
