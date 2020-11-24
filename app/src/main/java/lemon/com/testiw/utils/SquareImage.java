package lemon.com.testiw.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class SquareImage extends androidx.appcompat.widget.AppCompatImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public SquareImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // getMeasuredWidth - return width of phone screen
        // width equals height to make picture square
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
