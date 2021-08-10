package com.softhub.balajienterprise;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class InvoiceActivity extends AppCompatActivity {

    private WebView wv_register;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        getSupportActionBar().hide();

        ImageView backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle b = getIntent().getExtras();



        wv_register = (WebView) findViewById(R.id.webView);
        wv_register.setWebViewClient(new MyBrowser());
        url = "http://meradaftar.com/balaji/index.php/home/status_order_print/"+b.getString("order_id");
        Log.d("Invoice URL:", url);


        wv_register.getSettings().setDomStorageEnabled(true);
        wv_register.getSettings().setAppCacheEnabled(true);
        wv_register.getSettings().setLoadsImagesAutomatically(true);
        wv_register.getSettings().setBuiltInZoomControls(true);
        wv_register.getSettings().setDisplayZoomControls(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv_register.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wv_register.getSettings().setJavaScriptEnabled(true);
        wv_register.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv_register.loadUrl(url);

    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            progressDialog.dismiss();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }
}