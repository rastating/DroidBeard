package com.rastating.droidbeard.net;

public interface ApiResponseListener<T> {
    public void onApiRequestFinished(T result);
}