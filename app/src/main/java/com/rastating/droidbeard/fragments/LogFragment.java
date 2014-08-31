package com.rastating.droidbeard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rastating.droidbeard.R;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.FetchLogsTask;
import com.rastating.droidbeard.ui.CrossFader;
import com.rastating.droidbeard.ui.LoadingAnimation;

import java.io.InputStream;

public class LogFragment extends DroidbeardFragment implements ApiResponseListener<String[]> {
    private WebView mWebView;
    private View mErrorContainer;
    private ImageView mLoadingImage;
    private TextView mErrorMessage;

    public LogFragment() {
        setTitle(R.string.title_logs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_log, null, false);
        mWebView = (WebView) root.findViewById(R.id.web_view);
        mErrorContainer = root.findViewById(R.id.error_container);
        mLoadingImage = (ImageView) root.findViewById(R.id.loading);
        mErrorMessage = (TextView) root.findViewById(R.id.error_message);

        showLoadingAnimation();
        FetchLogsTask task = new FetchLogsTask(getActivity());
        task.addResponseListener(this);
        task.execute();

        return root;
    }

    @Override
    public void onApiRequestFinished(String[] result) {
        if (result != null) {
            String entries = "";
            for (String entry : result) {
                entries += entry + "<br>";
            }

            try {
                InputStream stream = getResources().openRawResource(R.raw.log_template);
                byte[] b = new byte[stream.available()];
                stream.read(b);
                String html = new String(b);
                mWebView.loadData(html.replace("{{logs}}", entries), "text/html", null);
                stream.close();
                showWebView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            showError(getString(R.string.error_fetching_logs));
        }
    }

    protected void showError(String message) {
        mErrorMessage.setText(message);
        mErrorContainer.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
        mLoadingImage.clearAnimation();
        mLoadingImage.setVisibility(View.GONE);
    }

    protected void showLoadingAnimation() {
        mErrorContainer.setVisibility(View.GONE);
        mWebView.setVisibility(View.GONE);
        mLoadingImage.setVisibility(View.VISIBLE);
        mLoadingImage.startAnimation(new LoadingAnimation());
    }

    protected void showWebView() {
        mErrorContainer.setVisibility(View.GONE);
        new CrossFader(mLoadingImage, mWebView, 500).start();
    }
}