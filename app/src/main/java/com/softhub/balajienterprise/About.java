package com.softhub.balajienterprise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class About extends AppCompatActivity {

    WebView about;
    String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about = findViewById(R.id.about);
        t = "<p>Balaji Enterprises are involved in supply of high quality cotton<br>\n" +
                "wiping rags under the brand name Easy Rags. Core values of Easy Rags are honesty<br>\n" +
                "and integrity which helps us in providing better quality cleaning rags at competitive<br>\n" +
                "pricing with quick delivery service to suit the requirements of our valuable customers as<br>\n" +
                "per specific industry.<br>\n" +
                "&#61623; For Easy Rags products we follow a work process flow of Material Selection, grading &amp;<br>\n" +
                "sorting of material, cutting, metal detection to remove unwanted objects like buttons, zips<br>\n" +
                "etc. and manual checking, packing in 10 kg compressed bags and lastly dispatch for<br>\n" +
                "final delivery to our customers.<br>\n" +
                "&#61623; Workforce / personnel constitute the key factor to the growth &amp; success of any<br>\n" +
                "Organization. Our employees are our strength whose zeal &amp; hard work helps us to serve<br>\n" +
                "our esteemed customers better.<br>\n" +
                "&#61623; Quality is the major aspect for any product and is the result of high intention, sincere<br>\n" +
                "effort and smart and skillful execution. Easy Rags has a satisfied Customer base as we<br>\n" +
                "give utmost importance to Quality. Thus once a client deals with us, remains with us<br>\n" +
                "forever as he is rest assured of getting quality product.<br>\n" +
                "&#61623; At Easy Rags we give utmost importance to each and every enquiry of valuable<br>\n" +
                "customer be it for small quantity or big volume and ensure that our Customer Service<br>\n" +
                "desk handles it with proper care, attention &amp; respect, resolves all the queries of every<br>\n" +
                "customer and do continuous improvement as per feedback received from them.<br>\n" +
                "</p>";

        about.loadDataWithBaseURL(null, t, "text/html", "utf-8", null);
    }
}