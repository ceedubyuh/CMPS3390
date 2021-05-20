// Carter, Zak , Edgar
// Testing
package com.example.snake;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

//

class NotTron extends SurfaceView implements Runnable {
    //Variables to get movement calculations.
    private float val1x, val2x, val1y, val2y;
    static final int MIN_DISTANCE = 150;
    private Thread thread = null;

    // To hold a reference to the Activity
    private Context context;

    private int p1_snake_crash = -1;
    private int p2_snake_crash = -1;
    private int p1_score = 0;
    private int p2_score = 0;

    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}


    // Start by heading to the right
    private Heading p1_heading = Heading.RIGHT;
    private Heading p2_heading = Heading.LEFT;
    // To hold the screen size in pixels
    private int screenX;
    private int screenY;
    // How long is the snake
    private int snakeLength;
    // max amount of x's = 6000 and y's = 6000;
    static private int MAX_LENGTH = 6000;

    // The size in pixels of a snake segment
    private final int blockSize;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 100;
    private int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;
    // Set's the speed of frames thus increasing speed as well.
    private final long FPS = 24;
    // There are 1000 milliseconds in a second
    private final long MILLIS_PER_SECOND = 1000;
// We will draw the frame much more often

    // How many points does the player have
    private int score;

    // The location in the grid of all the segments
    private int[] p1_snakeXs;
    private int[] p1_snakeYs;
    private int[] p2_snakeXs;
    private int[] p2_snakeYs;
    // Everything we need for drawing
    // Is the game currently playing?
    private volatile boolean isPlaying;

    // A canvas for our paint
    private Canvas canvas;

    // Required to use canvas
    private SurfaceHolder surfaceHolder;

    // Some paint for our canvas
    private Paint paint;
    private boolean dummy = false;

    public NotTron(Context context, Point size) {
        super(context);

        context = context;

        screenX = size.x;
        screenY = size.y;

        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenY / blockSize;


        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // If you score 200 you are rewarded with a crash achievement!
        // This is the max length's.
        // INITIALIZE DYNAMIC ARRAYS FOR p1 and p2 for X's and Y's
        p1_snakeXs = new int[MAX_LENGTH];
        p1_snakeYs = new int[MAX_LENGTH];

        p2_snakeXs = new int[MAX_LENGTH];
        p2_snakeYs = new int[MAX_LENGTH];
        // Start the game
        newGame();
    }
    @Override
    public void run() {
        if (dummy == true) {
            while (isPlaying) {

                // Update 10 times a second
                if (updateRequired()) {
                    update();
                    draw();
                }

            }
        }
    }

    public void dummy(){
        if(dummy == false) dummy = true;
        else dummy = false;
    }


    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
    public void newGame() {
        // Start with a single snake segment
        snakeLength = 1;
        //Determines where the Snake Spawns.
        p1_snakeXs[0] = (NUM_BLOCKS_WIDE / 2) - 20;
        p1_snakeYs[0] = (numBlocksHigh / 2) + 10;

        p2_snakeXs[0] = (NUM_BLOCKS_WIDE / 2) +20;
        p2_snakeYs[0] = (numBlocksHigh / 2) - 20;

        // Reset the score
        score = 0;

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    private void makeTrail(){
        // Increase the size of the snake
        snakeLength++;
    }
    // Handles Movement of snakes.
    private void moveSnake(){
        // Move the body
        for (int i = snakeLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            p1_snakeXs[i] = p1_snakeXs[i - 1];
            p1_snakeYs[i] = p1_snakeYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
            p2_snakeXs[i] = p2_snakeXs[i - 1];
            p2_snakeYs[i] = p2_snakeYs[i - 1];

        }

        // Move the head in the appropriate heading
        switch (p1_heading) {
            case UP:
                p1_snakeYs[0]--;
                break;

            case RIGHT:
                p1_snakeXs[0]++;
                break;

            case DOWN:
                p1_snakeYs[0]++;
                break;

            case LEFT:
                p1_snakeXs[0]--;
                break;
        }

        switch (p2_heading) {
            case UP:
                p2_snakeYs[0]--;
                break;

            case RIGHT:
                p2_snakeXs[0]++;
                break;

            case DOWN:
                p2_snakeYs[0]++;
                break;

            case LEFT:
                p2_snakeXs[0]--;
                break;
        }
    }


    // dir is direction 0 = Up, 1 = Down, 2 = Left, 3 = Right
    private int dir = 1;

    private void snake_ai(){
        // da_binky is the dot in front of  p2_snake that detects for lines or collisions
        // to add more difficulty to the game we can set da_binky closer to head of the snake
        // to make it easier set da_binky further away from the snake.
        // Default direction is (Down) "DEFAULT DIRECTION IS TO CHANGE"
        int da_binky = p2_snakeYs[0] - 1;
        // dir is direction 0 = Up, 1 = Down, 2 = Left, 3 = Right
        // int dir = 1;
        switch (p2_heading) {
            case UP:
                da_binky = p2_snakeYs[0] - 1;
                dir = 0;
                break;
            case DOWN:
                da_binky = p2_snakeYs[0] + 1;
                dir = 1;
                break;
            case LEFT:
                da_binky = p2_snakeXs[0] - 1;
                dir = 3;
                break;
            case RIGHT:
                da_binky = p2_snakeXs[0] + 1;
                dir = 4;
                break;
        }

        Random rand = new Random();
        int rand_o2 = rand.nextInt(2);
        // detection is defaulted to 0 [no collision to occur], 1 collision from UP or DOWN, 2 collision from left or right,
        int detection = 0;
        if(dir <= 1){
            if((da_binky == 0) ||
                    (da_binky == NUM_BLOCKS_WIDE )|| /*+9 bound may change*/
                    (da_binky == numBlocksHigh - 4)){ detection = 1;
            }

            for( int i = snakeLength - 1; i > 0; i--){
                if((da_binky == p1_snakeXs[i] && da_binky == p1_snakeYs[i]) ||
                        (da_binky == p2_snakeXs[i] && da_binky == p2_snakeYs[i])){
                    detection = 1;
                    break;
                }
            }


            if(detection == 1) {
                if (rand_o2 == 0) p2_heading = Heading.LEFT;
                if (rand_o2 == 1) p2_heading = Heading.RIGHT;
            }
        }
        if(dir >= 2){
            if((da_binky == 0) ||
                    (da_binky == NUM_BLOCKS_WIDE )|| /*+9 bound may change*/
                    (da_binky == numBlocksHigh - 4)){ detection = 2;
            }

            for( int i = snakeLength - 1; i > 0; i--) {
                if ((da_binky == p1_snakeXs[i] && da_binky == p1_snakeYs[i]) ||
                        (da_binky == p2_snakeXs[i] && da_binky == p2_snakeYs[i])) {
                    detection = 2;
                    break;
                }
            }

            if(detection == 2) {
                if (rand_o2 == 0) p2_heading = Heading.UP;
                if (rand_o2 == 1) p2_heading = Heading.DOWN;
            }
        }
        else{}
    }

    private int detectDeath(){
        // Has the snake died?
        int dead = 0;

        // Hit the screen edge
        if (p1_snakeXs[0] == -1) dead = 1;
        if (p1_snakeXs[0] >= NUM_BLOCKS_WIDE + 9) dead = 1;
        if (p1_snakeYs[0] == -1 ) dead = 1;
        if (p1_snakeYs[0] == numBlocksHigh - 4) dead = 1;


        // Did P1 Eaten itself or did it cross another snake?
        for (int i = snakeLength - 1; i > 0; i--) {
            if (/*(i > 4) && */ ((p1_snakeXs[0] == p1_snakeXs[i]) && (p1_snakeYs[0] == p1_snakeYs[i])) ||
                    ((p1_snakeXs[0] == p2_snakeXs[i]) && (p1_snakeYs[0] == p2_snakeYs[i]))) {
                dead = 1;
            }
        }

        if (p2_snakeXs[0] == -1) dead = 2;
        if (p2_snakeXs[0] >= NUM_BLOCKS_WIDE + 9) dead = 2;
        if (p2_snakeYs[0] == -1) dead = 2;
        if (p2_snakeYs[0] == numBlocksHigh - 4) dead =2;

        // Did P2 Eaten itself or did it cross another snake?
        for (int i = snakeLength - 1; i > 0; i--) {
            if (/*(i > 4) &&*/ ((p2_snakeXs[0] == p2_snakeXs[i]) && (p2_snakeYs[0] == p2_snakeYs[i])) ||
                    (p2_snakeXs[0] == p1_snakeXs[i]) && (p2_snakeYs[0] == p1_snakeYs[i])) {
                dead = 2;
            }
        }
        return dead;
    }

    public void update() {
        // Constantly update the snakes trail
        makeTrail();
        // Update snake movements
        moveSnake();
        // snake_ai gives a set chance for p2(the bot) to move in another direction before dying.
        snake_ai();
        //p1 died p2 score

        if (detectDeath() == 1) {
            //start again
            newGame();
            p2_score++;
        }
        //p2 died p1 score
        if (detectDeath() == 2) {
            newGame();
            p1_score++;
        }
    }

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            // Fill the screen with Game Code School blue
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            // Set the color of the paint to draw the snake white
            paint.setColor(Color.argb(255, 0, 150, 255));
            // Scale the HUD text
            paint.setTextSize(60);
            // Debug: Displaying Length: To see if it crashes at n length
            canvas.drawText("P1:" + p1_score, 10, 70, paint);
            paint.setColor(Color.argb(255, 255, 0, 0));
            canvas.drawText("P2:" + p2_score, 400, 70, paint);
            // Draw the snake one block at a time
            paint.setColor(Color.argb(255, 0, 150, 255));
            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(p1_snakeXs[i] * blockSize,
                        (p1_snakeYs[i] * blockSize),
                        (p1_snakeXs[i] * blockSize) + blockSize,
                        (p1_snakeYs[i] * blockSize) + blockSize,
                        paint);

            }
            // Set the color of the paint to draw P2 red
            paint.setColor(Color.argb(255, 255, 0, 0));
            for(int i = 0; i < snakeLength; i++) {
                canvas.drawRect(p2_snakeXs[i] * blockSize,
                        (p2_snakeYs[i] * blockSize),
                        (p2_snakeXs[i] * blockSize) + blockSize,
                        (p2_snakeYs[i] * blockSize) + blockSize,
                        paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed
            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }
    // Swipe Controls
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float deltaY, deltaX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch(p1_heading) {
                    case UP:
                        val1x = event.getX();
                        break;
                    case DOWN:
                        val1x = event.getX();
                        break;
                    case LEFT:
                        val1y = event.getY();
                        break;
                    case RIGHT:
                        val1y = event.getY();
                        break;
                }
                //String x = Float.toString(val1x);
                //String y = Float.toString(val1y);
                //Toast.makeText(this, "X:" +x + "Y:" + y, Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                switch (p1_heading) {
                    case UP:
                        val2x = event.getX();
                        if (val1x > val2x) {
                            deltaX = val1x - val2x;
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                p1_heading = Heading.LEFT;
                                break;
                            }
                        }
                        if (val1x < val2x) {
                            deltaX = val2x - val1x;
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                p1_heading = Heading.RIGHT;
                                break;
                            }
                        }
                    case DOWN:
                        val2x = event.getX();
                        if (val1x > val2x) {
                            deltaX = val1x - val2x;
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                p1_heading = Heading.LEFT;
                                break;
                            }
                        }
                        if (val1x < val2x) {
                            deltaX = val2x - val1x;
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                p1_heading = Heading.RIGHT;
                                break;
                            }
                        }

                    case LEFT:
                        val2y = event.getY();
                        if (val1y > val2y) {
                            deltaY = val1y - val2y;
                            if (Math.abs(deltaY) > MIN_DISTANCE) {
                                p1_heading = Heading.UP;
                                break;
                            }
                        }
                        if (val1y < val2y) {
                            deltaX = val2y - val1y;
                            if (Math.abs(deltaX) > MIN_DISTANCE) {
                                p1_heading = Heading.DOWN;
                                break;
                            }
                        }

                    case RIGHT:
                        val2y = event.getY();
                        if (val1y > val2y) {
                            deltaY = val1y - val2y;
                            if (Math.abs(deltaY) > MIN_DISTANCE) {
                                p1_heading = Heading.UP;
                                break;
                            }
                        }
                        if (val1y < val2y) {
                            deltaY = val2y - val1y;
                            if (Math.abs(deltaY) > MIN_DISTANCE) {
                                p1_heading = Heading.DOWN;
                                break;
                            }
                        }
                }
        }
        return true;
    }
}

