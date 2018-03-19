package com.jtv.videodemo.utils;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

/**
 * Created by fxy on 2018-03-16.
 */

public class ShareUtils {

    private static void share(Activity activity, SHARE_MEDIA platform, BaseMediaObject umMedia, UMShareListener umShareListener){
        ShareAction shareAction = new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(umShareListener);
        if (umMedia instanceof UMImage){
            shareAction.withText("分享图片").withMedia(((UMImage) umMedia));
        } else if (umMedia instanceof UMMin) {
            shareAction.withMedia(((UMMin) umMedia));
        } else if (umMedia instanceof UMWeb) {
            shareAction.withMedia(((UMWeb) umMedia));
        } else if (umMedia instanceof UMusic) {
            shareAction.withMedia(((UMusic) umMedia));
        } else if (umMedia instanceof UMVideo) {
            shareAction.withMedia(((UMVideo) umMedia));
        }
        shareAction.share();
    }

    /**
     * 分享图片
     */
    public static void shareImg(Activity activity, SHARE_MEDIA platform, UMImage umImage, UMShareListener umShareListener) {
      share(activity,platform,umImage,umShareListener);
    }

    /**
     * 分享链接
     */
    public static void shareWeb(Activity activity, SHARE_MEDIA platform, UMWeb umweb, UMShareListener umShareListener) {
        share(activity,platform,umweb,umShareListener);
    }

    /**
     * 分享视频
     */
    public static void shareVideo(Activity activity, SHARE_MEDIA platform, UMVideo umVideo, UMShareListener umShareListener) {
        share(activity,platform,umVideo,umShareListener);
    }

    /**
     * 分享音乐
     */
    public static void shareMusic(Activity activity, SHARE_MEDIA platform, UMusic uMusic, UMShareListener umShareListener) {
        share(activity,platform,uMusic,umShareListener);
    }


}
