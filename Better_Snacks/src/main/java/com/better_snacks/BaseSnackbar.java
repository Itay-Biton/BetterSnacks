package com.better_snacks;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public abstract class BaseSnackbar {

    public enum AnimationType {
        NONE, SLIDE_IN_BOTTOM, FADE_IN, SCALE
    }

    protected final View view;
    protected final String message;
    protected final String actionText;
    protected final int duration;
    protected final int layoutDirection;
    protected final boolean userDefinedDirection;
    protected final Integer backgroundColor;
    protected final Integer textColor;
    protected final Integer actionTextColor;
    protected final Drawable icon;
    protected final AnimationType animationType;
    protected final boolean vibrateOnShow;
    protected final long vibrationDuration;
    protected final boolean soundOnShow;
    protected final Integer soundResId;

    protected BaseSnackbar(Builder builder) {
        this.view = builder.view;
        this.message = builder.message;
        this.actionText = builder.actionText;
        this.duration = builder.duration;
        this.layoutDirection = builder.layoutDirection;
        this.userDefinedDirection = builder.userDefinedDirection;
        this.backgroundColor = builder.backgroundColor;
        this.textColor = builder.textColor;
        this.actionTextColor = builder.actionTextColor;
        this.icon = builder.icon;
        this.animationType = builder.animationType;
        this.vibrateOnShow = builder.vibrateOnShow;
        this.vibrationDuration = builder.vibrationDuration;
        this.soundOnShow = builder.soundOnShow;
        this.soundResId = builder.soundResId;
    }

    protected abstract void show();

    protected void applyVisuals(Snackbar snackbar) {
        if (duration > 0)
            snackbar.setDuration(duration);

        View snackbarView = snackbar.getView();

        int resolvedDirection = userDefinedDirection
                ? layoutDirection
                : TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());

        snackbarView.setLayoutDirection(resolvedDirection);

        if (backgroundColor != null) {
            snackbarView.setBackgroundColor(backgroundColor);
        }

        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        TextView actionView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_action);

        textView.setGravity(Gravity.VERTICAL_GRAVITY_MASK);

        if (textColor != null) {
            textView.setTextColor(textColor);
        }

        if (actionTextColor != null) {
            actionView.setTextColor(actionTextColor);
        }

        if (icon != null) {
            int iconSize = (int) (textView.getLineHeight() * 0.9f);

            icon.setBounds(0, 0, iconSize, iconSize);
            textView.setCompoundDrawablePadding((int) (iconSize * 0.6));

            if (resolvedDirection == View.LAYOUT_DIRECTION_RTL)
                textView.setCompoundDrawables(null, null, icon, null);
            else
                textView.setCompoundDrawables(icon, null, null, null);
        }

        if (vibrateOnShow)
            triggerVibration(view.getContext());
        if (soundOnShow)
            triggerSound(view.getContext());

        if(animationType != AnimationType.NONE) {
            snackbarView.setVisibility(GONE);
            snackbar.show();
            snackbarView.postDelayed(() -> {
                snackbarView.setVisibility(VISIBLE);
                applyAnimationIn(snackbarView, animationType);
            }, 100);
        }
        else
            snackbar.show();
    }

    protected void triggerVibration(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrationDuration > 0 ? vibrationDuration : 0, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    protected void triggerSound(Context context) {
        MediaPlayer player = MediaPlayer.create(context, soundResId != null ? soundResId : R.raw.default_sound);
        if (player != null) {
            player.setOnCompletionListener(MediaPlayer::release);
            player.setOnErrorListener((mp, what, extra) -> {
                mp.release();
                return true;
            });
            player.start();
        }
    }

    protected void applyAnimationIn(View view, AnimationType type) {
        switch (type) {
            case SLIDE_IN_BOTTOM:
                view.setTranslationY(view.getHeight());
                view.animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator())
                        .setDuration(400)
                        .start();
                break;
            case FADE_IN:
                view.setAlpha(0f);
                view.animate()
                        .alpha(1f)
                        .setDuration(400)
                        .start();
                break;
            case SCALE:
                view.setScaleX(0.4f);
                view.setScaleY(0.4f);
                view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(400)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
                break;
            case NONE:
            default:
                break;
        }
    }

    public abstract static class Builder<T extends Builder<T>> {
        private final View view;
        private String message = "message";
        private String actionText = "action";
        private int duration;
        private int layoutDirection = View.LAYOUT_DIRECTION_LOCALE;
        private boolean userDefinedDirection = false;
        private Integer backgroundColor;
        private Integer textColor;
        private Integer actionTextColor;
        private Drawable icon;
        private AnimationType animationType = AnimationType.NONE;
        private boolean vibrateOnShow = false;
        private long vibrationDuration = 150;
        private boolean soundOnShow = false;
        private Integer soundResId;

        public Builder(View view) {
            this.view = view;
        }

        public Builder(Activity activity) {
            this.view = activity.findViewById(android.R.id.content);
        }

        /**
         * Sets the main message text.
         * @param message Message string.
         */
        public T message(String message) {
            this.message = message;
            return self();
        }

        /**
         * Sets the action button text.
         * @param actionText Text for the action button.
         */
        public T actionText(String actionText) {
            this.actionText = actionText;
            return self();
        }

        /**
         * Sets the duration to show the snackbar (in milliseconds).
         * @param durationMillis Duration in ms.
         */
        public T duration(int durationMillis) {
            this.duration = durationMillis;
            return self();
        }

        /**
         * Sets the layout direction (LTR or RTL).
         * @param direction View.LAYOUT_DIRECTION_LTR or RTL.
         */
        public T layoutDirection(int direction) {
            this.layoutDirection = direction;
            this.userDefinedDirection = true;
            return self();
        }

        /**
         * Sets the background color.
         * @param color Background color integer.
         */
        public T backgroundColor(int color) {
            this.backgroundColor = color;
            return self();
        }

        /**
         * Sets the message text color.
         * @param color Color integer.
         */
        public T textColor(int color) {
            this.textColor = color;
            return self();
        }

        /**
         * Sets the action text color.
         * @param color Color integer.
         */
        public T actionTextColor(int color) {
            this.actionTextColor = color;
            return self();
        }

        /**
         * Sets an icon to appear alongside the message.
         * @param icon Drawable to use.
         */
        public T icon(Drawable icon) {
            this.icon = icon;
            return self();
        }

        /**
         * Enables or disables animation, and sets the animation type.
         * @param animationType The type of animation to apply.
         */
        public T animation(AnimationType animationType) {
            this.animationType = animationType;
            return self();
        }

        /**
         * Enables vibration when showing the snackbar.
         * @param vibrate True to enable.
         */
        public T vibrateOnShow(boolean vibrate) {
            this.vibrateOnShow = vibrate;
            return self();
        }

        /**
         * Sets how long the vibration should last.
         * @param millis Duration in milliseconds.
         */
        public T vibrationDuration(long millis) {
            this.vibrationDuration = millis;
            return self();
        }

        /**
         * Enables sound when showing the snackbar.
         * @param sound True to enable.
         */
        public T soundOnShow(boolean sound) {
            this.soundOnShow = sound;
            return self();
        }

        /**
         * Sets a specific raw sound resource to play.
         * @param rawResId Resource ID of the sound.
         */
        public T sound(int rawResId) {
            this.soundResId = rawResId;
            return self();
        }

        protected abstract T self();
    }
}