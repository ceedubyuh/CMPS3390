package edu.csub.startracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{
    private final Background background1;
    private final Background background2;
    private boolean isPlaying = true;
    private Thread thread;
    private int touchX, touchY;
    private final Player player;
    private EnemySpawner spawner;
    private ArrayList<Laser> lasers;
    private ArrayList<GameObject> enemies;
    private GameActivity gameActivity;
    private final float screenWidth, screenHeight;
    private Paint textPaint = new Paint();

    public GameView(GameActivity context, int screenX, int screenY) {
        super(context);
        Resources res = getResources();
        screenWidth = res.getDisplayMetrics().widthPixels;
        screenHeight = res.getDisplayMetrics().heightPixels;
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(screenWidth * 0.1f);
        background1 = new Background(screenX, screenY, res);
        background2 = new Background(screenX, screenY, res);
        background2.setY(screenY);
        player = new Player(res);
        spawner = new EnemySpawner(res);
        lasers = player.getLasers();
        spawner = new EnemySpawner(res);
        lasers = player.getLasers();
        enemies = spawner.getEnemies();
        gameActivity = context;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        touchX = (int)event.getX();
        touchY = (int)event.getY();
        return true;
    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void draw(){
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            background1.draw(canvas);
            background2.draw(canvas);
            if(!player.isAlive()){
                canvas.drawText("GAME OVER", screenWidth / 4f, screenHeight / 2f, textPaint);
            }
            player.draw(canvas);
            spawner.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        background1.update();
        background2.update();
        player.updateTouch(touchX, touchY);
        player.update();
        spawner.update();
        checkAllCollisions();
        checkEnemiesOffScreen();
    }

    private void checkEnemiesOffScreen() {
        for(GameObject go : enemies){
            if(go.getY() > screenHeight){
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    private void checkAllCollisions() {
        for(Laser laser : lasers){
            for(GameObject go : enemies){
                if(checkCollision(laser, go)){
                    laser.takeDamage(100);
                    go.takeDamage(25);
                }
            }
        }
        for(GameObject go : enemies){
            if(checkCollision(player, go)){
                player.takeDamage(100);
                go.takeDamage(100);
                gameActivity.gameOver();
            }
        }
    }

    private boolean checkCollision(GameObject g1, GameObject g2){
        return g1.getX() < g2.getX() + g2.getWidth() &&
                g1.getX() + g1.getWidth() > g2.getX() &&
                g1.getY() < g2.getY() + g2.getHeight() &&
                g1.getY() + g1.getHeight() > g2.getY();
    }

    public void pause(){
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
}
