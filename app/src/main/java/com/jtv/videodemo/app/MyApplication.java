package com.jtv.videodemo.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


/**
 * Created by fxy on 2017/12/18.
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;

    {
        PlatformConfig.setWeixin("wx22533a3c4cef80e0", "34faa33b8992f2952d7848ede75022dc");
        PlatformConfig.setQQZone("1106698043", "LnmLf96bdCEJO4UE");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initTBS();
        initUMemng();


    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }

    /**
     * 初始化TBS
     */
    private void initTBS() {
        com.tencent.smtt.sdk.QbSdk.PreInitCallback cb = new com.tencent.smtt.sdk.QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + b);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };

        //TBS  x5内核初始化接口
        com.tencent.smtt.sdk.QbSdk.initX5Environment(getApplicationContext(), cb);

    }

    /**
     * 初始化友盟
     */
    private void initUMemng() {
        UMShareAPI.get(this);

//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        //友盟分享  不需要推送的话secret可以传null
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "c936269d90d6af24c5a3227a80645c62");
        //友盟log
        UMConfigure.setLogEnabled(true);


    }

}
