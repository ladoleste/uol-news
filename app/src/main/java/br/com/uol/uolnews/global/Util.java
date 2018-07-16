package br.com.uol.uolnews.global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;

/**
 * Created by Anderson on 12/07/2018.
 */
public class Util {
    public static Bitmap getBitmapFromVectorDrawable(@DrawableRes Integer drawableId, Context context) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        assert drawable != null;
        drawable = DrawableCompat.wrap(drawable).mutate();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
