package com.jgacq.mark.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/4.
 */
public class RequestJsonObj {

    @SerializedName("siteurl")
    private String siteUrl;
    @SerializedName("sitename")
    private String siteName;
    @SerializedName("siteid")
    private String siteId;
    @SerializedName("siteindex")
    private String siteIndex;

    @SerializedName("sitepid")
    private String sitePid;

    @SerializedName("sitetags")
    private String siteTags;

    @SerializedName("sitetime")
    private String siteTime;

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setSiteIndex(String siteIndex) {
        this.siteIndex = siteIndex;
    }

    public void setSitePid(String sitePid) {
        this.sitePid = sitePid;
    }

    public void setSiteTags(String siteTags) {
        this.siteTags = siteTags;
    }

    public void setSiteTime(String siteTime) {
        this.siteTime = siteTime;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public String getSiteIndex() {
        return siteIndex;
    }

    public String getSitePid() {
        return sitePid;
    }

    public String getSiteTags() {
        return siteTags;
    }

    public String getSiteTime() {
        return siteTime;
    }
}
