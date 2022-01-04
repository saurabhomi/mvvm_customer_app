package com.ecocustomerapp.di.transactor;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;


import com.ecocustomerapp.ui.FragmentCallback;

import java.util.Stack;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SafeFragmentTransaction implements LifecycleObserver, DefaultLifecycleObserver {

    private final Stack<TransitionHandler> mFragmentTransitionsStack;
    private Lifecycle mLifecycle;
    private FragmentManager mFragmentManager;
    private final PublishSubject<Integer> fragmentEventSubject = PublishSubject.create();
    private final PublishSubject<FragmentCallback> fragmentCallbackSubject = PublishSubject.create();

    private SafeFragmentTransaction() {
        this.mFragmentTransitionsStack = new Stack<>();
    }

    public static SafeFragmentTransaction createInstance() {
        return new SafeFragmentTransaction();
    }

    private void onTransitionRegistered(SafeFragmentTransaction.TransitionHandler transition) {
        mFragmentTransitionsStack.add(transition);
        if (mLifecycle != null && mFragmentManager != null &&
                mLifecycle.getCurrentState()
                        .isAtLeast(Lifecycle.State.RESUMED)) {
            doTransactions();
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        doTransactions();
    }

    private synchronized void doTransactions() {
        while (mFragmentTransitionsStack.size() != 0) {
            TransitionHandler transition = mFragmentTransitionsStack.pop();
            transition.onTransitionAvailable(mFragmentManager);
        }
    }

    public SafeFragmentTransaction registerFragmentTransition(TransitionHandler transitionHandler) {
        onTransitionRegistered(transitionHandler);
        return this;
    }

    public void detachLifecycle() {
        mLifecycle.removeObserver(this);
        mLifecycle = null;
        mFragmentManager = null;
    }

    public void attachLifecycle(@NonNull final Lifecycle lifecycle,
                                @NonNull final FragmentManager fragmentManager) {
        this.mLifecycle = lifecycle;
        this.mFragmentManager = fragmentManager;
        lifecycle.addObserver(this);
    }

    public Observable<Integer> getFragmentEventObservable() {
        return fragmentEventSubject;
    }

    public void postFragmentAction(Integer actionId) {
        fragmentEventSubject.onNext(actionId);
    }

    public void FragmentCallbackAction(FragmentCallback callback) {
        fragmentCallbackSubject.onNext(callback);
    }

    @FunctionalInterface
    public interface TransitionHandler {
        void onTransitionAvailable(final FragmentManager fragmentManager);
    }
}