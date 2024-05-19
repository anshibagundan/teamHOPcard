package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomCircleView extends View {
    private Paint paint;
    private float circleX;
    private float circleY;

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(circleX, circleY, 20, paint);
    }

    public void setCirclePosition(float x, float y) {
        circleX = x;
        circleY = y;
        invalidate();
    }
}