package com.rastating.droidbeard;

public interface ApiResponseListener<T> {
    public void onApiRequestFinished(T[] objects);
}