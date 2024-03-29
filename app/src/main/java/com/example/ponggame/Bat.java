package com.example.ponggame;

import android.graphics.RectF;

import java.util.logging.Logger;

class Bat {
    private RectF mRect;
    private float mLength;
    private float mXCoord;
    private float mBatSpeed;
    private int mScreenX;
    private float scale;
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    private boolean batChanged = false;
    private int counter = 0;

    // Keeps track of if and how the ball is moving
    private int mBatMoving = STOPPED;

    Bat(int sx, int sy){

        scale = 0.5f;

        // Bat needs to know the screen
        // horizontal resolution
        // Outside of this method
        mScreenX = sx;
        // Configure the size of the bat based on
        // the screen resolution
        // One eighth the screen width
        mLength = mScreenX / 8;
        // One fortieth the screen height
        float height = sy / 40;
        // Configure the starting location of the bat
        // Roughly the middle horizontally
        mXCoord = mScreenX / 2;
        // The height of the bat
        // off the bottom of the screen
        float mYCoord = sy - height;
        // Initialize mRect based on the size and position
        mRect = new RectF(mXCoord - mLength * scale, mYCoord,
                mXCoord + mLength * scale,
                mYCoord + height);
        // Configure the speed of the bat
        // This code means the bat can cover the
        // width of the screen in 1 second
        mBatSpeed = mScreenX;
    }
    // Return a reference to the mRect object
    RectF getRect(){
        return mRect;
    }
    // Update the movement state passed
    // in by the onTouchEvent method
    void setMovementState(int state){
        mBatMoving = state;
    }
    // Update the bat- Called each frame/loop
    void update(long fps){
        // Move the bat based on the mBatMoving variable
        // and the speed of the previous frame
        if(mBatMoving == LEFT){
            mXCoord = mXCoord - mBatSpeed / fps;
        }
        if(mBatMoving == RIGHT){
            mXCoord = mXCoord + mBatSpeed / fps;
        }

        // Stop the bat going off the screen
        if(mXCoord - mLength* scale < 0){
            mXCoord = mLength*scale;
        } else if(mXCoord + mLength * scale > mScreenX){
            mXCoord = mScreenX - mLength*scale;
        }
        // Update mRect based on the results from
        // the previous code in update
        mRect.left = mXCoord - mLength* scale;
        mRect.right = mXCoord + mLength * scale;

        if (batChanged) {
            counter++;
            if (counter > 500) {
                batChanged = false;
                scale = 0.5f;
                counter = 0;
            }
        }

    }
    void reset(int x, int y) {
        float height = 30;
        mXCoord = x / 2;
        float yc = y - 30;
        mRect = new RectF(x, yc, x + mLength, yc + height);
    }

    public void changeBat(int x, int y) {
        float height = 30;
        float yc = y - 30;
        scale = 1f;
        mRect = new RectF(mXCoord - mLength * scale, yc, mXCoord + mLength * scale, yc + height);
        batChanged = true;
    }

}
