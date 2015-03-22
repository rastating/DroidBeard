/*
     DroidBeard - a free, open-source Android app for managing SickBeard
     Copyright (C) 2014-2015 Robert Carr

     This program is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     This program is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program.  If not, see http://www.gnu.org/licenses/.
*/

package com.rastating.droidbeard.net;

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