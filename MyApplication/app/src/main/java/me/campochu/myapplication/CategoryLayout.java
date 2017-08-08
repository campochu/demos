package me.campochu.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * ckb on 2017/8/8.
 */

public class CategoryLayout extends ViewGroup {

    private int mDividerWidth = 10;

    private int mTotalHeight;

    public CategoryLayout(Context context) {
        this(context, null);
    }

    public CategoryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mTotalHeight = 0;
        int maxWidth = 0;
        int index = 0;
        int skiped = 0;

        final int count = getChildCount();

        View left = null;

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null) {
                skiped++;
                continue;
            }

            if (child.getVisibility() == GONE) {
                skiped++;
                continue;
            }

            index = i - skiped;
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

    }

    private int measurePair(View left, View right, int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (right == null) {
            final MarginLayoutParams leftLp = (MarginLayoutParams) left.getLayoutParams();
            measureChildWithMargins(left, widthMeasureSpec, 0, heightMeasureSpec, mTotalHeight);
            mTotalHeight += left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
            return measureWidth;
        }

        final MarginLayoutParams leftLp = (MarginLayoutParams) left.getLayoutParams();
        final MarginLayoutParams rightLp = (MarginLayoutParams) right.getLayoutParams();

        int widthUsed = (measureWidth - getPaddingLeft() - getPaddingRight() - mDividerWidth) / 2 + mDividerWidth;
        measureChildWithMargins(left, widthMeasureSpec, widthUsed, heightMeasureSpec, mTotalHeight);
        measureChildWithMargins(right, widthMeasureSpec, widthUsed, heightMeasureSpec, mTotalHeight);

        mTotalHeight += Math.max(left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin,
                right.getMeasuredHeight() + rightLp.topMargin + rightLp.bottomMargin);

        return measureWidth;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childTop = getPaddingTop();

        int index = 0;
        int skiped = 0;

        final int count = getChildCount();

        View left = null;

        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);

            if (child == null) {
                skiped++;
                continue;
            }

            if (child.getVisibility() == GONE) {
                skiped++;
                continue;
            }

            index = i - skiped;

            if (index % 2 == 0) {
                left = child;
                continue;
            }

            if (index > 1) {
                childTop += mDividerWidth;
            }

            MarginLayoutParams leftLp = (MarginLayoutParams) left.getLayoutParams();
            MarginLayoutParams rightLp = (MarginLayoutParams) child.getLayoutParams();

            int leftLeft = l + getPaddingLeft() + leftLp.leftMargin;
            int rightRight = r - getPaddingRight() - rightLp.rightMargin;

            int leftHeight = left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
            int rightHeight = child.getMeasuredHeight() + rightLp.topMargin + rightLp.bottomMargin;

            if (leftHeight > rightHeight) {

                left.layout(leftLeft,
                        childTop + leftLp.topMargin,
                        leftLeft + left.getMeasuredWidth(),
                        childTop + leftLp.topMargin + left.getMeasuredHeight());
                child.layout(rightRight - child.getMeasuredWidth(),
                        childTop + (leftHeight - rightHeight) / 2 + rightLp.topMargin,
                        rightRight,
                        childTop + (leftHeight - rightHeight) / 2 + rightLp.topMargin + child.getMeasuredHeight());

            } else {
                left.layout(leftLeft,
                        childTop + (rightHeight - leftHeight) / 2 + leftLp.topMargin,
                        leftLeft + left.getMeasuredWidth(),
                        childTop + (rightHeight - leftHeight) / 2 + leftLp.topMargin + left.getMeasuredHeight());

                child.layout(rightRight - child.getMeasuredWidth(),
                        childTop + rightLp.topMargin,
                        rightRight,
                        childTop + rightLp.topMargin + child.getMeasuredHeight());
            }
            childTop += Math.max(leftHeight, rightHeight);

            left = null;

        }

        if (left != null) {

            if (index > 1) {
                childTop += mDividerWidth;
            }
            MarginLayoutParams lp = (MarginLayoutParams) left.getLayoutParams();
            left.layout(l + getPaddingLeft() + lp.leftMargin,
                    childTop + lp.topMargin,
                    r - getPaddingRight() - lp.rightMargin,
                    childTop + lp.topMargin + left.getMeasuredHeight());

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
