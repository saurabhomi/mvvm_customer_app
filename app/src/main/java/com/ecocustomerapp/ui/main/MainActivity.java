/*
 *  Copyright (C) 2017 JAJ Technologies Private Limited
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

package com.ecocustomerapp.ui.main;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.BuildConfig;
import com.ecocustomerapp.R;
import com.ecocustomerapp.data.model.api.BookingRequest;
import com.ecocustomerapp.databinding.ActivityMainBinding;
import com.ecocustomerapp.databinding.NavHeaderMainBinding;
import com.ecocustomerapp.di.component.ActivityComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseActivity;
import com.ecocustomerapp.ui.fragments.bookings.BookingsFragment;
import com.ecocustomerapp.ui.fragments.bookings.BookingsListFragment;
import com.ecocustomerapp.ui.fragments.main.main.MainFragment;
import com.ecocustomerapp.ui.fragments.mode.ModeFragment;
import com.ecocustomerapp.ui.fragments.profile.ProfileFragment;
import com.ecocustomerapp.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;


/**
 * Class Description.
 *
 * @author Saurabh Srivastava.
 * @version 1.0.
 * @since 15/6/19.
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, NavigationView.OnNavigationItemSelectedListener, FragmentCallback {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mActivityMainBinding;
    private DrawerLayout mDrawer;
    private MenuItem item;
    private Fragment fragment;
    private boolean isBack;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void handleError(Throwable throwable, int message) {
        // handle error
        showToast(getString(message));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentAttached(Fragment fragment, boolean isBack) {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setBack(isBack);
        this.fragment = fragment;
        if (fragment instanceof ModeFragment) {
            item = mActivityMainBinding.navView.getMenu().getItem(0).setChecked(true);
        } else if (fragment instanceof ProfileFragment) {
            item = mActivityMainBinding.navView.getMenu().getItem(2).setChecked(true);
        } else if (fragment instanceof BookingsFragment) {
            item = mActivityMainBinding.navView.getMenu().getItem(3).setChecked(true);
        }else if(item!=null) {
            item.setChecked(false);
            item=null;
        }

    }

    @Override
    public void setBack(boolean isBack) {
        this.isBack=isBack;
        mActivityMainBinding.appBar.toolbar.setNavigationIcon(isBack ? R.drawable.back : R.drawable.ic_menu);
    }

    @Override
    public void onFragmentDetached(String tag) {
        mViewModel.removeFragment(this, tag);
    }


    public void replaceFragment(Fragment fragment, String TAG) {
        mViewModel.transitFragment(this, fragment);
    }


    public void goBack(Fragment fragment, String TAG) {
        mViewModel.transitFragment(this, fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            showLoading();
        } else {
            hideLoading();
        }
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mViewModel.onStart(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            {
                mViewModel.checkGPS(this);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mViewModel.onStop(this);
    }

    @Override
    public MainActivity getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        NavHeaderMainBinding mainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, mActivityMainBinding.navView, false);
        mActivityMainBinding.navView.addHeaderView(mainBinding.getRoot());
        mActivityMainBinding.setViewModel(mViewModel);
        mViewModel.setBinding(mActivityMainBinding);
        setUp();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    private void setUp() {
        mDrawer = mActivityMainBinding.drawerLayout;
        mActivityMainBinding.navView.setNavigationItemSelectedListener(this);
        item = mActivityMainBinding.navView.getMenu().getItem(0).setChecked(true);

        setSupportActionBar(mActivityMainBinding.appBar.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mActivityMainBinding.appBar.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);


        getSupportActionBar().setDisplayShowHomeEnabled(false);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActivityMainBinding.appBar.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mViewModel.onNavMenuCreated();
        mViewModel.transitFragment(this, ModeFragment.newInstance());
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBack){
                    onBackPressed();
                }else {
                    mDrawer.open();
                }

            }
        });
    }

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        if (this.item != null && this.item == item) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.nav_logout:
                mViewModel.logout();
                Intent intent = LoginActivity.newIntent(MainActivity.this);
                startActivity(intent);
                finish();
                this.item = item;
                break;
            case R.id.nav_home:
                mViewModel.transitFragment(this, ModeFragment.newInstance());
                this.item = item;
                break;
            case R.id.nav_bookings:
                if (!(fragment instanceof BookingsFragment) && !(fragment instanceof BookingsListFragment)) {
                    mViewModel.transitFragment(this, BookingsFragment.newInstance());
                    this.item = item;
                }

                break;
            case R.id.nav_profile:
                replaceFragment(ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName());
                this.item = item;
                break;
            case R.id.nav_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.nav_support:
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:01140794079")));
                break;
            case R.id.nav_about_us:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ecorentacar.com/about-us/"));
                browserIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(browserIntent);
                break;
            default:
                Toast.makeText(this, "Under development", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }


}
