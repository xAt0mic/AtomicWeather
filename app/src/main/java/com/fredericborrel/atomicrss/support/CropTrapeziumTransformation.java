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

    private final float pointA = AtomicRSS.context.getResources().getDimension(R.dimen.item_image_width);
    private final float pointB = AtomicRSS.context.getResources().getDimension(R.dimen.item_image_height);

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bmp = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Path trapPath = new Path();
        trapPath.moveTo(0,0);
        trapPath.lineTo(pointA,0);
        trapPath.lineTo(pointB, pointB);
        trapPath.lineTo(0, pointB);
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
