package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Enemy02 implements GameObject{
    private float x, y, ySpeed;
    private float health = 100;
    Bitmap enemy;
    Bitmap enemyFast;
    Bitmap curImg;
    private final int screenWidth, screenHeight, dpi;
    private final int width, height;
    private Paint paint = new Paint();
    private int frameTick, launchTick;

    public Enemy02(Resources res, float x, float y){
        dpi = res.getDisplayMetrics().densityDpi;
        screenHeight = res.getDisplayMetrics().heightPixels;
        screenWidth = res.getDisplayMetrics().widthPixels;
        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy02);
        enemyFast = BitmapFactory.decodeResource(res, R.mipmap.enemy02_fast);
        curImg = enemy;
        width = curImg.getWidth();
        height = curImg.getHeight();
        this.x = x;
        this.y = y;
        ySpeed = 0.01f * dpi;
        launchTick  = new Random().nextInt(120-30) + 30;
    }

    @Override
    public void update() {
        frameTick++;
        if(frameTick == launchTick){
            curImg = enemyFast;
            ySpeed *= 4f;
        }
        y += ySpeed;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(curImg, x, y, paint);
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
}
