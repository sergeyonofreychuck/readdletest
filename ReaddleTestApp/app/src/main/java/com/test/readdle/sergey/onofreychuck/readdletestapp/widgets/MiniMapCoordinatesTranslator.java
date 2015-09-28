package com.test.readdle.sergey.onofreychuck.readdletestapp.widgets;

import android.graphics.Rect;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

/**
 * Created by sergey on 9/26/15.
 */
class MiniMapCoordinatesTranslator {

    private Rect mClipBounds;
    private int mColumns;
    private int mRows;

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
        mColumns = columns;
        mRows = rows;
    }

    public Rect getBounds(int x, int y) {

        int allWidth = mClipBounds.right - mClipBounds.left;
        int allHeight = mClipBounds.bottom - mClipBounds.top;
        float width = (float)allWidth/(float)mColumns;
        float height = (float)allHeight/(float)mRows;

        int left = (int)(mClipBounds.left + x * width);
        int right = (int)(left + width);
        int top = (int)(mClipBounds.top + y * height);
        int bottom = (int)(top + height);

        return new Rect(left, top, right, bottom);
    }

    public RoomCoordinates getTouchCoordinates(int x, int y){
        int allWidth = mClipBounds.right - mClipBounds.left;
        int allHeight = mClipBounds.bottom - mClipBounds.top;
        float width = (float)allWidth/(float)mColumns;
        float height = (float)allHeight/(float)mRows;

        return new RoomCoordinates((int)(x/width), (int)(y/height));
    }

}
