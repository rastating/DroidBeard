package com.rastating.droidbeard.net;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientManager {
    private HttpClient mClient;

    public final static HttpClientManager INSTANCE = new HttpClientManager();

    private HttpClientManager() {
        mClient = new DefaultHttpClient();
    }

    public HttpClient getClient() {
        return mClient;
    }
}