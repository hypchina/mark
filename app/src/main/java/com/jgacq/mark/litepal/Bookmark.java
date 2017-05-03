package com.jgacq.mark.litepal;

import com.google.gson.annotations.SerializedName;
import com.jgacq.mark.util.Md5;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/29.
 */
public class Bookmark extends DataSupport{
    @SerializedName("siteurl")
    private String siteUrl;
    @SerializedName("sitename")
    private String siteName;
    @SerializedName("siteid")
    private String siteId;
    @SerializedName("siteindex")
    private String siteIndex;


    public Bookmark(){}
    public Bookmark(String siteurl,String sitename,String siteid,String siteindex){
        siteUrl = siteurl;
        siteName = sitename;
        siteId = siteid;
        siteIndex = siteindex;
    }

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

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Bookmark)){
            return false;
        }
        String oStr = ((Bookmark)obj).getSiteId();
        String mStr = this.getSiteId();
        return oStr.equals(mStr);
    }

    @Override
    public int hashCode() {
        return Md5.hashKeyForDisk(getSiteName()+getSiteIndex()+getSiteUrl()+getSiteId()).hashCode();
    }

    @Override
    public String toString() {
        return " SiteName: "+getSiteName()+
                " SiteIndex: "+getSiteIndex()+
                " SiteId: "+getSiteId();
    }
}
