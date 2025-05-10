package com.better_snacks;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class InfoSnackbar extends BaseSnackbar {

    private final Runnable onConfirm;

    private InfoSnackbar(Builder builder) {
        super(builder);
        this.onConfirm = builder.onConfirm;
    }

    @Override
    public void show() {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(actionText, v -> {
            if (onConfirm != null) onConfirm.run();
        });
        applyVisuals(snackbar);
    }

    public static class Builder extends BaseSnackbar.Builder<Builder> {
        private Runnable onConfirm;

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
            actionText("Confirm");
            message("For your information");
        }

        /**
         * Sets the confirmation action to be performed if the user clicks "Confirm".
         * @param onConfirm A runnable representing the confirm logic.
         */
        public Builder onConfirm(Runnable onConfirm) {
            this.onConfirm = onConfirm;
            return this;
        }

        /**
         * Builds the Snackbar to be used or shown later.
         */
        public InfoSnackbar build() {
            return new InfoSnackbar(this);
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
