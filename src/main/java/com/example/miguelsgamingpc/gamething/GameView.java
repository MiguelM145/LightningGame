package com.example.miguelsgamingpc.gamething;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;


import java.util.Random;





public class GameView extends View {
    //thing
    public static final String EXTRA_SCORE = "com.example.miguelsgamingpc.gamething" ;

    //test
    private Context mContext;

    //Character Variables
    private Bitmap char1[] = new Bitmap[2];

    //Cloud Variables
    private Bitmap cloud[] = new Bitmap[3];
    private Bitmap cloud2[] = new Bitmap[3];
    private Bitmap cloud3[] = new Bitmap[3];

    //Background
    private Bitmap ground;
    private Bitmap sky;
    Point point;
    int dWidth, dHeight;
    Rect rect;
    Rect groundR;
    Display display;

    //score
    private Paint scorePaint = new Paint();


    //Variables
    int charX, charY;
    int charSpeed;
    int charFrame;
    int speed;
    public int score;

    //cloud frames
    //Personal Notes :D
    //CLFR1 IS LEFT CLOUD
    //CLFR2 IS MIDDLE CLOUD
    //CLFR3 IS RIGHT CLOUD
    int clFr1, clFr2, clFr3;

    //Random variables
    Random rand = new Random();

    //Time delay stuff
    boolean active_time;
    boolean first_time;

    int random;

    boolean gameover;

    boolean alreadyExecuted;

    int timeThing;






    public GameView(Context context) {
        super(context);
        this.mContext = context;




        char1[0] = BitmapFactory.decodeResource(getResources(), R.drawable.char1);
        char1[1] = BitmapFactory.decodeResource(getResources(), R.drawable.char2);

        //cloud 1
        cloud[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
        cloud[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
        cloud[2] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
        //cloud 2
        cloud2[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
        cloud2[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
        cloud2[2] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);
        //cloud 3
        cloud3[0] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
        cloud3[1] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
        cloud3[2] = BitmapFactory.decodeResource(getResources(), R.drawable.cloud3);


        sky = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);




        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(120);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);


        display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        groundR = new Rect(0, dHeight/2 + 180 + char1[0].getHeight(), dWidth, dHeight);
        charX = dWidth / 2 - char1[0].getWidth() / 2;
        charY = dHeight / 2 + 180;
        charSpeed = 0;
        charFrame = 0;
        speed = 20;
        score = 0;
        clFr1 = 0;
        clFr2 = 0;
        clFr3 = 0;
        active_time = false;
        random = 0;
        gameover = false;
        alreadyExecuted = false;
        first_time = false;


    }

    public void randomM(){
        random = rand.nextInt(3) + 1;

    }




    public void cloudMethod() {

        if(score <= 40) {
            timeThing = 2000 - score * 40;
        }else{
            timeThing = 400;
        }

            if (!active_time) {
                randomM();

                 new CountDownTimer( timeThing, 1000) {
                    public void onTick(long millisUntilFinished) {

                        if(first_time) {
                            if (millisUntilFinished > 300) {
                                beforeClo(random);
                            }
                        }
                        active_time = true;

                    }

                    public void onFinish() {

                        active_time = false;
                        if(first_time) {
                            cloudThing(random);
                            cloudHit();
                        }
                        first_time = true;
                    }
                }.start();
            }
        }


    public void cloudThing(int clo){

        if (clo == 1) {
            clFr1 = 1;
            clFr2 = 0;
            clFr3 = 0;
        }
        if (clo == 2) {
            clFr1 = 0;
            clFr2 = 1;
            clFr3 = 0;
        }
        if (clo == 3) {
            clFr1 = 0;
            clFr2 = 0;
            clFr3 = 1;
        }

        score++;
    }



    public void beforeClo(int clo){
        if(clo == 1){
            clFr1 = 2;
            clFr2 = 0;
            clFr3 = 0;
        }
        if(clo == 2){
            clFr1 = 0;
            clFr2 = 2;
            clFr3 = 0;
        }
        if(clo == 3){
            clFr1 = 0;
            clFr2 = 0;
            clFr3 = 2;
        }

    }


    //work in progress
    public void cloudHit(){

        //left cloud
        if(clFr1 == 1 && charX < dWidth/3){
                gameOver();
        }
        //middle cloud
       if(clFr2 == 1 && charX > dWidth/3 - cloud[0].getWidth()/8 && charX < dWidth - dWidth/3 + cloud[0].getWidth()/20){
               gameOver();
       }
       //Right cloud
        if(clFr3 == 1 && charX > dWidth - cloud[0].getWidth()/2 ){
            gameOver();
        }

    }

    public void gameOver(){
        if(!alreadyExecuted) {
            //method to go to game over screen
            score = score - 1;
            String text = String.valueOf(score);

            Intent intent = new Intent(mContext, GameOver.class);
            intent.putExtra(EXTRA_SCORE, text);
            mContext.startActivity(intent);
            ((Activity) getContext()).finish();
            alreadyExecuted = true;
        }
    }




    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(sky, null, rect, null);
        canvas.drawBitmap(ground, null, groundR, null);

            //movement
            charX += charSpeed;

            //don't get off screen
            if (charX < 0) {
                charX = 0;
            }
            if (charX > dWidth - char1[0].getWidth()) {
                charX = dWidth - char1[0].getWidth();
            }

            cloudMethod();
            //cloudHit(); seems like it works better if in onFinish change if it screws up

            //Left Cloud
            canvas.drawBitmap(cloud[clFr1], 0 - cloud[0].getWidth() / 3, 0, null);
            //Right Cloud
            canvas.drawBitmap(cloud3[clFr3], dWidth - cloud3[0].getWidth() / 2, 0, null);
            //Middle Cloud
            canvas.drawBitmap(cloud2[clFr2], dWidth / 2 - cloud[0].getWidth() / 2, 0, null);
            canvas.drawBitmap(char1[charFrame], charX, charY, null);
            canvas.drawText("" + score, dWidth / 2, 220, scorePaint);


        }






    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() < dWidth / 2) {
            charSpeed = -speed;
            charFrame = 1;


        }
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() > dWidth / 2) {
            charSpeed = speed;
            charFrame = 0;

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            charSpeed = 0;
        }


        return true;

    }



    }



