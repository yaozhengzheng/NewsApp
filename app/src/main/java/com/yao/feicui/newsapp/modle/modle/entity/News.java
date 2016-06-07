package com.yao.feicui.newsapp.modle.modle.entity;

import java.io.Serializable;

/**
 * Created by 16245 on 2016/06/07.
 */
public class News implements Serializable {
    private static final long serialVersionUID = 1L;
    private int type;//类型标识  1:列表新闻 2：大图新闻
    private int nid;//新闻ID
    private String stamp;//时间戳
    private String icon;//图标
    private String title;//新闻标题
    private String summary;//新闻摘要
    private String link;//新闻链接

    public int getType() {
        return type;
    }

    public int getNid() {
        return nid;
    }

    public String getStamp() {
        return stamp;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getLink() {
        return link;
    }

    public News(int type, int nid, String stamp, String icon, String title,
                String summary, String link){
        super();
        this.type = type;
        this.nid = nid;
        this.stamp = stamp;
        this.icon = icon;
        this.title = title;
        this.summary = summary;
        this.link = link;
    }
}
