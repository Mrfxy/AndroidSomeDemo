package com.jtv.videodemo.wxapi;

import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * Created by fxy on 2018-03-12.
 * 微信回调
 */

public class WXEntryActivity extends WXCallbackActivity{
    private static final String TAG = "WXEntryActivity";
    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onResp: " + resp);
        super.onResp(resp);
    }
}
