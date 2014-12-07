package com.rastating.droidbeard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity implements View.OnClickListener {

    private String getVersionName() {
        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String versionName = "Unknown";

        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);

        findViewById(R.id.rastating_link).setOnClickListener(this);
        findViewById(R.id.adam_prescott_link).setOnClickListener(this);
        findViewById(R.id.reddit_link).setOnClickListener(this);
        findViewById(R.id.phyushin_link).setOnClickListener(this);
        findViewById(R.id.official_website_link).setOnClickListener(this);
        findViewById(R.id.facebook_link).setOnClickListener(this);
        findViewById(R.id.google_link).setOnClickListener(this);

        ((TextView) findViewById(R.id.version_number)).setText("Version " + getVersionName());
    }

    @Override
    public void onClick(View view) {
        String uri = "";
        switch (view.getId()) {
            case R.id.rastating_link:
                uri = "https://twitter.com/iamrastating";
                break;

            case R.id.adam_prescott_link:
                uri = "https://twitter.com/Adam_Prescott";
                break;

            case R.id.reddit_link:
                uri = "http://www.reddit.com/r/sickbeard/";
                break;

            case R.id.phyushin_link:
                uri = "https://twitter.com/Phyushin";
                break;

            case R.id.official_website_link:
                uri = "http://www.droidbeard.com/";
                break;

            case R.id.facebook_link:
                uri = "https://www.facebook.com/droidbeard";
                break;

            case R.id.google_link:
                uri = "https://plus.google.com/u/0/communities/109361186281608237451";
                break;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }
}