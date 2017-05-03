package com.jgacq.mark.gson;

import com.google.gson.annotations.SerializedName;
import com.jgacq.mark.litepal.Bookmark;

import java.util.List;

/**
 * Created by Administrator on 2017/4/29.
 */
public class Response {
    private int code;
    private String msg;
    @SerializedName("data")
    private List<Bookmark> markList;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDataList(List<Bookmark> dataList) {
        this.markList = dataList;
    }

    public int getCode() {

        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<Bookmark> getDataList() {
        return markList;
    }
}
