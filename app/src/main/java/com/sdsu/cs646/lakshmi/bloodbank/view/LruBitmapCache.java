package com.sdsu.cs646.lakshmi.bloodbank.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import com.android.volley.toolbox.ImageLoader;



public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache

{
    public LruBitmapCache(int maxSize)
    {
        super(maxSize);
    }
    public LruBitmapCache(Context ctx)
    {
        this(getCacheSize(ctx));
    }
    @Override
    protected int sizeOf(String key, Bitmap value)
    {
        return value.getRowBytes() * value.getHeight();
    }

    // Returns a cache size equal to approximately three screens worth of images.
    public static int getCacheSize(Context ctx)
    {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;
        return screenBytes * 3;
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {

    }
}