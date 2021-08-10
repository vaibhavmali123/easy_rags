package com.softhub.balajienterprise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

public class Terms extends AppCompatActivity {

    WebView terms;
    String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        terms = findViewById(R.id.terms);
       t = "Terms &amp; Conditions:<br>\n" +
               "&#61623; Transportation: Ex-works Tathawade office.<br>\n" +
               "&#61623; Terms of Payment: 100% advance / against delivery.<br>\n" +
               "&#61623; Prices: All prices mentioned in this offer are in Indian Rupees.<br>\n" +
               "&#61623; Delivery: Ex-stock Available. For out of stock items the delivery will be 2 weeks from the date of<br>\n" +
               "confirmed order.<br>\n" +
               "&#61623; Any changes in the taxes will be extra. Excise: Not Applicable, Shipping charges to be borne by<br>\n" +
               "the buyer.<br>\n" +
               "&#61623; Penalty against cancellation of order: Once the order is placed, the same cannot be cancelled<br>\n" +
               "under any circumstances. In case any cancellation done due to unavoidable circumstances, then<br>\n" +
               "the penalty of 10% of the total value of the order shall be debited to your account.<br>\n" +
               "&#61623; Any change in statutory laws shall be applicable accordingly.";

        terms.loadDataWithBaseURL(null, t, "text/html", "utf-8", null);
    }


}