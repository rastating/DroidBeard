package com.rastating.droidbeard.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Pair;

import com.rastating.droidbeard.Preferences;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class SickbeardAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private Context mContext;
    private List<ApiResponseListener<Result>> mResponseListeners;

    public SickbeardAsyncTask(Context context) {
        mContext = context;
        mResponseListeners = new ArrayList<ApiResponseListener<Result>>();
    }

    public void addResponseListener(ApiResponseListener<Result> listener) {
        if (!mResponseListeners.contains(listener)) {
            mResponseListeners.add(listener);
        }
    }

    public void removeResponseListener(ApiResponseListener<Result> listener) {
        mResponseListeners.remove(listener);
    }

    protected Bitmap getBitmap(String cmd, List<Pair<String, Object>> params) {
        Bitmap bitmap = null;
        Preferences preferences = new Preferences(mContext);
        String format = "%sapi/%s/?cmd=%s";

        String uri = String.format(format, preferences.getSickbeardUrl(), preferences.getApiKey(), cmd);
        if (params != null) {
            for (Pair<String, Object> pair : params) {
                uri += "&" + pair.first + "=" + pair.second.toString();
            }
        }

        try {
            InputStream stream = new URL(uri).openStream();
            bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
        } catch (MalformedURLException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return bitmap;
    }

    protected Context getContext() {
        return mContext;
    }

    protected String getJson(String cmd, List<Pair<String, Object>> params) {
        String uri = null;
        String body = null;
        String format = "%sapi/%s/?cmd=%s";
        Preferences preferences = new Preferences(mContext);
        HttpClient client = new DefaultHttpClient();

        uri = String.format(format, preferences.getSickbeardUrl(), preferences.getApiKey(), cmd);
        if (params != null) {
            for (Pair<String, Object> pair : params) {
                uri += "&" + pair.first + "=" + pair.second.toString();
            }
        }

        try {
            HttpGet request = new HttpGet(uri);
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();

            if (status.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                response.getEntity().writeTo(stream);
                stream.close();
                body = stream.toString();
            } else {
                response.getEntity().getContent().close();
                throw new IOException(status.getReasonPhrase());
            }
        }
        catch (Exception e) {
            return null;
        }

        return body;
    }

    protected List<ApiResponseListener<Result>> getResponseListeners() {
        return mResponseListeners;
    }
}
