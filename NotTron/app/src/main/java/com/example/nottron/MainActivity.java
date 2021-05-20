package com.example.nottron;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main driver class for NotTron
 */
public class MainActivity extends AppCompatActivity {
    NotTron NotTronEngine;
    boolean secondViewOpened = false;

    /**
     * Function to make the game fullscreen on all (most) Android APIs
     */
    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * Function to create the buttons on the home screen, and whenever the player returns to the
     * home screen
     */
    public void createButtons(){
        Button btn = findViewById(R.id.credits);
        Button btn2 = findViewById(R.id.start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("credits", "Button log Message");
                Toast.makeText(getApplicationContext(), "Carter Womack",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotTronEngine.fixGame();
                NotTronEngine.resume();
                setContentView(NotTronEngine);
                secondViewOpened = true;
            }
        });
    }

    /**
     * OnCreate function to initialize the game's state, create the home screen
     * buttons and start the NotTronEngine
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        NotTronEngine = new NotTron(this, size);
        FullScreencall();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        createButtons();

    }
    @Override
    protected void onResume() {
        super.onResume();
        NotTronEngine.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        NotTronEngine.pause();
    }
    /**
     * onBackPressed function to pause the game when the back button on the device is pressed
     * and go back to the home screen. Saves game state, does not restart the game.
     */
    @Override
    public void onBackPressed()
    {
        if(secondViewOpened){
            NotTronEngine.fixGame();
            NotTronEngine.pause();
            secondViewOpened = false;
            setContentView(R.layout.activity_main);
        }
        createButtons();
    }


}
