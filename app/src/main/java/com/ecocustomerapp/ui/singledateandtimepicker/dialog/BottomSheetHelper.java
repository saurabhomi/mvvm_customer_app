package com.ecocustomerapp.ui.singledateandtimepicker.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;

import com.ecocustomerapp.R;

import static android.content.Context.WINDOW_SERVICE;

public class BottomSheetHelper implements View.OnClickListener {

    private final Context context;
    private final int layoutId;

    private View view;
    private Listener listener;

    private final Handler handler;
    private WindowManager windowManager;
    private Button cancel;

    public BottomSheetHelper(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        this.handler = new Handler(Looper.getMainLooper());
    }

    private void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (context instanceof Activity) {
                    windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

                    view = LayoutInflater.from(context).inflate(layoutId, null, true);
                    cancel = view.findViewById(R.id.cancel);
                    cancel.setOnClickListener(BottomSheetHelper.this);


                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                            // Shrink the window to wrap the content rather than filling the screen
                            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                            // Don't let it grab the input focus
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                            // Make the underlying application window visible through any transparent parts
                            PixelFormat.TRANSLUCENT);

                    if ((layoutParams.softInputMode
                            & WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION) == 0) {
                        WindowManager.LayoutParams nl = new WindowManager.LayoutParams();
                        nl.copyFrom(layoutParams);
                        nl.softInputMode |= WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION;
                        layoutParams = nl;
                    }

                    windowManager.addView(view, layoutParams);


                    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            view.getViewTreeObserver().removeOnPreDrawListener(this);
                            if (listener != null) {
                                listener.onLoaded(view);
                            }
                            animateBottomSheet();
                            return false;
                        }
                    });
                }
            }
        }, 100);
    }

    public BottomSheetHelper setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public void display() {
        init();
    }

    public void hide() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final ObjectAnimator objectAnimator =
                        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, view.getHeight());
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        if (listener != null) {
                            listener.onClose();
                        }
                        remove();
                    }
                });
                objectAnimator.start();
            }
        }, 200);
    }

    public void dismiss() {
        remove();
    }

    private void remove() {
        if (view.getWindowToken() != null) windowManager.removeView(view);
    }

    private void animateBottomSheet() {
        final ObjectAnimator objectAnimator =
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.getHeight(), 0);
        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onOpen();
                }
            }
        });
        objectAnimator.start();
    }

    @Override
    public void onClick(View v) {
        hide();
    }

    public interface Listener {
        void onOpen();

        void onLoaded(View view);

        void onClose();
    }
}
