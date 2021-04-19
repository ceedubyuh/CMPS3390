package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.biometrics.BiometricManager;

import java.util.Random;

public class Enemy01 implements GameObject{
    private final float dpi;
    private float x;
    private float y;
    private float ySpeed;
    private final float width;
    private final float height;
    private float health = 100;
    private final Bitmap enemy, enemy_left, enemy_right;
    private Bitmap curImg;
    private int screenWidth;
    private int screenHeight;
    private Paint paint = new Paint();

    public Enemy01(Resources res, float x, float y){
        dpi = res.getDisplayMetrics().densityDpi;
        screenHeight = res.getDisplayMetrics().heightPixels;
        screenWidth = res.getDisplayMetrics().widthPixels;
        enemy = BitmapFactory.decodeResource(res, R.mipmap.enemy01);
        enemy_left = BitmapFactory.decodeResource(res, R.mipmap.enemy01_left);
        enemy_right = BitmapFactory.decodeResource(res, R.mipmap.enemy01_right);
        curImg = enemy;
        width = curImg.getWidth();
        height = curImg.getHeight();
        this.x = x;
        this.y = y;
        ySpeed = 0.02f * dpi;
    }

    @Override
    public void update() {
        float xOff = (float)(0.01f * screenWidth * Math.sin(y / (0.04f * screenHeight)));
        x += xOff;
        curImg = xOff > 0 ?  enemy_left  : enemy_right;
        if(Math.abs(xOff) < 2) curImg = enemy;
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
