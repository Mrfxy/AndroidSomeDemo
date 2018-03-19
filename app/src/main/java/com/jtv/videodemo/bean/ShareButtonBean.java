package com.jtv.videodemo.bean;

/**
 * Created by fxy on 2018-03-13.
 * 分享按钮和按钮类型
 */

public class ShareButtonBean {

    public String text;
    //
    /**
     * <pre>
     *     当前按钮的类型
     *     微信        1
     *     QQ          2
     *     微博        3
     *     ...
     * </pre>
     */
    public int type;

    public ShareButtonBean(String text, int type) {
        this.text = text;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ShareButtonBean{" +
                "text='" + text + '\'' +
                ", type=" + type +
                '}';
    }
}
