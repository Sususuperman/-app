package com.cs.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 该监听在类容不能滚动的情况下无效
 *
 * @author
 */
public class SoftKeyBoardSatusView extends LinearLayout {

    private final int CHANGE_SIZE = 100;

    public SoftKeyBoardSatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SoftKeyBoardSatusView(Context context) {
        super(context);
        init();
    }

    private void init() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);

        if (oldw == 0 || oldh == 0)
            return;

        if (boardListener != null) {
            boardListener.keyBoardStatus(w, h, oldw, oldh);
            if (oldw != 0 && h - oldh < -CHANGE_SIZE) {
                boardListener.keyBoardVisable(Math.abs(h - oldh));
            }

            if (oldw != 0 && h - oldh > CHANGE_SIZE) {
                boardListener.keyBoardInvisable(Math.abs(h - oldh));
            }
        }
    }


    public interface SoftkeyBoardListener {
        void keyBoardStatus(int w, int h, int oldw, int oldh);

        void keyBoardVisable(int move);

        void keyBoardInvisable(int move);
    }

    SoftkeyBoardListener boardListener;

    public void setSoftKeyBoardListener(SoftkeyBoardListener boardListener) {
        this.boardListener = boardListener;
    }
}
