package ru.surf.nikita_makarov.makeboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

    public class ChessSquareView extends ImageView {
        Resources res = getResources();
        Drawable mPiece = res.getDrawable(R.drawable.colour_square);

        public ChessSquareView(Context context){
            super(context);
            setClickable(true);
        }
        public ChessSquareView(Context context, AttributeSet attributes) {
            super(context, attributes);
            setClickable(true);
        }
        public void setPiece(Drawable _piece){
            mPiece = _piece;
        }
        public Drawable getPiece(){
            return mPiece;
        }

        @Override
        protected void onDraw(Canvas _canvas){
            super.onDraw(_canvas);
            mPiece.draw(_canvas);
        }
    }
