package cn.example.tvmouseview;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TvWebView webView;
    private MouseView mouseView;

    private String expmpleUrl = "https://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (TvWebView) findViewById(R.id.web_view);
        initSetting();
        webView.loadUrl(expmpleUrl);

        mouseView = (MouseView) findViewById(R.id.mouse_view);

    }

    private void initSetting() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String ua = settings.getUserAgentString();
        //see http://www.fynas.com/ua
        //UC手机版 Mozilla/5.0 (Linux; Android 6.0.1; NL-5101 Build/MXC89L; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Safari/537.36
        //UC平板 (iPad; U; CPU OS 5_1 like Mac OS X) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10
        //UC电脑 (Windows;U;Windows NT 5.2; en-US) AppleWebKit/534.31 (KHTML, like Gecko) Chrome/17.0.558.0 Safari/534.31
        //实测发现pad版广告少一点
//        settings.setUserAgentString("Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
        settings.setUserAgentString("(iPad; U; CPU OS 5_1 like Mac OS X) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10");
        settings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading:" + url);
                webView.loadUrl(url);
//                return super.shouldOverrideUrlLoading(view, url);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading request:" + request.getUrl().toString());
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        mouseView.moveMouse(webView, event);
        return super.dispatchKeyEvent(event);
    }
}
