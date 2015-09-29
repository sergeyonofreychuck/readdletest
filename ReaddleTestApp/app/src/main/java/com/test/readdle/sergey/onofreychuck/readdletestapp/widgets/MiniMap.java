package com.test.readdle.sergey.onofreychuck.readdletestapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.test.readdle.sergey.onofreychuck.readdletestapp.R;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.DeviceAbstract;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Room;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Trackable;

import java.util.ArrayList;
import java.util.List;

public class MiniMap extends View {

    private static final String TAG = "MiniMap";

    private int mRows;
    private int mColumns;
    private List<RoomCoordinates> mRooms;
    private Trackable mTrackedObject;
    private Drawable mRoomDrawable;
    private MiniMapOnTouchListener mOnTouchListener;
    private Rect mClipBounds;
    private float mTrackableScaleX = 0;
    private float mTrackableScaleY = 0;

    private MiniMapCoordinatesTranslator mTranslator;

    public MiniMap(Context context) {
        super(context);
        init(context);
    }

    public MiniMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MiniMap(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        mRoomDrawable = ContextCompat.getDrawable(context, R.drawable.minimap_room);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() & MotionEvent.ACTION_UP) == MotionEvent.ACTION_UP) {
                    if (mOnTouchListener != null) {
                        mOnTouchListener.onTouch(mTranslator.getTouchCoordinates((int) event.getX(), (int) event.getY()));
                    }
                    return false;
                }
                return true;
            }
        });
    }

    @SuppressWarnings("rows and columns can take different values in future releases")
    public void initGrid(int rows, int columns) {
        mRows = rows;
        mColumns = columns;
    }

    public void setRooms(List<RoomCoordinates> rooms){
        if (rooms == null) {
            throw new IllegalArgumentException("rooms");
        }

        mRooms = new ArrayList<>(rooms);
        invalidate();
    }

    public void setTrackedObject(Trackable trackedObject){
        mTrackedObject = trackedObject;
        mTrackedObject.setActionsCallback(new DeviceAbstract.PositionChangedCallback() {
            @Override
            public void positionChanged(Room room, Direction direction) {
                Log.d(TAG, "tracked position changed: " + room.getCoordinates() + "   " + direction);
                invalidate();
            }
        });
    }

    public void setOnTouchListener(MiniMapOnTouchListener listener) {
        mOnTouchListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mClipBounds == null) {
            mClipBounds = canvas.getClipBounds();
            mTranslator = new MiniMapCoordinatesTranslator(mClipBounds, mRows, mColumns);
        }

        if (mRooms == null) {
            return; //data can be still not set
        }

        for (RoomCoordinates coordinates : mRooms){
            Rect currentCellBounds = mTranslator.getBounds(coordinates.getX(), coordinates.getY());

            if (mTrackedObject != null && coordinates.equals(mTrackedObject.getRoom().getCoordinates())) {
                Bitmap icon = mTrackedObject.getIcon();

                if (mTrackableScaleX == 0f || mTrackableScaleY == 0f) {
                    mTrackableScaleX = (float)currentCellBounds.width()/(float)icon.getWidth();
                    mTrackableScaleY = (float)currentCellBounds.height()/(float)icon.getHeight();
                }
                Matrix transformMatrix = new Matrix();
                transformMatrix.postScale(mTrackableScaleX, mTrackableScaleY);
                transformMatrix.postTranslate(currentCellBounds.left, currentCellBounds.top);

                canvas.drawBitmap(icon, transformMatrix, null);
            } else {
                mRoomDrawable.setBounds(mTranslator.getBounds(coordinates.getX(), coordinates.getY()));
                mRoomDrawable.draw(canvas);
            }
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //noinspection SuspiciousNameCombination This is square form support logic
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public interface MiniMapOnTouchListener {
        void onTouch(RoomCoordinates coordinates);
    }
}
