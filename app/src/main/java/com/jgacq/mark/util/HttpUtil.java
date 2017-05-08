package com.jgacq.mark.util;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/29.
 */
public class HttpUtil {
    public static class URLCONFIG{
        public final static String markListUrl = "http://jgacq.com/index.php?m=blog&c=mark&a=readlist";
        public final static String addMarkUrl = "http://jgacq.com/api/Mark/sendMark";
        public final static String batchAddMarkUrl = "http://jgacq.com/api/Mark/syncMark";
    }

    public static void sendRequest(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static String sendRequest(String url){
        OkHttpClient client = new OkHttpClient();
        Response response = null;
        Request request = new Request.Builder().url(url).build();
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送请求
     * @param url
     * @param base64Json
     * @return
     */
    public static String sendBase64Request(String url,String key,String base64Json){
        OkHttpClient client = new OkHttpClient();
        //MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        //key => "senddata"
        RequestBody requestBody = new FormBody.Builder().add(key,base64Json).build();
        Request request = new Request.Builder().post(requestBody).url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
