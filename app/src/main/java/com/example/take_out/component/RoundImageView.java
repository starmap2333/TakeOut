package com.example.take_out.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    float width, height;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int n = 30;

        if (width > n && height > n) {
            Path path = new Path();
            path.moveTo(n, 0);
            path.lineTo(width - n, 0);
            path.quadTo(width, 0, width, n);
            path.lineTo(width, height - n);
            path.quadTo(width, height, width - n, height);
            path.lineTo(n, height);
            path.quadTo(0, height, 0, height - n);
            path.lineTo(0, n);
            path.quadTo(0, 0, n, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}