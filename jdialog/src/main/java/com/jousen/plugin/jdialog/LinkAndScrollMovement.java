package com.jousen.plugin.jdialog;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.TextView;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

/**
 * This class unites {@link ScrollingMovementMethod} and {@link android.text.method.LinkMovementMethod}
 * While scrolling a TextView, the spans stay locked until the next tap event.
 *
 * @author nuclearfog
 * @version 1.4
 */
class LinkAndScrollMovement extends ScrollingMovementMethod {

    private static final LinkAndScrollMovement instance = new LinkAndScrollMovement();

    /**
     * setup the x axis threshold to disable click events.
     */
    private static final int THRESHOLD_WIDTH_DIVIDER = 6;

    /**
     * setup the y axis threshold to disable click events.
     */
    private static final int THRESHOLD_HEIGHT_DIVIDER = 3;

    private int thresholdX = THRESHOLD_WIDTH_DIVIDER;
    private int thresholdY = THRESHOLD_HEIGHT_DIVIDER;

    private int xScroll = 0;
    private int yScroll = 0;

    private LinkAndScrollMovement() {
        super();
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        switch (event.getAction()) {
            case ACTION_DOWN:
                lockParentScrolling(widget, true);
                xScroll = widget.getScrollX();
                yScroll = widget.getScrollY();
                break;

            case ACTION_UP:
                lockParentScrolling(widget, false);
                int deltaX = Math.abs(widget.getScrollX() - xScroll);
                int deltaY = Math.abs(widget.getScrollY() - yScroll);
                if (deltaY <= widget.getTextSize() / thresholdY && deltaX <= widget.getWidth() / thresholdX) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();
                    x += widget.getScrollX();
                    y += widget.getScrollY();
                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);
                    ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
                    if (link.length > 0) {
                        link[0].onClick(widget);
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    /**
     * sets maximum threshold to lock link clicks
     *
     * @param thrX threshold for x axis
     * @param thrY threshold for y axis
     */
    public LinkAndScrollMovement setThreshold(int thrX, int thrY) {
        thresholdX = thrX;
        thresholdY = thrY;
        return this;
    }

    /**
     * lock parent view scrolling
     *
     * @param widget interacting TextView
     * @param lock   true if parent views scrolling should be locked
     */
    private void lockParentScrolling(TextView widget, boolean lock) {
        ViewParent parent = widget.getParent();
        int lineCount = widget.getLineCount();
        int maxLines = widget.getMaxLines();
        if (parent != null && maxLines > 0 && lineCount > maxLines) {
            parent.requestDisallowInterceptTouchEvent(lock);
        }
    }

    /**
     * Get singleton instance of the movement method
     *
     * @return LinkAndScrollingMovementMethod object
     */
    public static LinkAndScrollMovement getInstance() {
        return instance;
    }
}