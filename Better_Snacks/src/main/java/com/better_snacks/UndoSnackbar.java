package com.better_snacks;

import android.app.Activity;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class UndoSnackbar extends BaseSnackbar {

    private final Runnable onUndo;

    private UndoSnackbar(Builder builder) {
        super(builder);
        this.onUndo = builder.onUndo;
    }

    @Override
    public void show() {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        if (onUndo != null)
            snackbar.setAction(actionText, v -> onUndo.run());
        applyVisuals(snackbar);
    }

    public static class Builder extends BaseSnackbar.Builder<Builder> {
        private Runnable onUndo;

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
            actionText("Undo");
            message("Changed your mind?");
            duration(4000);
        }

        /**
         * Sets the undo action to be performed if the user clicks "Undo".
         * @param onUndo A runnable representing the undo logic.
         */
        public Builder onUndo(Runnable onUndo) {
            this.onUndo = onUndo;
            return this;
        }

        /**
         * Builds the Snackbar to be used or shown later.
         */
        public UndoSnackbar build() {
            return new UndoSnackbar(this);
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