package com.jgacq.mark.util;

import com.google.gson.Gson;
import com.jgacq.mark.gson.Response;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */
public class ParseJSON {

    public static Response getResponse(String json){
        Gson gson = new Gson();
        Response response = gson.fromJson(json,Response.class);
        return response;
    }
}
