package bates.letterdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class LetterDrawView extends View {

    private String text = null;
    private int currentTextIndex = 0;

    private ArrayList<Letter> letters = new ArrayList<>();
    private Point lastPoint = null;
    private final static float LETTER_DISTANCE = 30;

    private long fontSize = 0;

    public LetterDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Paint paint = new Paint();
        do {
            paint.setTextSize(++fontSize);
        } while(paint.measureText("O") < LETTER_DISTANCE);
    }

    public void setText(String s) {
        text = s;
        currentTextIndex = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Letter letter : letters) {
            letter.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Point p = new Point(e.getX(), e.getY());
        if (lastPoint == null || e.getAction() == MotionEvent.ACTION_DOWN) {
            lastPoint = p;
            return true;
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if (lastPoint.distance(p) >= LETTER_DISTANCE) {
                addLetterBetweenPoints(lastPoint, p);
                lastPoint = p;
            }
        }
        return true;
    }

    private void addLetterBetweenPoints(Point p1, Point p2) {
        // TODO: do multiple midpoints...

        String letterText = text.substring(currentTextIndex, currentTextIndex+1);
        currentTextIndex = (currentTextIndex + 1) % text.length();

        Point midpoint = Point.midpoint(p1, p2);
        float angle = (float)Math.atan2(p2.y - p1.y, p2.x - p1.x);
        letters.add(new Letter(letterText, midpoint.x, midpoint.y, angle));

        invalidate();
    }

    private class Letter {

        public String text;
        public float x;
        public float y;
        public float angle;
        public Rect rect = new Rect();

        private Paint paint;

        public Letter(String text, float x, float y, float angle) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.angle = angle;

            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(fontSize);
            paint.getTextBounds(text, 0, 1, rect);
            paint.setTextAlign(Paint.Align.LEFT);
        }

        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate((float) (angle*180/Math.PI), x-rect.width()/2, y+rect.height()/2);
            canvas.drawText(text, x-rect.width()/2, y+rect.height()/2, paint);
            canvas.restore();
        }

    }

    private static class Point {

        public float x;
        public float y;

        static Point midpoint(Point p1, Point p2) {
            return new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float distance(Point p) {
            return (float)Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
        }

    }

}
