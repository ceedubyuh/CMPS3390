package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Background class that draws the background and creates the looping visuals
 */
public class Background {
    private Bitmap background;
    private int screenX, screenY;
    private Paint paint = new Paint();
    private float dpi;
    private float x = 0f, y = 0f;

    /**
     * Getter for X pos of the background
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Setter for X pos of the background
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /** Getter for Y pos of the background
     * @return y
     */
    public float getY() {
        return y;
    }

    /** Setter for Y pos of the background
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Function to set the size of the background according to the dpi of the phone it is running on
     * @param screenX
     * @param screenY
     * @param res
     */
    public Background(int screenX, int screenY, Resources res){
        this.screenX = screenX;
        this.screenY = screenY;
        this.background = BitmapFactory.decodeResource(res, R.mipmap.background);
        this.background = Bitmap.createScaledBitmap(this.background, screenX, screenY, false);
        this.dpi = res.getDisplayMetrics().densityDpi;

    }

    /**
     * Function to update the screen as it scrolls down.
     */
    public void update() {
        this.y += 0.006f * dpi;

        if(this.y > screenY){
            this.y = -screenY;
        }
    }

    /**
     * Function to draw the background to the canvas.
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.background, this.x, this.y, paint);
    }
}
