package com.pinkcar.user;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.pinkcar.user.activities.login.IntroActivity;
import com.pinkcar.user.helper.SharedHelper;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;


/**
 * Created by Amit on 29/01/17.
 */

public class ClassLuxApp extends Application {

    public static final String TAG = ClassLuxApp.class
            .getSimpleName();
    private static ClassLuxApp mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized ClassLuxApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        EmojiManager.install(new IosEmojiProvider());

        setLocale(SharedHelper.getKey(ClassLuxApp.this, "selectedlanguage"));
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void cancelRequestInQueue(String tag) {
        VolleyLog.DEBUG = true;
        getRequestQueue().cancelAll(tag);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        VolleyLog.DEBUG = true;
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        VolleyLog.DEBUG = true;
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}
