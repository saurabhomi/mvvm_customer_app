package com.ecocustomerapp.ui.fragments.mode;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecocustomerapp.BR;
import com.ecocustomerapp.R;
import com.ecocustomerapp.databinding.FragmentModeBinding;
import com.ecocustomerapp.di.component.FragmentComponent;
import com.ecocustomerapp.ui.FragmentCallback;
import com.ecocustomerapp.ui.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModeFragment extends BaseFragment<FragmentCallback, FragmentModeBinding, ModeViewModel> implements ModeNavigator {


    public ModeFragment() {
        // Required empty public constructor
    }

    public static ModeFragment newInstance() {
        ModeFragment fragment = new ModeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentModeBinding fragmentModeBinding =getViewDataBinding();
        mViewModel.setBinding(fragmentModeBinding);
        mViewModel.setPager();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mode;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        setCallback((FragmentCallback) context).onFragmentAttached(this,false);
    }

    @Override
    public void onDetach() {
        getCallback().onFragmentDetached(this.getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
    }

    @Override
    public void replaceFragment(Fragment fragment, String TAG) {
        getCallback().replaceFragment(fragment, TAG);
    }


    @Override
    public Context getContext() {
        return getBaseActivity();
    }
}