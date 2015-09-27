package com.test.readdle.sergey.onofreychuck.readdletestapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.test.readdle.sergey.onofreychuck.readdletestapp.R;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.DeviceAbstract;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Direction;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Level;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.Room;
import com.test.readdle.sergey.onofreychuck.readdletestapp.level.RoomCoordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 9/26/15.
 */
public class MiniMap extends View {

    private static final String TAG = "MiniMap";

    private int mRows;
    private int mColumns;
    private List<RoomCoordinates> mRooms;
    private Trackable mTrackedObject;
    private Paint mPaint = new Paint();
    private Drawable mRoomDrawable;
    private MiniMapOnTouchListener mOnTouchListener;

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
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6f);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mRoomDrawable = context.getResources().getDrawable(R.drawable.minimap_room);

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


        Rect bounds = canvas.getClipBounds();
        if (mTranslator == null) {
            mTranslator = new MiniMapCoordinatesTranslator(bounds, mRows, mColumns);
        }

        if (mRooms != null) {
            for (RoomCoordinates coordinates : mRooms){
                Rect currentCellBounds = mTranslator.getBouds(coordinates.getX(), coordinates.getY());

                if (mTrackedObject != null && coordinates.equals(mTrackedObject.getPosition())) {
                    Bitmap icon = mTrackedObject.getIcon();

                    float scaleX = (float)currentCellBounds.width()/(float)icon.getWidth();
                    float scaleY = (float)currentCellBounds.height()/(float)icon.getHeight();

                    Matrix transformMatrix = new Matrix();
                    transformMatrix.postScale(scaleX, scaleY);
                    transformMatrix.postTranslate(currentCellBounds.left, currentCellBounds.top);

                    canvas.drawBitmap(icon, transformMatrix, null);
                } else {
                    mRoomDrawable.setBounds(mTranslator.getBouds(coordinates.getX(), coordinates.getY()));
                    mRoomDrawable.draw(canvas);
                }
            }
        }
    }

    public interface LevelDataProvider {
        List<RoomCoordinates> getRoomsCoordinates();
        int getColumns();
        int getRows();
    }

    public interface MiniMapOnTouchListener {
        void onTouch(RoomCoordinates coordinates);
    }
}
