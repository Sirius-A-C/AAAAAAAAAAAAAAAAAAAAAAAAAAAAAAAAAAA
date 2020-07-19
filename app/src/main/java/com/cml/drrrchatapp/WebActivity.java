package com.cml.drrrchatapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private Button run, bringScript, fullscreen, rotation, libraryLoader;
    private EditText jsField;
    private JavaScriptManager javaScriptManager;
    private int windowMode = 0;
    private int rotationController = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity_webactivity);
        webView = findViewById(R.id.WebView);
        new WebThread(webView).run();

        bringScript = findViewById(R.id.button);
        bringScript.setVisibility(View.VISIBLE);

        run = findViewById(R.id.run);
        run.setVisibility(View.GONE);

        jsField = findViewById(R.id.jsField);
        jsField.setVisibility(View.GONE);
        jsField.setText("Usage: \n" +
                "method 1: -an <your code here>\n" +
                "method 2: <just the code>\n" +
                "and press Run!\n");
        javaScriptManager = new JavaScriptManager(webView);
        javaScriptManager.setJSField(jsField);

        rotation = findViewById(R.id.rotation);
        rotation.setVisibility(View.GONE);

        fullscreen = findViewById(R.id.fullscreen);
        fullscreen.setVisibility(View.GONE);

        libraryLoader = findViewById(R.id.libraryLoader);
        libraryLoader.setVisibility(View.GONE);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bringScript.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {


                if (jsField.getVisibility() == View.GONE) {
                    jsField.setVisibility(View.VISIBLE);
                    run.setVisibility(View.VISIBLE);
                    fullscreen.setVisibility(View.VISIBLE);
                    rotation.setVisibility(View.VISIBLE);
                    libraryLoader.setVisibility(View.VISIBLE);

                } else {
                    jsField.setVisibility(View.GONE);
                    run.setVisibility(View.GONE);
                    fullscreen.setVisibility(View.GONE);
                    rotation.setVisibility(View.GONE);
                    libraryLoader.setVisibility(View.GONE);
                }
                return true;
            }
        });
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                javaScriptManager.runJSCommand(jsField.getText().toString());
                jsField.setText("");
            }
        });

        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWindowMode(windowMode);
                windowMode = (windowMode + 1) % 3;

            }
        });

        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rotationController) {
                    case 1:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                        rotation.setText("Rotation Reverse");
                        break;
                    case 0:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        rotation.setText("Rotation Portrait");
                        break;
                    case 2:
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                        rotation.setText("Rotation Unspecified");
                        break;

                }
                rotationController = (rotationController + 1) % 2;


            }
        });

        libraryLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // javaScriptManager.loadAll();
                jsField.setText("Not Implemented yet. Wait for the next version ^^; >.>");

            }
        });

        //  textView.setText(message);
    }


    private void setWindowMode(int windowed) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();

        if (windowed == 0) {
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;

        } else if (windowed == 1) {
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        } else if (windowed == 2) {
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        }
        getWindow().setAttributes(attrs);
    }

    public void onBackPressed() {
        if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else super.onBackPressed();
    }
}

class WebThread extends Thread {
    WebView webView;

    WebThread(WebView webView) {
        this.webView = webView;
    }

    public void run() {

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        // webView.setRotation(180);

        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webView.loadUrl("http://www.drrr.com");
        // compute primes larger than minPrime

    }
}
