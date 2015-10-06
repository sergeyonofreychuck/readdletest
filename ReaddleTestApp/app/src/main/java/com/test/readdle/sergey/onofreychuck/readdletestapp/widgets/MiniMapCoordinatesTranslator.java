package com.test.readdle.sergey.onofreychuck.readdletestapp.widgets;

import android.graphics.Rect;

import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

class MiniMapCoordinatesTranslator {

    private Rect mClipBounds;
    private float mCellWidth;
    private float mCellHeight;

    public MiniMapCoordinatesTranslator(Rect clipBounds, int columns, int rows){
        if (clipBounds == null) {
            throw new IllegalArgumentException("clipBounds");
        }

        mClipBounds = clipBounds;
        int allWidth = mClipBounds.right - mClipBounds.left;
        int allHeight = mClipBounds.bottom - mClipBounds.top;
        mCellWidth = (float)allWidth/(float)columns;
        mCellHeight = (float)allHeight/(float)rows;
    }

    public Rect getBounds(int x, int y) {
        return getBounds(x, y, 1);
    }

    public Rect getBounds(int x, int y, float scale) {
        float width = mCellWidth * scale;
        float height = mCellWidth * scale;

        float top = mClipBounds.top + y * mCellHeight;
        top += (mCellHeight - height)/2;

        float left = mClipBounds.left + x * mCellWidth;
        left += (mCellWidth - width)/2;

        int bottom = (int)(top + height);
        int right = (int)(left + width);

        return new Rect((int)left, (int)top, right, bottom);
    }

    public RoomCoordinates getTouchCoordinates(int x, int y){
        return new RoomCoordinates((int)(x/mCellWidth), (int)(y/mCellHeight));
    }
}
