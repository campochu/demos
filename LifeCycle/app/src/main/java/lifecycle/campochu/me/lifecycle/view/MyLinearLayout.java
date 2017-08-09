package lifecycle.campochu.me.lifecycle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by ckb on 17/8/9.
 */

public class MyLinearLayout extends LinearLayout {

    private final String TRACE_VIEW = "linear_layout";
    private final boolean mTraceLog = true;

    public MyLinearLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long nanoStart = SystemClock.elapsedRealtimeNanos();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        long nanoStart = SystemClock.elapsedRealtimeNanos();
        super.onLayout(changed, l, t, r, b);
        if (mTraceLog) {
            Log.d(TRACE_VIEW, "layout time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long nanoStart = SystemClock.elapsedRealtimeNanos();
        canvas.drawColor(getResources().getColor(android.R.color.holo_orange_light));
        if (mTraceLog) {
            Log.d(TRACE_VIEW, "draw time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }
    }
}
