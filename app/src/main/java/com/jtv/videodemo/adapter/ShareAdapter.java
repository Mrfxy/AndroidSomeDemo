package com.jtv.videodemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jtv.videodemo.R;
import com.jtv.videodemo.bean.ShareButtonBean;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

/**
 * Created by fxy on 2018-03-13.
 * 分享
 */

public class ShareAdapter extends RecyclerView.Adapter {
    private static final String TAG = "ShareAdapter";

    private Activity context;
    private List<ShareButtonBean> list;

    public ShareAdapter(Activity context, List<ShareButtonBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_share_btn, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        Holder holder = (Holder) holder1;
        final ShareButtonBean data = list.get(position);
        holder.button.setText(data.text);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share("测试测试分享分享分享", data.type);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 分享事件处理
     */
    private void share(String content, final int type) {
        SHARE_MEDIA platform = null;
        UMShareAPI umShareAPI = UMShareAPI.get(context);
        switch (type) {
            case 1:
                //微信
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case 2:
                //QQ
                platform = SHARE_MEDIA.QQ;
                break;
            case 3:
                //微博
                platform = SHARE_MEDIA.SINA;
                break;
        }
        boolean install = umShareAPI.isInstall(context, platform);
        Log.i(TAG, "share: " + type + "  " + install);
        if (platform == null)
            return;
        //不调用分享面板
        /*new ShareAction(context)
                .setPlatform(platform)//传入平台
                .withText(content)//分享内容
                .setCallback(umShareListener)//回调监听器
                .share();*/

        UMImage umImage = new UMImage(context, R.drawable.umeng_socialize_copyurl);
        UMWeb umWeb = new UMWeb("http://www.baidu.com");
        umWeb.setThumb(umImage);
        umWeb.setTitle("百度两下,你就知道");
       /*  new ShareAction(context)
                .setPlatform(platform)
                .withMedia(umWeb)
                .setCallback(umShareListener)
                .share();*/

     /*   new ShareAction(context)
                .setPlatform(platform)
                .withText("qq分享")
                .setCallback(umShareListener)
                .share();*/


    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i(TAG, "onStart: " + share_media + " 开始");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(context, "成功了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(context, "出错了" + throwable.getMessage(), Toast.LENGTH_LONG).show();
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(context, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    private class Holder extends RecyclerView.ViewHolder {

        Button button;

        public Holder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_share);
        }
    }
}
