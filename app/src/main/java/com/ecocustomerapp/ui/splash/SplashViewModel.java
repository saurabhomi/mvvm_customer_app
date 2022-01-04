

package com.ecocustomerapp.ui.splash;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

import com.ecocustomerapp.data.manager.DataManager;
import com.ecocustomerapp.databinding.ActivitySplashBinding;
import com.ecocustomerapp.ui.base.BaseViewModel;
import com.ecocustomerapp.ui.splash.SplashNavigator;
import com.ecocustomerapp.utils.rx.SchedulerProvider;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Saurabh Srivastava on 08/07/17.
 */

public class SplashViewModel extends BaseViewModel<SplashNavigator, ActivitySplashBinding> {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    void startVideo(VideoView view, String path) {


        getCompositeDisposable()
                .add(Observable.just(view)
                        .flatMap(view1 -> {
                            view.setVideoURI(Uri.parse(path));
                            return Observable.just(view);
                        })
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(view1 -> {
                            view.start();
                            view.setOnCompletionListener(onCompletionListener);
                        },throwable -> {
                            Timber.e(throwable.getCause());
                        }));
    }



    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.stop();
            decideNextActivity();
        }
    };

    void decideNextActivity() {
        if (getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
            getNavigator().openLoginActivity();
        } else {
            getNavigator().openMainActivity();
        }
    }
}
