package com.fredericborrel.atomicrss.support;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import com.fredericborrel.atomicrss.AtomicRSS;
import com.fredericborrel.atomicrss.R;
import com.squareup.picasso.Transformation;

/**
 * Created by Frederic on 19/01/2017.
 */

public class CropTrapeziumTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Path trapPath = new Path();
        trapPath.moveTo(0,0);
        trapPath.lineTo(width,0);
        trapPath.lineTo(height, height);
        trapPath.lineTo(0, height);
        trapPath.close();

        canvas.drawPath(trapPath, paint);

        if(bmp != source)
            source.recycle();

        return bmp;
    }

    @Override
    public String key() {
        return "trapezium()";
    }
}
