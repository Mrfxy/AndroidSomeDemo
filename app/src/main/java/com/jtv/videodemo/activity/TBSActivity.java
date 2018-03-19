package com.jtv.videodemo.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jtv.videodemo.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * TBS在线播放视频
 */
public class TBSActivity extends AppCompatActivity {
    private static final String TAG = "TBSActivity";

    private WebView webView;
    private EditText et_url;
    private View btn_load;
    private String videoUrl = "http://v-cc.dushu.io/video/full/b985a4c28cb2a7ae980534933fed0126_09895a/playlist.m3u8";
//    private String videoUrl = "http://www.zzpfm.com:9105/yingde/uploadFiles/book/video/GRhYv45VIcNpKBh5.m3u8";
//    private String videoUrl = "http://www.zzpfm.com:9105/yingde/uploadFiles/book/video/GRhYv45VIcNpKBh5.m3u8";
//    private String videoUrl = "http://10.96.74.186:8085/yingde/uploadFiles/book/video/testtest.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        webView = findViewById(R.id.webView);
        btn_load = findViewById(R.id.btn_load);
        et_url = findViewById(R.id.et_url);

        webView.loadUrl(videoUrl);
        // 这个对宿主没什么影响，建议声明
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        initWebView();

        setBtn();
    }

    private void setBtn(){
        et_url.setText(videoUrl);
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_url.getText().toString().trim();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(TBSActivity.this,"先输入地址！",Toast.LENGTH_SHORT).show();
                }else{
                    webView.loadUrl(s);
                }
            }
        });
    }

    private void initWebView() {

        initWebViewSettings();
        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        if (webView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }

    }

    private void initWebViewSettings() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
