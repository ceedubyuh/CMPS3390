package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Laser {
    private float x, y;
    private Bitmap laser;
    private float dpi;
    private Paint paint = new Paint();

    /**
     * Main function for the Laser class that sets its image to the correct mipmap
     * @param res
     */
    public Laser(Resources res) {
        laser = BitmapFactory.decodeResource(res, R.mipmap.bullet);
        dpi = res.getDisplayMetrics().densityDpi;
    }

    /**
     * Boolean function to check if the laser is on or off screen.
     * @return true if it is on screen, false if not.
     */
    public boolean isOnScreen(){
        return !(y < getHeight());
    }

    /**
     * Function that updates the image
     */
    public void update(){
        y -= 0.1f * dpi;
    }

    /**
     * Function that draws the Bitmap to the canvas/
     * @param canvas
     */
    public void draw(Canvas canvas){
        canvas.drawBitmap(laser, this.x, this.y, this.paint);
    }

    /**
     * Function that gets the mid point of the laser image
     * @return returns the mid point of X
     */
    public float getMidX(){
        return laser.getWidth() / 2f;
    }

    /**
     * Function that returns the height of the laser image
     * @return the height
     */
    public float getHeight(){
        return laser.getHeight();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
