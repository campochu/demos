package me.campochu.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * ckb on 17/8/8.
 */

public class CategoryPairLayout extends ViewGroup {

    private int mTotalHeight;

    @ColorInt
    private int mFrameColor;
    private int mDividerWidth;

    public CategoryPairLayout(Context context) {
        this(context, null);
    }

    public CategoryPairLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryPairLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        setWillNotDraw(false);
    }

    public void setDividerWidth(int dividerWidth) {
        if (mDividerWidth != dividerWidth) {
            requestLayout();
        }
        mDividerWidth = dividerWidth;
    }

    public void setFrameColor(@ColorInt int frameColor) {
        if (mFrameColor != frameColor) {
            requestLayout();
        }
        mFrameColor = frameColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mTotalHeight = 0;
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

            if (index % 2 == 0) {
                left = child;
                continue;
            }

            if (index > 1) {
                mTotalHeight = mTotalHeight + mDividerWidth;
            }

            measurePair(left, child, widthMeasureSpec, heightMeasureSpec);
            left = null;

        }

        if (left != null) {

            if (index > 1) {
                mTotalHeight = mTotalHeight + mDividerWidth;
            }

            measurePair(left, null, widthMeasureSpec, heightMeasureSpec);
        }

        mTotalHeight += (getPaddingTop() + getPaddingBottom());

        setMeasuredDimension(widthMeasureSpec, resolveSizeAndState(mTotalHeight, heightMeasureSpec, 0));

    }

    private void measurePair(View left, View right, int widthMeasureSpec, int heightMeasureSpec) {

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthSpec = widthMeasureSpec;
        int heightSpec = heightMeasureSpec;

        if (widthMode != MeasureSpec.EXACTLY) {
            widthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.AT_MOST) {
            heightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
        }

        if (right == null) {

            final MarginLayoutParams leftLp = (MarginLayoutParams) left.getLayoutParams();

            // 强制组件宽
            leftLp.width = LayoutParams.MATCH_PARENT;

            if (leftLp.height == LayoutParams.MATCH_PARENT) {
                leftLp.height = LayoutParams.WRAP_CONTENT;
            }

            measureChildWithMargins(left, widthSpec, 0, heightSpec, mTotalHeight);
            mTotalHeight += left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
            return;
        }

        final MarginLayoutParams leftLp = (MarginLayoutParams) left.getLayoutParams();
        final MarginLayoutParams rightLp = (MarginLayoutParams) right.getLayoutParams();

        // 强制组件宽
        leftLp.width = LayoutParams.MATCH_PARENT;
        rightLp.width = LayoutParams.MATCH_PARENT;

        if (leftLp.height == LayoutParams.MATCH_PARENT) {
            leftLp.height = LayoutParams.WRAP_CONTENT;
        }
        if (rightLp.height == LayoutParams.MATCH_PARENT) {
            rightLp.height = LayoutParams.WRAP_CONTENT;
        }

        final int widthUsed = (widthSize - getPaddingLeft() - getPaddingRight() - mDividerWidth) / 2 + mDividerWidth;

        measureChildWithMargins(left, widthSpec, widthUsed, heightSpec, mTotalHeight);
        measureChildWithMargins(right, widthSpec, widthUsed, heightSpec, mTotalHeight);

        final int leftHeight = left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
        final int rightHeight = right.getMeasuredHeight() + rightLp.topMargin + rightLp.bottomMargin;

        // 高度矫正
        if (leftHeight > rightHeight) {
            rightLp.height = leftHeight - rightLp.topMargin - rightLp.bottomMargin;
            measureChildWithMargins(right, widthSpec, widthUsed, heightSpec, mTotalHeight);
        } else if (rightHeight > leftHeight) {
            leftLp.height = rightHeight - leftLp.topMargin - leftLp.bottomMargin;
            measureChildWithMargins(left, widthSpec, widthUsed, heightSpec, mTotalHeight);
        }

        mTotalHeight += Math.max(leftHeight, rightHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

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
                mTotalHeight = mTotalHeight + mDividerWidth;
            }

            layoutPair(left, child, l, r);

            left = null;

        }

        if (left != null) {

            if (index > 1) {
                mTotalHeight = mTotalHeight + mDividerWidth;
            }

            layoutPair(left, null, l, r);
        }

    }

    private void layoutPair(View left, View right, int l, int r) {

        if (right == null) {

            final MarginLayoutParams lp = (MarginLayoutParams) left.getLayoutParams();

            left.layout(l + getPaddingLeft() + lp.leftMargin,
                    mTotalHeight + lp.topMargin,
                    r - getPaddingRight() - lp.rightMargin,
                    mTotalHeight + lp.topMargin + left.getMeasuredHeight());

            return;
        }

        final MarginLayoutParams leftLp = (MarginLayoutParams) left.getLayoutParams();
        final MarginLayoutParams rightLp = (MarginLayoutParams) right.getLayoutParams();

        final int leftHeight = left.getMeasuredHeight() + leftLp.topMargin + leftLp.bottomMargin;
        final int rightHeight = right.getMeasuredHeight() + rightLp.topMargin + rightLp.bottomMargin;

        if (leftHeight > rightHeight) {

            left.layout(l + getPaddingLeft() + leftLp.leftMargin,
                    mTotalHeight + leftLp.topMargin,
                    l + getPaddingLeft() + leftLp.leftMargin + left.getMeasuredWidth(),
                    mTotalHeight + leftLp.topMargin + left.getMeasuredHeight());

            right.layout(left.getRight() + leftLp.rightMargin + mDividerWidth + rightLp.leftMargin,
                    mTotalHeight + (leftHeight - rightHeight) / 2 + rightLp.topMargin,
                    r - getPaddingRight() - rightLp.rightMargin,
                    mTotalHeight + (leftHeight - rightHeight) / 2 + rightLp.topMargin + right.getMeasuredHeight());

        } else {

            left.layout(l + getPaddingLeft() + leftLp.leftMargin,
                    mTotalHeight + (rightHeight - leftHeight) / 2 + leftLp.topMargin,
                    l + getPaddingLeft() + leftLp.leftMargin + left.getMeasuredWidth(),
                    mTotalHeight + (rightHeight - leftHeight) / 2 + leftLp.topMargin + left.getMeasuredHeight());

            right.layout(left.getRight() + leftLp.rightMargin + mDividerWidth + rightLp.leftMargin,
                    mTotalHeight + rightLp.topMargin,
                    r - getPaddingRight() - rightLp.rightMargin,
                    mTotalHeight + rightLp.topMargin + right.getMeasuredHeight());

        }

        mTotalHeight += Math.max(leftHeight, rightHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFrame(canvas);
    }

    private void drawFrame(final Canvas canvas) {

        if (mFrameColor == 0) {
            return;
        }

        canvas.save();

        final Rect rect = new Rect();
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE) {
                continue;
            }

            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            rect.set(child.getLeft() - lp.leftMargin,
                    child.getTop() - lp.topMargin,
                    child.getRight() + lp.rightMargin,
                    child.getBottom() + lp.bottomMargin);

            canvas.clipRect(rect, Region.Op.XOR);

        }

        canvas.drawColor(mFrameColor);

        canvas.restore();

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

    @Override
    public CharSequence getAccessibilityClassName() {
        return CategoryPairLayout.class.getName();
    }
}
