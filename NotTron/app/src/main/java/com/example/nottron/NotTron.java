package com.example.nottron;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

class NotTron extends SurfaceView implements Runnable {
    //Variables to get movement calculations.
    private float val1x;
    private float val1y;
    static final int MIN_DISTANCE = 150;
    private Thread thread = null;

    // To hold a reference to the Activity
    private int p1_score = 0;
    private int p2_score = 0;
    private int score;
    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}

    // Start by heading to the right
    private Heading p1_heading = Heading.UP;
    private Heading p2_heading = Heading.DOWN;
    // How long is the bike
    private int bikeLength;
    // max amount of x's = 6000 and y's = 6000;
    static private int MAX_LENGTH = 6000;

    // The size in pixels of a bike segment
    private final int blockSize;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 180; //100
    private final int numBlocksHigh;

    // Control pausing between updates
    private long nextFrameTime;

    // INITIALIZE DYNAMIC ARRAYS FOR p1 and p2 for X's and Y's
    // The location in the grid of all the segments
    private int[] p1_bikeXs = new int[MAX_LENGTH];
    private int[] p1_bikeYs = new int[MAX_LENGTH];
    private int[] p2_bikeXs = new int[MAX_LENGTH];
    private int[] p2_bikeYs = new int[MAX_LENGTH];
    private boolean isPlaying;
    // Required to use canvas
    private final SurfaceHolder surfaceHolder;
    // Some paint for our canvas
    private final Paint paint;
    private boolean gameUnbreaker = false;

    public NotTron(Context context, Point size) {
        super(context);
        // To hold the screen size in pixels
        int screenX = size.x;
        int screenY = size.y;
        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenY / blockSize;
        // Initialize the drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        // Start the game
        newGame();
    }

    /**
     * Override run function that runs the game while the booleans are true.
     */
    @Override
    public void run() {
        if (gameUnbreaker) {
            while (isPlaying) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                    draw();
                }

            }
        }
    }

    /**
     * Awful solution that I don't understand to an issue that I don't understand.
     * Without this acting as a buffer (?) to the game's refresh rate, the game would break.
     * Setting a boolean to false and true over and over again fixed it...? No clue. Don't care.
     * Wanted to break something.
     */
    public void fixGame(){
      if(!gameUnbreaker){
          gameUnbreaker = true;
      }
        else gameUnbreaker = false;
    }

    /**
     * Function that pauses the thread the game is running on.
     * Called when the back button is pressed.
     */
    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resumes the thread and game when returned to the game screen.
     */
    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Main function to initialize new game instance.
     * Sets bike positions and resets score.
     */
    public void newGame() {
        // Start with a single bike segment
        bikeLength = 1;
        //Determines where the bike spawns.
        p1_bikeXs[0] = (NUM_BLOCKS_WIDE / 2) - 20;
        p1_bikeYs[0] = (numBlocksHigh / 2) + 10;
        p2_bikeXs[0] = (NUM_BLOCKS_WIDE / 2) + 20;
        p2_bikeYs[0] = (numBlocksHigh / 2) - 20;
        // Reset the score
        score = 0;
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * Simple function to iterate the length of each bike.
     */
    private void makeTrail(){
        // Increase the size of the bike
        bikeLength++;
    }

    /**
     * Function that tracks the movement of each bike and iterates its dynamic array
     */
    private void moveBike(){
        // Move the body
        for (int i = bikeLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            p1_bikeXs[i] = p1_bikeXs[i - 1];
            p1_bikeYs[i] = p1_bikeYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
            p2_bikeXs[i] = p2_bikeXs[i - 1];
            p2_bikeYs[i] = p2_bikeYs[i - 1];

        }
        // Move the head in the appropriate heading
        switch (p1_heading) {
            case UP:
                p1_bikeYs[0]--;
                break;

            case RIGHT:
                p1_bikeXs[0]++;
                break;

            case DOWN:
                p1_bikeYs[0]++;
                break;

            case LEFT:
                p1_bikeXs[0]--;
                break;
        }

        switch (p2_heading) {
            case UP:
                p2_bikeYs[0]--;
                break;

            case RIGHT:
                p2_bikeXs[0]++;
                break;

            case DOWN:
                p2_bikeYs[0]++;
                break;

            case LEFT:
                p2_bikeXs[0]--;
                break;
        }
    }
    // dir is direction 0 = Up, 1 = Down, 2 = Left, 3 = Right
    private int dir = 1;

    /**
     * Function to handle the "ai" of the second player.
     */
    private void bikeAi(){
        // tracker is the dot in front of p2_bike that detects for lines or collisions
        // to add more difficulty to the game we can set tracker closer to head of the bike
        // to make it easier set tracker further away from the bike.
        // Default direction is (Down) "DEFAULT DIRECTION IS TO CHANGE"
        int tracker = p2_bikeYs[0];
        // dir is direction 0 = Up, 1 = Down, 2 = Left, 3 = Right
        // int dir = 1;
        switch (p2_heading) {
            case UP:
                tracker = p2_bikeYs[0] - 1;
                dir = 0;
                break;
            case DOWN:
                tracker = p2_bikeYs[0] + 1;
                dir = 1;
                break;
            case LEFT:
                tracker = p2_bikeXs[0] - 1;
                dir = 3;
                break;
            case RIGHT:
                tracker = p2_bikeXs[0] + 1;
                dir = 4;
                break;
        }
        Random rand = new Random();
        int rand_o2 = rand.nextInt(2);
        // detection is defaulted to 0 [no collision to occur], 1 collision from UP or DOWN, 2 collision from left or right,
        int detection = 0;
        if(dir <= 1){
            if((tracker == 0) ||
                    (tracker == NUM_BLOCKS_WIDE )||
                    (tracker == numBlocksHigh)){ detection = 1;
            }
            for( int i = bikeLength - 1; i > 0; i--){
                if((tracker == p1_bikeXs[i] && tracker == p1_bikeYs[i]) ||
                        (tracker == p2_bikeXs[i] && tracker == p2_bikeYs[i])){
                    detection = 1;
                    break;
                }
            }
            if(detection == 1) {
                if (rand_o2 == 0){
                    p2_heading = Heading.LEFT;
                }
                else p2_heading = Heading.LEFT;
            }
        }
        if(dir >= 2){
            if((tracker == 0) ||
                    (tracker == NUM_BLOCKS_WIDE )||
                    (tracker == numBlocksHigh)){ detection = 2;
            }
            for( int i = bikeLength - 1; i > 0; i--) {
                if ((tracker == p1_bikeXs[i] && tracker == p1_bikeYs[i]) ||
                        (tracker == p2_bikeXs[i] && tracker == p2_bikeYs[i])) {
                    detection = 2;
                    break;
                }
            }
            if(detection == 2) {
                if (rand_o2 == 0){
                    p2_heading = Heading.UP;
                }
                else p2_heading = Heading.DOWN;
            }
        }
    }

    /**
     * Function to detect if a bike has hit a wall or another bike.
     * @return dead; variable that determines which bike died.
     */
    private int detectDeath(){
        // Has the bike died?
        int dead = 0;
        // Hit the screen edge
        if (p1_bikeXs[0] == -1) dead = 1;
        if (p1_bikeXs[0] >= NUM_BLOCKS_WIDE) dead = 1;
        if (p1_bikeYs[0] == -1 ) dead = 1;
        if (p1_bikeYs[0] == numBlocksHigh) dead = 1;
        //Set dead to 1, when dead = 1, point to P2
        // Did P1 hit itself or did it cross another bike?
        for (int i = bikeLength - 1; i > 0; i--) {
            if (((p1_bikeXs[0] == p1_bikeXs[i]) && (p1_bikeYs[0] == p1_bikeYs[i])) ||
                    ((p1_bikeXs[0] == p2_bikeXs[i]) && (p1_bikeYs[0] == p2_bikeYs[i]))) {
                dead = 1;
            }
        }
        if (p2_bikeXs[0] == -1) dead = 2;
        if (p2_bikeXs[0] >= NUM_BLOCKS_WIDE) dead = 2;
        if (p2_bikeYs[0] == -1) dead = 2;
        if (p2_bikeYs[0] == numBlocksHigh) dead = 2;
        // Did P2 hit itself or did it cross another bike?
        for (int i = bikeLength - 1; i > 0; i--) {
            if (((p2_bikeXs[0] == p2_bikeXs[i]) && (p2_bikeYs[0] == p2_bikeYs[i])) ||
                    (p2_bikeXs[0] == p1_bikeXs[i]) && (p2_bikeYs[0] == p1_bikeYs[i])) {
                dead = 2;
            }
        }
        return dead;
    }

    /**
     * Update function that updates the bike's positions, trails and scores
     */
    public void update() {
        // Constantly update the bikes trail
        makeTrail();
        // Update bike movements
        moveBike();
        // bike_ai gives a set chance for p2 to move in another direction before dying.
        bikeAi();
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

    /**
     * Function that draws all the text and bikes on screen. Janky, but it works.
     */
    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            // A canvas for our paint
            Canvas canvas = surfaceHolder.lockCanvas();
            // Fill the screen with black
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            // Set the color of the paint to draw the bike
            // Scale the HUD text and color text
            paint.setTextSize(60);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawText("P1:" + p1_score, 400, 70, paint);
            // Paint P2 text
            paint.setColor(Color.argb(255, 255, 154, 0));
            canvas.drawText("P2:" + p2_score, 2000, 70, paint);
            // Draw the bike one block at a time
            //Paint bike 1
            paint.setColor(Color.argb(255, 255, 255, 255));
            for (int i = 0; i < bikeLength; i++) {
                canvas.drawRect(p1_bikeXs[i] * blockSize,
                        (p1_bikeYs[i] * blockSize),
                        (p1_bikeXs[i] * blockSize) + blockSize,
                        (p1_bikeYs[i] * blockSize) + blockSize,
                        paint);
            }
            // Set the color of the paint to draw P2 red
            paint.setColor(Color.argb(255, 255, 154, 0));
            for(int i = 0; i < bikeLength; i++) {
                canvas.drawRect(p2_bikeXs[i] * blockSize,
                        (p2_bikeYs[i] * blockSize),
                        (p2_bikeXs[i] * blockSize) + blockSize,
                        (p2_bikeYs[i] * blockSize) + blockSize,
                        paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Boolean function that returns true if the frametime is less than or equal to the current
     * system time. Creates the game's framerate and update speed.
     * @return
     */
    public boolean updateRequired() {
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed
            // Setup when the next update will be triggered
            long FPS = 30;
            long MILLIS_PER_SECOND = 1000;
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            // Return true so that the update and draw
            // functions are executed
            return true;
        }
        return false;
    }

    /**
     * Boolean function that controls the players movement. Swipe up to go up, down to go down, etc.
     * @param event MotionEvent that tracks x and y position of the players finger
     * @return direction of the swipe
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float deltaY, deltaX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch(p1_heading) {
                    case UP:
                    case DOWN:
                        val1x = event.getX();
                        break;
                    case LEFT:
                    case RIGHT:
                        val1y = event.getY();
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (p1_heading) {
                    case UP:
                        float val2x = event.getX();
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
                        float val2y = event.getY();
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

