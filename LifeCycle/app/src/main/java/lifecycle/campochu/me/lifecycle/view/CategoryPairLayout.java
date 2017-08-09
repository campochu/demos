package lifecycle.campochu.me.lifecycle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Debug;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * 1,2
 * 3,4
 *
 * Created by ckb on 17/8/8.
 */

public class CategoryPairLayout extends ViewGroup {

    private final String TRACE_VIEW = "category_pair";
    private final boolean mTraceLog = true;

    private int mDividerWidth;

    private int mTotalHeight;

    public CategoryPairLayout(Context context) {
        this(context, null);
    }

    public CategoryPairLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryPairLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    public int getDividerWidth() {
        return mDividerWidth;
    }

    public void setDividerWidth(int dividerWidth) {
        mDividerWidth = dividerWidth;
    }

    boolean first = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        long nanoStart = SystemClock.elapsedRealtimeNanos();

        if (first) {
            Debug.startMethodTracing("trace_layout.trace");
        }

        mTotalHeight = 0;
        int maxWidth = 0;
        int index = 0;
        int skipped = 0;

        final int count = getChildCount();

        View left = null;

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                skipped++;
                continue;
            }

            index = i - skipped;
            // 0 1
            // 2 3
            // 4 5
            if (index % 2 == 0) {
                left = child;
                continue;
            }

            if (index > 1) {
                mTotalHeight += mDividerWidth;
            }

            maxWidth = Math.max(measurePair(left, child, widthMeasureSpec, heightMeasureSpec), maxWidth);
            left = null;

        }

        if (left != null) {

            if (index > 1) {
                mTotalHeight += mDividerWidth;
            }

            maxWidth = Math.max(measurePair(left, null, widthMeasureSpec, heightMeasureSpec), maxWidth);
        }

        mTotalHeight += (getPaddingTop() + getPaddingBottom());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            resolveSizeAndState(Math.max(mTotalHeight, getSuggestedMinimumHeight()), heightMeasureSpec, 0));

        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

        if (first) {
            Debug.stopMethodTracing();
            first = false;
        }

    }

    private int measurePair(View left, View right, int widthMeasureSpec, int heightMeasureSpec) {

        long nanoStart = SystemClock.elapsedRealtimeNanos();

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        int widthSpec = widthMeasureSpec;
        int heightSpec = heightMeasureSpec;

        if (MeasureSpec.getMode(widthMeasureSpec) != EXACTLY) {
            widthSpec = makeMeasureSpec(measuredWidth, EXACTLY);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) != AT_MOST) {
            heightSpec = makeMeasureSpec(measuredHeight, AT_MOST);
        }

        if (right == null) {

            final MarginLayoutParams leftLp = (MarginLayoutParams)left.getLayoutParams();

            // 强制组件宽
            leftLp.width = LayoutParams.MATCH_PARENT;

            if (leftLp.height == LayoutParams.MATCH_PARENT) {
                leftLp.height = LayoutParams.WRAP_CONTENT;
            }

            measureChildWithMargins(left, widthSpec, 0, heightSpec, mTotalHeight);
            mTotalHeight += left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
            return measuredWidth;
        }

        final MarginLayoutParams leftLp = (MarginLayoutParams)left.getLayoutParams();
        final MarginLayoutParams rightLp = (MarginLayoutParams)right.getLayoutParams();

        // 强制组件宽
        leftLp.width = LayoutParams.MATCH_PARENT;
        rightLp.width = LayoutParams.MATCH_PARENT;

        if (leftLp.height == LayoutParams.MATCH_PARENT) {
            leftLp.height = LayoutParams.WRAP_CONTENT;
        }
        if (rightLp.height == LayoutParams.MATCH_PARENT) {
            rightLp.height = LayoutParams.WRAP_CONTENT;
        }

        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure pre first time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

        int widthUsed = (measuredWidth - getPaddingLeft() - getPaddingRight() - mDividerWidth) / 2 + mDividerWidth;
        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure pre 1 first time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }
        measureChildWithMargins(left, widthSpec, widthUsed, heightSpec, mTotalHeight);
        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure pre 2 first time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }
        measureChildWithMargins(right, widthSpec, widthUsed, heightSpec, mTotalHeight);
        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure pre 3 first time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

        int leftHeight = left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
        int rightHeight = right.getMeasuredHeight() + rightLp.topMargin + rightLp.bottomMargin;

        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure first time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

        // 高度矫正，二次计算，把矮的拉高
        if (leftHeight > rightHeight) {
            rightLp.height = leftHeight;
            measureChildWithMargins(right, widthSpec, widthUsed, heightSpec, mTotalHeight);
        } else if (rightHeight > leftHeight) {
            leftLp.height = rightHeight;
            measureChildWithMargins(left, widthSpec, widthUsed, heightSpec, mTotalHeight);
        }

        mTotalHeight += Math.max(leftHeight, rightHeight);

        if (mTraceLog) {
            Log.d(TRACE_VIEW, "measure second time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

        return measuredWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        long nanoStart = SystemClock.elapsedRealtimeNanos();

        mTotalHeight = getPaddingTop();

        int index = 0;
        int skiped = 0;

        final int count = getChildCount();

        View left = null;

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                skiped++;
                continue;
            }

            index = i - skiped;

            if (index % 2 == 0) {
                left = child;
                continue;
            }

            if (index > 1) {
                mTotalHeight += mDividerWidth;
            }

            layoutPair(left, child, l, r);

            left = null;

        }

        if (left != null) {

            if (index > 1) {
                mTotalHeight += mDividerWidth;
            }

            layoutPair(left, null, l, r);
        }

        if (mTraceLog) {
            Log.d(TRACE_VIEW, "layout time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

    }

    private void layoutPair(View left, View right, int l, int r) {

        if (right == null) {
            MarginLayoutParams lp = (MarginLayoutParams)left.getLayoutParams();
            left.layout(l + getPaddingLeft() + lp.leftMargin,
                mTotalHeight + lp.topMargin,
                r - getPaddingRight() - lp.rightMargin,
                mTotalHeight + lp.topMargin + left.getMeasuredHeight());
            return;
        }

        MarginLayoutParams leftLp = (MarginLayoutParams)left.getLayoutParams();
        MarginLayoutParams rightLp = (MarginLayoutParams)right.getLayoutParams();

        int leftLeft = l + getPaddingLeft() + leftLp.leftMargin;
        int rightRight = r - getPaddingRight() - rightLp.rightMargin;

        int leftHeight = left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
        int rightHeight = right.getMeasuredHeight() + rightLp.topMargin + rightLp.bottomMargin;

        if (leftHeight > rightHeight) {

            left.layout(leftLeft,
                mTotalHeight + leftLp.topMargin,
                leftLeft + left.getMeasuredWidth(),
                mTotalHeight + leftLp.topMargin + left.getMeasuredHeight());
            right.layout(rightRight - right.getMeasuredWidth(),
                mTotalHeight + (leftHeight - rightHeight) / 2 + rightLp.topMargin,
                rightRight,
                mTotalHeight + (leftHeight - rightHeight) / 2 + rightLp.topMargin + right.getMeasuredHeight());

        } else {
            left.layout(leftLeft,
                mTotalHeight + (rightHeight - leftHeight) / 2 + leftLp.topMargin,
                leftLeft + left.getMeasuredWidth(),
                mTotalHeight + (rightHeight - leftHeight) / 2 + leftLp.topMargin + left.getMeasuredHeight());

            right.layout(rightRight - right.getMeasuredWidth(),
                mTotalHeight + rightLp.topMargin,
                rightRight,
                mTotalHeight + rightLp.topMargin + right.getMeasuredHeight());
        }
        mTotalHeight += Math.max(leftHeight, rightHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawFrame(canvas);

        super.onDraw(canvas);
    }

    private void drawFrame(Canvas canvas) {

        long nanoStart = SystemClock.elapsedRealtimeNanos();

        canvas.save();

        //Rect rect = new Rect();
        //final int count = getChildCount();
        //for (int i = 0; i < count; i++) {
        //    View child = getChildAt(i);
        //    if (child == null || child.getVisibility() == GONE) {
        //        continue;
        //    }
        //    MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
        //    rect.set(child.getLeft() - lp.leftMargin,
        //        child.getTop() - lp.topMargin,
        //        child.getRight() + lp.rightMargin,
        //        child.getBottom() + lp.bottomMargin);
        //    canvas.clipRect(rect, Op.XOR);
        //}
        canvas.drawColor(getResources().getColor(android.R.color.holo_orange_light));

        canvas.restore();

        if (mTraceLog) {
            Log.d(TRACE_VIEW, "draw time : " + (SystemClock.elapsedRealtimeNanos() - nanoStart));
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }
}
