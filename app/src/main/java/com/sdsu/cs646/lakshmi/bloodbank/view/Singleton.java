package com.sdsu.cs646.lakshmi.bloodbank.view;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class Singleton
{
    private static Singleton instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context context;

    private Singleton(Context context)
    {
        context = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, (ImageLoader.ImageCache) new LruBitmapCache(
                LruBitmapCache.getCacheSize(context)));
    }
    public static synchronized Singleton getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new Singleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }
}
