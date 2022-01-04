// Generated by Dagger (https://dagger.dev).
package com.ecocustomerapp.ui.main;

import com.ecocustomerapp.ui.base.BaseActivity_MembersInjector;
import dagger.MembersInjector;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<MainViewModel> mViewModelProvider;

  public MainActivity_MembersInjector(Provider<MainViewModel> mViewModelProvider) {
    this.mViewModelProvider = mViewModelProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<MainViewModel> mViewModelProvider) {
    return new MainActivity_MembersInjector(mViewModelProvider);}

  @Override
  public void injectMembers(MainActivity instance) {
    BaseActivity_MembersInjector.injectMViewModel(instance, mViewModelProvider.get());
  }
}
