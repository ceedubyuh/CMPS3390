package edu.csub.startracker;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class EnemySpawner{
    private Resources res;
    private ArrayList<GameObject> enemies;
    float x, y;
    int screenWidth;
    int wave = 1, enemy01spawned = 0, enemy02spawned = 0;
    int frameTick = 0, spawnTick, waveTick = 0;
    private Paint paint = new Paint();


    public EnemySpawner(Resources res){
        enemies = new ArrayList<>();
        screenWidth = res.getDisplayMetrics().widthPixels;
        this.res = res;
        spawnTick = new Random().nextInt(120-60) + 60;
        paint.setColor(Color.WHITE);
        paint.setTextSize(screenWidth * 0.05f);
    }

    public void update(){
        frameTick++;
        if(frameTick >= spawnTick){
            frameTick = 0;
            spawnTick = new Random().nextInt(120-60) + 60;
            x = new Random().nextInt((int) (screenWidth * 0.75f - screenWidth * 0.5f)) + screenWidth * 0.5f;
            int tmp = (int)Math.round(Math.random());
            if(tmp == 0 && enemy01spawned < wave){
                enemies.add(new Enemy01(res, x, y));
                enemy01spawned++;
            } else {
                tmp = 1;
            }

            if(tmp == 1 && enemy02spawned < wave){
                enemies.add(new Enemy02(res, x, y));
                enemy02spawned++;
            }
        }

        if (enemy01spawned >= wave && enemy02spawned >= wave){
            waveTick++;
        }
        if(waveTick >= 240){
            wave++;
            waveTick = 0;
            enemy01spawned = 0;
            enemy02spawned = 0;
        }
        for(Iterator<GameObject> iterator = enemies.iterator(); iterator.hasNext();){
            GameObject go = iterator.next();
            go.update();
            if(!go.isAlive()){
                iterator.remove();
            }
        }
    }

    public void draw(Canvas canvas){
        canvas.drawText("Wave: " + wave, screenWidth * 0.05f, screenWidth * 0.05f, paint);
        for(GameObject go : enemies){
            go.draw(canvas);
        }
    }

    public ArrayList<GameObject> getEnemies(){
        return enemies;
    }

 }
