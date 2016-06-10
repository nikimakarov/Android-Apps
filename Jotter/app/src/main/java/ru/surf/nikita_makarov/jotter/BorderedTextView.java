package ru.surf.nikita_makarov.jotter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderedTextView extends TextView {
    private Paint paint = new Paint();
    public static final int BORDER_TOP = 0;
    public static final int BORDER_RIGHT = 1;
    public static final int BORDER_BOTTOM = 2;
    public static final int BORDER_LEFT = 3;

    public Border borders[];

    public BorderedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BorderedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BorderedTextView(Context context) {
        super(context);
        init();
    }

    private void init(){
        borders = new Border[4];
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(13);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(borders == null) return;

        for(Border border : borders){
            paint.setColor(border.getColor());
            paint.setStrokeWidth(border.getWidth());

            if(border.getStyle() == BORDER_TOP){
                canvas.drawLine(0, 0, getWidth(), 0, paint);
            } else
            if(border.getStyle() == BORDER_RIGHT){
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
            } else
            if(border.getStyle() == BORDER_BOTTOM){
                canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
            } else
            if(border.getStyle() == BORDER_LEFT){
                canvas.drawLine(0, 0, 0, getHeight(), paint);
            }
        }
    }

    public Border[] getBorders() {
        return borders;
    }

    public void setBorders(int Color, int Width) {
        for (int side=0; side<4; side++) {
            this.borders[side] = new Border();
            this.borders[side].setStyle(side);
            this.borders[side].setColor(Color);
            this.borders[side].setWidth(Width);
        }
    }

}