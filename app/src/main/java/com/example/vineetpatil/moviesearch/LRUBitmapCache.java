package com.example.vineetpatil.moviesearch;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class LRUBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    public LRUBitmapCache() {
        super(20 * 1024 * 1024); // 20MB cache size
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
