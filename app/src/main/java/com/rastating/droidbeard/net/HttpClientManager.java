package com.rastating.droidbeard.net;

import android.content.Context;

import com.rastating.droidbeard.Application;
import com.rastating.droidbeard.Preferences;

import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.security.KeyStore;

public class HttpClientManager {
    private HttpClient mClient;

    public final static HttpClientManager INSTANCE = new HttpClientManager();

    private HttpClientManager() {
        invalidateClient();
    }

    public HttpClient getClient() {
        return mClient;
    }

    private void setupHttpCredentials() {
        try {
            Preferences preferences = new Preferences(Application.getContext());
            Credentials credentials = new UsernamePasswordCredentials(preferences.getHttpUsername(), preferences.getHttpPassword());
            ((AbstractHttpClient) mClient).getCredentialsProvider().setCredentials(new AuthScope(preferences.getAddress(), preferences.getPort()), credentials);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invalidateClient() {
        Preferences preferences = new Preferences(com.rastating.droidbeard.Application.getContext());
        if (preferences.getTrustAllCertificatesFlag()) {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                SSLSocketFactory sf = new TrustedSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

                mClient = new DefaultHttpClient(ccm, params);
            } catch (Exception e) {
                e.printStackTrace();
                mClient = new DefaultHttpClient();
            }
        }
        else {
            mClient = new DefaultHttpClient();
        }

        setupHttpCredentials();
    }
}