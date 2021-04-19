package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Main function for the Player class that initializes all variables and draws the ship images
 * to the canvas.
 */
public class Player implements GameObject{
    private float x, y, prevX, prevY;
    private final Bitmap playerImg;
    private final Bitmap playerLeft;
    private final Bitmap playerRight;
    private Bitmap curImg;
    private Paint paint = new Paint();
    private final float dpi;
    private int frameTicks = 0, shotTicks = 0;
    private final Resources res;
    private final int width, height;

    ArrayList<Laser> lasers = new ArrayList<>();
    private float health = 100f;

    /**
     * Function to draw each image of the Player's ship. Turning left, right or center.
     * @param res
     */
    public Player(Resources res) {
        this.res = res;
        playerImg = BitmapFactory.decodeResource(res, R.mipmap.player);
        playerLeft = BitmapFactory.decodeResource(res, R.mipmap.player_left);
        playerRight = BitmapFactory.decodeResource(res, R.mipmap.player_right);
        curImg = playerImg;
        width = curImg.getWidth();
        height = curImg.getHeight();
        DisplayMetrics dm = res.getDisplayMetrics();
        dpi = dm.densityDpi;
        x = (dm.widthPixels / 2f) - (playerImg.getWidth() / 2f);
        y = (dm.heightPixels * 0.75f);
    }

    public void updateTouch(int touchX, int touchY){
        if(touchX > 0 && touchY > 0){
            this.x = touchX - (playerImg.getWidth() / 2f);
            this.y = touchY - (playerImg.getHeight() * 2f);
        }
    }

    /**
     * Function to update the Player ship image when it is moving.
     * Function also updates each laser fired by the ship and removes them when they are off screen.
     */
    @Override
    public void update(){
        if(health <= 0){
            return;
        }
        if(Math.abs(x - prevX) < 0.04 * dpi){
            frameTicks++;
        } else{
            frameTicks = 0;
        }

        if(this.x < prevX - 0.04 * dpi){
            curImg = playerLeft;
        } else if(this.x > prevX + 0.04 * dpi){
            curImg = playerRight;
        } else if(frameTicks > 5){
            curImg = playerImg;
        }

        prevX = x;
        prevY = y;

        shotTicks++;
        if(shotTicks >= 14){
            Laser tmp = new Laser(this.res);
            tmp.setX(x + playerImg.getWidth() / 2f - tmp.getMidX());
            tmp.setY(y - tmp.getHeight() / 2f);
            lasers.add(tmp);
            shotTicks = 0;
        }

        for(Iterator<Laser> iterator = lasers.iterator(); iterator.hasNext();){
            Laser laser = iterator.next();
            if(!laser.isOnScreen() || !laser.isAlive()){
                iterator.remove();
            }
        }

        for(Laser laser : lasers){
                laser.update();
            }
    }

    public void draw(Canvas canvas){
        if(health <= 0){
            return;
        }
        canvas.drawBitmap(curImg, this.x, this.y, this.paint);
        for(Laser laser : lasers) {
            laser.draw(canvas);
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public boolean isAlive() {
        return health > 0f;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float takeDamage(float damage) {
        return health -= damage;
    }

    @Override
    public float addHealth(float rAmount) {
        return health += rAmount;
    }

    public ArrayList<Laser> getLasers(){
        return lasers;
    }
}
