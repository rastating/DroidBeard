package com.rastating.droidbeard.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Pair;

import com.rastating.droidbeard.Preferences;
import com.rastating.droidbeard.entities.TVShow;

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

public class FetchShowTask extends SickbeardAsyncTask<Integer, Void, TVShow> {

    public FetchShowTask(Context context) {
        super(context);
    }

    private Bitmap getBanner(int tvdbid) {
        ArrayList<Pair<String, Object>> params = new ArrayList<Pair<String, Object>>();
        params.add(new Pair<String, Object>("tvdbid", tvdbid));
        return getBitmap("show.getbanner", params);
    }

    @Override
    protected TVShow doInBackground(Integer... integers) {
        int tvdbid = integers[0];
        TVShow show = new TVShow();
        Bitmap banner = getBanner(tvdbid);
        show.setBanner(banner);
        return show;
    }

    @Override
    protected void onPostExecute(TVShow show) {
        List<ApiResponseListener<TVShow>> listeners = getResponseListeners();
        for (ApiResponseListener<TVShow> listener : listeners) {
            listener.onApiRequestFinished(show);
        }
    }
}
