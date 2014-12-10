package com.rastating.droidbeard.net;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ErrorReportTask extends AsyncTask<JSONObject, Void, Integer> {

    public JSONObject postData(String url, JSONObject obj) {
        HttpClient client = new DefaultHttpClient();
        String json = obj.toString();

        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);

            HttpResponse response = client.execute(post);
            String retval = EntityUtils.toString(response.getEntity());

            return new JSONObject(retval);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Integer doInBackground(JSONObject... exceptions) {
        String url = "http://blog.rastating.com:5050/exception";

        try {
            JSONObject result = postData(url, exceptions[0]);
            if (result != null && result.has("number")) {
                return result.getInt("number");
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {

    }
}