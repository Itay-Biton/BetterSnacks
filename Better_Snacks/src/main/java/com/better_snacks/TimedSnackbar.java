package com.better_snacks;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class TimedSnackbar extends BaseSnackbar {

    private final Runnable onAction;
    private final Runnable onTimeout;

    private TimedSnackbar(Builder builder) {
        super(builder);
        this.onAction = builder.onAction;
        this.onTimeout = builder.onTimeout;
    }

    @Override
    public void show() {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(actionText, v -> {
            if (onAction != null) onAction.run();
        });

        if (onTimeout != null) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (snackbar.isShown()) onTimeout.run();
            }, duration);
        }
        applyVisuals(snackbar);
    }

    public static class Builder extends BaseSnackbar.Builder<Builder> {
        private Runnable onAction;
        private Runnable onTimeout;

        /**
         * Initializes builder with a view.
         * @param view Anchor view for the snackbar.
         */
        public Builder(View view) {
            super(view);
            setPreset();
        }

        /**
         * Initializes builder with an activity root view.
         * @param activity Activity to extract the root view.
         */
        public Builder(Activity activity) {
            super(activity);
            setPreset();
        }

        private void setPreset() {
            actionText("Abort");
            message("applying changes in 3 seconds");
            duration(3000);
        }

        /**
         * Sets the action to be performed if the user clicks the button.
         * @param onAction A runnable representing an action.
         */
        public Builder onAction(Runnable onAction) {
            this.onAction = onAction;
            return this;
        }

        /**
         * Sets the action to be run after the snackbar times out.
         * @param onTimeout The timeout logic.
         */
        public Builder onTimeout(Runnable onTimeout) {
            this.onTimeout = onTimeout;
            return this;
        }

        /**
         * Builds the Snackbar to be used or shown later.
         */
        public TimedSnackbar build() {
            return new TimedSnackbar(this);
        }

        /**
         * Builds and shows the Snackbar.
         */
        public void show() {
            build().show();
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
