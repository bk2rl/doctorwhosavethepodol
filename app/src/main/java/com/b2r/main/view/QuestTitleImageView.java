package com.b2r.main.view;


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

    private int strokeFirstColor;
    private int strokeSecondColor;
    private int strokeWidth;
    private String backgroundImage;
    private ShapeDrawable backgroundDrawable;
    private ShapeDrawable progressBarDrawable;
    private RectF progressRectangle;
    private int sweepAngle;
    private Paint secondPaint;
    private BitmapShader backgroundShader;


    public QuestTitleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context, attrs);
        backgroundDrawable = new ShapeDrawable(new OvalShape());
        progressBarDrawable = new ShapeDrawable(new OvalShape());
        progressBarDrawable.getPaint().setColor(strokeFirstColor);
        secondPaint = new Paint();
        secondPaint.setColor(strokeSecondColor);
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
        try {
            if (backgroundShader == null) {
                bitmapStream = getResources().getAssets().open(String.format("user/%s", backgroundImage));
                Bitmap userImage = BitmapFactory.decodeStream(bitmapStream);
                userImage = Bitmap.createScaledBitmap(userImage, getWidth() - 2 * strokeWidth, getHeight() - 2 * strokeWidth, false);
//                userImage = Bitmap.createScaledBitmap(userImage, getWidth(), getHeight(), false);
                backgroundShader = new BitmapShader(userImage, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            }

            backgroundDrawable.getPaint().setShader(backgroundShader);
            backgroundDrawable.getShape().resize(getWidth() - 2 * strokeWidth, getHeight() - 2 * strokeWidth);
            backgroundDrawable.setBounds(strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth);

            progressBarDrawable.getShape().resize(getWidth(), getHeight());

            progressRectangle = new RectF(0, 0, getWidth(), getHeight());

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
        invalidate();
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        progressBarDrawable.draw(canvas);
        canvas.drawArc(progressRectangle, -90, sweepAngle, true, secondPaint);
        backgroundDrawable.draw(canvas);
    }

}