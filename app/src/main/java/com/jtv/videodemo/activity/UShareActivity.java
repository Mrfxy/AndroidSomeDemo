package com.jtv.videodemo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jtv.videodemo.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

/**
 * 友盟分享功能
 */
public class UShareActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UShareActivity";

    private RecyclerView rv_share;

    private String imgUrl = "http://c.hiphotos.baidu.com/image/h%3D300/sign=5d662b6f291f95cab9f594b6f9177fc5/72f082025aafa40f8197e0cca764034f78f01949.jpg";

    private String musicUrl = "http://m10.music.126.net/20180316164404/4b293414603e7b591a18ce6de60c9d67/ymusic/d3ad/a40a/1122/d310dc1216f58aeee265e69a788c4ab0.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ushare);
        View btn_img = findViewById(R.id.btn_img);
        View btn_url = findViewById(R.id.btn_url);
        View btn_music = findViewById(R.id.btn_music);

        btn_img.setOnClickListener(this);
        btn_url.setOnClickListener(this);
        btn_music.setOnClickListener(this);

        requestUMPermisson();
    }

    /**
     * 请求友盟分享需要的权限
     */
    private void requestUMPermisson() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //请求权限回调

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    /**
     * 分享回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i(TAG, "onStart: " + share_media + " 开始");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(UShareActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(UShareActivity.this, "出错了" + throwable.getMessage(), Toast.LENGTH_LONG).show();
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(UShareActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v) {
        UMImage umImage = new UMImage(this, imgUrl);
        UMImage thumb = new UMImage(this, R.drawable.umeng_socialize_douban);//缩略图
        umImage.setThumb(thumb);

        UMWeb web = new UMWeb("http://www.baidu.com");
        web.setTitle("百度");//标题
        UMImage thumb2 = new UMImage(this, R.drawable.umeng_socialize_copyurl);
        web.setThumb(thumb2);  //缩略图
        web.setDescription("百度一下，你就知道");//描述

        switch (v.getId()) {
            case R.id.btn_img:
                new ShareAction(this).withMedia(umImage).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener).open();
                break;
            case R.id.btn_url:
                new ShareAction(this).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener).open();
                break;

            case R.id.btn_music:
                UMusic music = new UMusic(musicUrl);//音乐的播放链接
                music.setTitle("音乐标题");//音乐的标题
                music.setThumb(new UMImage(this, R.drawable.umeng_socialize_share_music));//音乐的缩略图
                music.setDescription("描述");//音乐的描述
                music.setmTargetUrl("");//音乐的跳转链接
                new ShareAction(this).withMedia(music).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener).open();
                break;

        }
    }

}
