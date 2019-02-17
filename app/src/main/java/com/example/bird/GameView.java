package com.example.bird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {

    private OnGameOver onGameOver;

    private int canvasWidth;
    private int canvasHeight;

    private int score;
    private int lifeCount;

    private Bitmap mBird [] = new Bitmap[2];
    private int birdX = 35;
    private int birdY;
    private int birdSpeed;


    // Blue ball.
    private int blueX;
    private int blueY;
    private int blueSpeed = 15;
    private Paint bluePaint = new Paint();


    // Black ball;
    private int blackX;
    private int blackY;
    private int blackSpeed = 22;
    private Paint blackPaint = new Paint();


    private Bitmap mBackground;

    private Paint mScorePaint = new Paint();
    private Paint mLevelPaint = new Paint();

    private Bitmap mLife [] = new Bitmap[2];

    private boolean touchFlag = false;

    public GameView(Context context) {
        super(context);

        mBird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        mBird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);

        mScorePaint.setColor(Color.BLACK);
        mScorePaint.setTextSize(32);
        mScorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mScorePaint.setAntiAlias(true);

        mLevelPaint.setColor(Color.DKGRAY);
        mLevelPaint.setTextSize(32);
        mLevelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mLevelPaint.setTextAlign(Paint.Align.CENTER);
        mLevelPaint.setAntiAlias(true);

        mLife[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        mLife[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_null);

        birdY = 500;
        score = 0;
        lifeCount = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        canvas.drawBitmap(mBackground, 0, 0, null);

        int minBirdY = mBird[0].getHeight();
        int maxBirdY = canvasHeight - mBird[0].getHeight() * 3;

        birdY += birdSpeed;

        if (birdY < minBirdY) {
            birdY = minBirdY;
        }

        if (birdY > maxBirdY) {
            birdY = maxBirdY;
        }

        birdSpeed += 2;

        if (touchFlag) {
            canvas.drawBitmap(mBird[1], birdX, birdY, null);
            touchFlag = false;
        } else {
            canvas.drawBitmap(mBird[0], birdX, birdY, null);
        }

        // Black.
        blackX -= blackSpeed;

        if (hitCheck(blackX, blackY)) {
            blackX = -100;
            lifeCount--;

            if (lifeCount <= 0) {
                onGameOver.onGameOver(score);
            }
        }

        if (blackX < 0) {
            blackX = canvasWidth + 200;
            blackY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }

        canvas.drawCircle(blackX, blackY, 20, blackPaint);

        // Blue.
        blueX -= blueSpeed;

        if (hitCheck(blueX, blueY)) {
            score += 10;
            blueX = -100;
        }

        if (blueX < 0) {
            blueX = canvasWidth + 20;
            blueY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
        }

        for (int i = 0; i < 3; i++) {
            int x = (int) (500 + mLife[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCount) {
                canvas.drawBitmap(mLife[0],  x, y, null);
            } else {
                canvas.drawBitmap(mLife[1],  x, y, null);
            }
        }

        canvas.drawCircle(blueX, blueY, 10, bluePaint);

        canvas.drawText("Score: " + score, 20, 60, mScorePaint);

        canvas.drawText("Level: 1", canvasWidth / 2, canvasHeight / 2, mLevelPaint);
    }

    private boolean hitCheck(int x, int y) {
        if (birdX < x && x < (birdX + mBird[0].getWidth())
        && birdY < y && y < (birdY + mBird[0].getHeight())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchFlag = true;
            birdSpeed = -20;
        }

        return true;
    }

    public interface OnGameOver {
        void onGameOver(int score);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onGameOver = (OnGameOver) getContext();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onGameOver = null;
    }
}
