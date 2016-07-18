package com.justbloc.rick.webvew_3;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // substitute your url here
    private final String mViewUrl = "http://www.amazon.com";

    private final String mViewQs = "?d=android";
    private final String mNochaceQs = "&nocache=";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //build the main view
        buildView(savedInstanceState);

    }

    //pass any url to this function to auto-handle certain activities (eg: opening PDF documents or making phone calls)
    protected void handleExternalDeviceActivity(String url){
        //pass this special URL to an external activity (outside of the app)
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    //verify that an internet connection is available
    protected boolean hasInternetConnection(Context context) {
        boolean isConnected=false;
        //if connectivity object is available
        ConnectivityManager con_manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (con_manager.getActiveNetworkInfo() != null){
            //if network is available
            if(con_manager.getActiveNetworkInfo().isAvailable()){
                //if connected
                if(con_manager.getActiveNetworkInfo().isConnected()){
                    //yep... there is connectivity
                    isConnected=true;
                }
            }
        }
        return isConnected;
    }

    //show something if there is no internet connectivity
    protected void noInternetMessage(){
        //build an alert box
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        // Set the message to display
        alertbox.setMessage(R.string.err_connection_summary);
        // Add a neutral button to the alert box and assign a click listener
        alertbox.setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //quit the app
                finish();
            }
        });
        alertbox.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                //quit the app
                finish();
            }
        });
        // show the alert box
        alertbox.show();
    }

    protected void buildView(final Bundle savedInstanceState) {
        //if there is still internet connectivity
       // if (hasInternetConnection(this)) {
            //get the webview object
            mWebView = (WebView) findViewById(R.id.activity_main_webview);

            //Force links and redirects to open in the WebView instead of in a browser
            mWebView.setWebViewClient(new WebViewClient());

            //if web view is NOT already initialized (don't re-init just because orientation changed)
            if (savedInstanceState == null) {
                // Enable Javascript
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                //INIT LOADING INDICATOR OBJECT
                //=============================
                final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
                //KEY WEBVIEW EVENTS (OVERRIDES)
                //==============================
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //hide the loading indicator when the webview is fully loaded
                        if(pd.isShowing()&&pd!=null){
                            pd.dismiss();
                            //clear the webview cache
                            super.onPageFinished(view, url);
                            view.clearCache(true);
                        }
                    }
                });
                //LOAD THE APP PAGE INTO THE WEB VIEW
                //===================================
                Calendar now = Calendar.getInstance();
                Date date = new Date();
                now.setTime(date);
                String nocache = now.get(Calendar.YEAR)  + "_" + now.get(Calendar.MONTH) + "_" + now.get(Calendar.DAY_OF_MONTH) + "_"
                        + now.get(Calendar.HOUR_OF_DAY) + "_" + now.get(Calendar.MINUTE) + "_" + now.get(Calendar.SECOND);
                mWebView.loadUrl(mViewUrl + mViewQs + mNochaceQs + nocache);

            }else{
                mWebView.restoreState(savedInstanceState);
            }
       // }else{
       //     noInternetMessage();
       // }
    }

    @Override public void onBackPressed()
    {
      if(mWebView.canGoBack()){
          mWebView.goBack();
      }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        //save the state of the WebView
        mWebView.saveState(outState);
    }

}
