package com.test.readdle.sergey.onofreychuck.readdletestapp.widgets;

import android.graphics.Rect;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

/**
 * Created by sergey on 9/26/15.
 */
class MiniMapCoordinatesTranslator {

    private Rect mClipBounds;
    private float mCellWidth;
    private float mCellHeight;

    public MiniMapCoordinatesTranslator(Rect clipBounds, int columns, int rows){
        if (clipBounds == null) {
            throw new IllegalArgumentException("clipBounds");
        }
        if (rows <= 0) {
            throw new IllegalArgumentException("rows");
        }
        if (columns <= 0) {
            throw new IllegalArgumentException("columns");
        }

        mClipBounds = clipBounds;

        int allWidth = mClipBounds.right - mClipBounds.left;
        int allHeight = mClipBounds.bottom - mClipBounds.top;
        mCellWidth = (float)allWidth/(float)columns;
        mCellHeight = (float)allHeight/(float)rows;
    }

    public Rect getBounds(int x, int y) {
        int left = (int)(mClipBounds.left + x * mCellWidth);
        int right = (int)(left + mCellWidth);
        int top = (int)(mClipBounds.top + y * mCellHeight);
        int bottom = (int)(top + mCellHeight);

        return new Rect(left, top, right, bottom);
    }

    public RoomCoordinates getTouchCoordinates(int x, int y){
        return new RoomCoordinates((int)(x/mCellWidth), (int)(y/mCellHeight));
    }
}
