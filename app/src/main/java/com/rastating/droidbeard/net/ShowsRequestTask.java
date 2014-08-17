package com.rastating.droidbeard.net;

import com.rastating.droidbeard.ApiResponseListener;
import com.rastating.droidbeard.Preferences;
import com.rastating.droidbeard.TVShow;
import com.rastating.droidbeard.TVShowComparator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ShowsRequestTask extends AsyncTask<Void, Void, List<TVShow>> {
    private Context mContext;
    private ArrayList<ApiResponseListener<TVShow>> mResponseListeners;

    public ShowsRequestTask(Context context) {
        mContext = context;
        mResponseListeners = new ArrayList<ApiResponseListener<TVShow>>();
    }

    public void addResponseListener(ApiResponseListener<TVShow> listener) {
        mResponseListeners.add(listener);
    }

    private String getShowInformationJson(int id) {
        String uri = null;
        String body = null;
        String format = "%sapi/%s/?cmd=show&tvdbid=%d";
        Preferences preferences = new Preferences(mContext);
        HttpClient client = new DefaultHttpClient();

        try {
            uri = String.format(format, preferences.getSickbeardUrl(), preferences.getApiKey(), id);
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
        catch (ClientProtocolException ex) {
            // TODO: handle exception
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            // TODO: handle exception
            System.out.println(ex.getMessage());
        }

        return body;
    }

    @Override
    protected List<TVShow> doInBackground(Void... voids) {
        List<TVShow> shows = new ArrayList<TVShow>();
        Preferences preferences = new Preferences(mContext);
        HttpClient client = new DefaultHttpClient();
        String body = null;
        String format = "%sapi/%s/?cmd=shows";
        String uri = String.format(format, preferences.getSickbeardUrl(), preferences.getApiKey());

        try {
            HttpGet request = new HttpGet(uri);
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();

            if (status.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                response.getEntity().writeTo(stream);
                stream.close();
                body = stream.toString();
            }
            else {
                response.getEntity().getContent().close();
                throw new IOException(status.getReasonPhrase());
            }
        }
        catch (ClientProtocolException ex) {
            // TODO: handle exception
            System.out.println(ex.getMessage());
        }
        catch (IOException ex) {
            // TODO: handle exception
            System.out.println(ex.getMessage());
        }

        try {
            JSONObject root = new JSONObject(body);
            JSONObject data = root.getJSONObject("data");
            Iterator<String> keys = data.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject show = data.getJSONObject(key);
                int tvdbid = show.getInt("tvdbid");

                TVShow tvShow = new TVShow(show.getString("show_name"));
                tvShow.setNetwork(show.getString("network"));

                try {
                    String nextDateString = show.getString("next_ep_airdate");
                    if (!nextDateString.equals("")) {
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(nextDateString);
                        tvShow.setNextAirDate(date);
                    }
                } catch (ParseException e) {
                    // TODO: Handle exception
                    e.printStackTrace();
                }

                shows.add(tvShow);
                Log.v("droidbeard", "Added " + tvShow.getName());
            }
        }
        catch (JSONException ex) {
            // TODO: handle exception
            System.out.println(ex.getMessage());
        }

        return shows;
    }

    @Override
    protected void onPostExecute(List<TVShow> shows) {
        Collections.sort(shows, new TVShowComparator());

        for (ApiResponseListener<TVShow> listener : mResponseListeners) {
            listener.onApiRequestFinished(shows.toArray(new TVShow[shows.size()]));
        }
    }
}