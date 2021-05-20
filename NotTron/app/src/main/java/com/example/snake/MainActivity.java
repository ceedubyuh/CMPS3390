package com.example.snake;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    NotTron NotTronEngine;
    boolean secondViewOpened = false;
    //12/4/20 added fullscreen functionality for newer Androids, but still works with old APIs
    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        NotTronEngine = new NotTron(this, size);
        FullScreencall();
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button btn = findViewById(R.id.credits);
        Button btn2 = findViewById(R.id.start);



        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("credits", "Button log Message");
                Toast.makeText(getApplicationContext(), "Zachary Scholefield\n" +
                                "Edgar Bacallo\nCarter Womack",
                        Toast.LENGTH_LONG).show();
            }

        });
        //Added a button to start game - Zak
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotTronEngine.dummy();
                NotTronEngine.resume();
                setContentView(NotTronEngine);
                secondViewOpened = true;
            }
        });
    }

    //Test to see if configuration is working
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        // super.onBackPressed();  // optional depending on your needs
        if(secondViewOpened){
            NotTronEngine.dummy();
            NotTronEngine.pause();
            secondViewOpened = false;
            setContentView(R.layout.activity_main);
        }

        Button btn = findViewById(R.id.credits);
        Button btn2 = findViewById(R.id.start);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("credits", "Button log Message");
                Toast.makeText(getApplicationContext(), "Zachary Scholefield\n" +
                                "Edgar Bacallo\nCarter Womack",
                        Toast.LENGTH_LONG).show();
            }

        });
        //Added a button to start game - Zak
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotTronEngine.dummy();
                NotTronEngine.resume();
                setContentView(NotTronEngine);
                secondViewOpened = true;
            }
        });
    }


}
