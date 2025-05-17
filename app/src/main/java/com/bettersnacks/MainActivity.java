package com.bettersnacks;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.better_snacks.TimedSnackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TimedSnackbar.Builder builder = new TimedSnackbar.Builder(this)
                .message("שומר נתונים...")
                .actionText("בטל")
                .onAction(() -> {
                    // Logic to execute when the cancel button is clicked
                    Log.i("BetterSnacks", "Saving cancelled");
                })
                .onTimeout(() -> {
                    // Code to run when the snackbar times out
                    Toast.makeText(this, "Data saved automatically", Toast.LENGTH_SHORT).show();
                })
                .duration(5000) // Show for 5 seconds
                .layoutDirection(View.LAYOUT_DIRECTION_RTL) // Example for RTL layout
                .backgroundColor(Color.BLACK)
                .textColor(Color.GREEN)
                .actionTextColor(Color.RED);

        TimedSnackbar timedSnackbar = builder.border(Color.WHITE, 3).build();

        timedSnackbar.show();
    }
}