package ru.surf.nikita_makarov.makeboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareButton extends ImageView {
    public  SquareButton(Context context) {
        super(context);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.MAGENTA);
        paint.setTextSize(30);
        canvas.drawText("Custom View",30, 30, paint);

        super.onDraw(canvas);
        canvas.restore();
    }
}