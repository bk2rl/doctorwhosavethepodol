package com.b2r.main.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.b2r.main.R;

import java.io.IOException;
import java.io.InputStream;



public class QuestTitleImageView extends android.widget.ImageView {

    private int progressBarWidth;
    private int strokeFirstColor;
    private int strokeSecondColor;
    private int strokeWidth;
    private String backgroundImage;
    private ShapeDrawable backgroundDrawable;
    private ShapeDrawable progressBarDrawable;
    private ShapeDrawable sphereDrawable;
    private RectF progressRectangle;
    private int sweepAngle;
    private Paint secondPaint;
    private BitmapShader backgroundShader;
    private final int sphereWidth = 10;
    private int spherePadding;
    private int pad;
    private int backgroundWidth;
    private float sinX;
    private float cosY;
    private int x;
    private int y;


    public QuestTitleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context, attrs);
        backgroundDrawable = new ShapeDrawable(new OvalShape());
        progressBarDrawable = new ShapeDrawable(new OvalShape());
        progressBarDrawable.getPaint().setColor(strokeFirstColor);
        secondPaint = new Paint();
        secondPaint.setColor(strokeSecondColor);
        sphereDrawable = new ShapeDrawable(new OvalShape());
        sweepAngle = 0;
    }

    //доступны методы для определния высоты и ширины
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        prepareDrawables();
    }

    private void setCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray customAttributes = null;
        try {
            customAttributes = context.obtainStyledAttributes(attrs, R.styleable.QuestTitleImageView, 0, 0);
            strokeFirstColor = customAttributes.getColor(R.styleable.QuestTitleImageView_stroke_first_color, 0);
            strokeSecondColor = customAttributes.getColor(R.styleable.QuestTitleImageView_stroke_second_color, 0);
            strokeWidth = customAttributes.getDimensionPixelSize(R.styleable.QuestTitleImageView_stroke_width, 0);
            backgroundImage = customAttributes.getString(R.styleable.QuestTitleImageView_avatar_asset);
        } finally {
            if (customAttributes != null) {
                customAttributes.recycle();
            }
        }
    }


    //call after view is layouted
    private void prepareDrawables() {
        InputStream bitmapStream = null;

        if (spherePadding == 0) {
            spherePadding = (sphereWidth - strokeWidth) / 2;
        }

        if (progressBarWidth == 0) {
            progressBarWidth = getWidth() - 2 * spherePadding;
        }

        if (backgroundWidth == 0) {
            backgroundWidth = progressBarWidth - 2 * strokeWidth;
        }

        try {
            if (backgroundShader == null) {
                bitmapStream = getResources().getAssets().open(String.format("user/%s", backgroundImage));
                Bitmap userImage = BitmapFactory.decodeStream(bitmapStream);
                userImage = Bitmap.createScaledBitmap(userImage, backgroundWidth, backgroundWidth, false);
//                userImage = Bitmap.createScaledBitmap(userImage, getWidth(), getHeight(), false);
                backgroundShader = new BitmapShader(userImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            }

            progressBarDrawable.getShape().resize(progressBarWidth, progressBarWidth);
            progressBarDrawable.setBounds(spherePadding, spherePadding, spherePadding + progressBarWidth, spherePadding + progressBarWidth);

            backgroundDrawable.getPaint().setShader(backgroundShader);
            backgroundDrawable.getShape().resize(backgroundWidth, backgroundWidth);
            backgroundDrawable.setBounds(strokeWidth + spherePadding, strokeWidth + spherePadding,  strokeWidth + spherePadding + backgroundWidth, strokeWidth + spherePadding + backgroundWidth);

            sphereDrawable.getShape().resize(sphereWidth,sphereWidth);
            sphereDrawable.getPaint().setColor(strokeSecondColor);

//            progressRectangle = new RectF(progressBarDrawable.getBounds());
              progressRectangle = new RectF(spherePadding,spherePadding,spherePadding+progressBarWidth,spherePadding+progressBarWidth);

            pad = getWidth() / 2;
            sinX = (float) ((progressBarWidth - strokeWidth) / 2 * Math.sin(sweepAngle));
            cosY = - (float) ((progressBarWidth - strokeWidth) / 2 * Math.cos(sweepAngle));
            x = Math.round(sinX + pad);
            y = Math.round(cosY + pad);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bitmapStream != null) {
                    bitmapStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setProgress(int progress){
        sweepAngle = progress;
        sinX = (float) ((progressBarWidth - strokeWidth) / 2 * Math.sin(sweepAngle));
        cosY = - (float) ((progressBarWidth - strokeWidth) / 2 * Math.cos(sweepAngle));
        x = Math.round(sinX + pad);
        y = Math.round(cosY + pad);
        invalidate();
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        sphereDrawable.setBounds(x -sphereWidth/2, y -sphereWidth/2, x +sphereWidth/2, y +sphereWidth/2);
        progressBarDrawable.draw(canvas);
        canvas.drawArc(progressRectangle, -90, sweepAngle, true, secondPaint);
//        canvas.drawCircle(,,5,secondPaint);
        backgroundDrawable.setBounds(strokeWidth + spherePadding, strokeWidth + spherePadding,  strokeWidth + spherePadding + backgroundWidth, strokeWidth + spherePadding + backgroundWidth);
        backgroundDrawable.draw(canvas);
        sphereDrawable.draw(canvas);
    }

}