package com.example.vineetpatil.moviesearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Favorites {
    public static final String TAG = Favorites.class.getName();

    private static final String FAVORITES_SHARED_PREF_FILE = "com.example.vineetpatil.moviesearch.FAVORITES";

    private static Favorites sInstance = null;

    private final SharedPreferences sharedPreferences;

    private Favorites(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public static Favorites getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Favorites(context.getApplicationContext().getSharedPreferences(FAVORITES_SHARED_PREF_FILE, Context.MODE_PRIVATE));
        }
        return sInstance;
    }

    public synchronized TitleRecord getFavorite(String imdbID) {
        if (sharedPreferences.contains(imdbID)) {
            String titleRecordString = sharedPreferences.getString(imdbID, "");
            if (!TextUtils.isEmpty(titleRecordString)) {
                return TitleRecord.fromJson(titleRecordString);
            }
        }
        return null;
    }

    public synchronized boolean putFavorite(String imdbID, TitleRecord titleRecord) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(imdbID, titleRecord.toString());
        editor.apply();  // apply() is ASynchronous and updates inMemory copy immediately and asynchronously updates disk copy
        //editor.commit();

        return true;
    }

    public synchronized boolean deleteFavorite(String imdbID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(imdbID);
        editor.apply();  // apply() is ASynchronous and updates inMemory copy immediately and asynchronously updates disk copy
        //editor.commit();

        return true;
    }

    public synchronized List<TitleRecord> getAllFavorites() {
        Map<String, String> favoritesMap = (Map<String, String>) sharedPreferences.getAll();
        if (favoritesMap != null && favoritesMap.size() > 0) {
            List<TitleRecord> titleRecords = new ArrayList<>();
            for (String value : favoritesMap.values()) {
                TitleRecord titleRecord = TitleRecord.fromJson(value);
                titleRecords.add(titleRecord);
            }
            return titleRecords;
        }

        return Collections.emptyList();
    }

    public synchronized boolean putAllFavorite(List<TitleRecord> titleRecords) {
        if (titleRecords != null && titleRecords.size() > 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            for (TitleRecord titleRecord : titleRecords) {
                editor.putString(titleRecord.getImdbID(), titleRecord.toString());
            }
            editor.apply();  // apply() is ASynchronous and updates inMemory copy immediately and asynchronously updates disk copy
            //editor.commit();

            return true;
        }
        return false;
    }

    public synchronized boolean deleteAllFavorites() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();  // apply() is ASynchronous and updates inMemory copy immediately and asynchronously updates disk copy
        //editor.commit();

        return true;
    }
}
