package com.example.romancesample;

import android.app.Application;

public class MyApplication extends Application {
    private String photoUri;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
