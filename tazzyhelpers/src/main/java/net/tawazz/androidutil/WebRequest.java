package net.tawazz.androidutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by tawanda on 2/03/2016.
 */
public class WebRequest {

    private static WebRequest instance = null;
    private final RequestQueue requestQueue;
    private final ImageLoader imageLoader;

    private WebRequest(Context context){

        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static WebRequest getInstance(Context context){

        if(instance == null){
            instance = new WebRequest(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public DefaultRetryPolicy retryPolicy() {

        return new DefaultRetryPolicy(120000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
}
