package edu.csub.startracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private HighScore highScore = HighScore.getInstance();

    /**
     * Main acvitity for Star Tracker. Draws initial game window.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getTopScores(10);
        TextView tvHighScore = findViewById(R.id.tvHighScore);
        EditText editTextPlayerName = findViewById(R.id.editTextPlayerName);
        editTextPlayerName.setText(highScore.getPlayerName());
        tvHighScore.setText(String.format("High Score: %s",  highScore.getHighScore()));
        if(highScore.getHighScore() != 0 || highScore.getHighScore() == highScore.getCurScore()) {
            highScore.postHighScore();
        }

    }

    private void getTopScores(int howMany) {
        ListView highScores = findViewById(R.id.lvTopScores);
        highScore.getHighScores(howMany, highScores, this);
    }

    /**
     * Starts GameActivity when play button is pressed
     * @param view GameActivity view
     */
    public void onPlayButtonClicker(View view) {
        highScore.resetCurScore();
        EditText editTextPlayerName = findViewById(R.id.editTextPlayerName);
        highScore.setPlayerName(editTextPlayerName.getText().toString());
        startActivity(new Intent(MainActivity.this, GameActivity.class));

    }
}