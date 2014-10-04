package com.rastating.droidbeard.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Pair;

import com.rastating.droidbeard.Preferences;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

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

    protected String getJson(String cmd) {
        return getJson(cmd, null);
    }

    protected String getJson(String cmd, String paramKey, Object paramValue) {
        List<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>(paramKey, paramValue));
        return getJson(cmd, params);
    }

    protected String getJson(String cmd, List<Pair<String, Object>> params) {
        String uri = null;
        String body = null;
        String format = "%sapi/%s/?cmd=%s";
        Preferences preferences = new Preferences(mContext);
        HttpClient client = HttpClientManager.INSTANCE.getClient();

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
                HttpEntity entity = response.getEntity();
                entity.writeTo(stream);
                stream.close();
                entity.consumeContent();
                body = stream.toString();
                stream.close();
            } else {
                HttpEntity entity = response.getEntity();
                entity.getContent().close();
                entity.consumeContent();
                throw new IOException(status.getReasonPhrase());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return body;
    }

    protected List<ApiResponseListener<Result>> getResponseListeners() {
        return mResponseListeners;
    }

    @Override
    protected void onPostExecute(Result result) {
        List<ApiResponseListener<Result>> listeners = getResponseListeners();
        for (ApiResponseListener<Result> listener : listeners) {
            listener.onApiRequestFinished(result);
        }
    }

    public void start(Executor executor, Params... args) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, args);
        }
        else {
            this.execute(args);
        }
    }

    public void start(Params... args) {
        this.start(AsyncTask.THREAD_POOL_EXECUTOR, args);
    }
}