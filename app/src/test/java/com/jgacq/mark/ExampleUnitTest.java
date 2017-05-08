package com.jgacq.mark;

import android.test.AndroidTestCase;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jgacq.mark.gson.RequestJsonObj;
import com.jgacq.mark.gson.Response;
import com.jgacq.mark.litepal.Bookmark;
import com.jgacq.mark.util.HttpUtil;
import com.jgacq.mark.util.ParseJSON;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.litepal.crud.DataSupport;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(PowerMockRunner.class)
public class ExampleUnitTest {



    @Test
    @PrepareForTest({Base64.class,System.class,Log.class,BaseActivity.class})
    public void addition_isCorrect() throws Exception {
        File file = new File("E:\\androidworkspace\\test.txt");

        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuilder json = new StringBuilder();
        while((line = br.readLine()) != null){
            json.append(line);
        }
        isr.close();
        br.close();
        Gson gson = new Gson();
        JsonElement j = new JsonParser().parse(json.toString());
        JsonObject jo = j.getAsJsonObject();
        List<RequestJsonObj> list = new ArrayList<>();
        for(int i =0;i<jo.size();i++){
            JsonElement jsonElement = jo.get(i+"");
            RequestJsonObj ro = gson.fromJson(jsonElement, RequestJsonObj.class);
            list.add(ro);
        }
        String reqString = gson.toJson(list);
        byte[] bytes = reqString.getBytes("utf-8");
        PowerMockito.mockStatic(System.class);
        PowerMockito.mockStatic(BaseActivity.class);
        PowerMockito.mockStatic(Base64.class);

//        PowerMockito.when(Base64.encode(bytes,Base64.DEFAULT)).thenCallRealMethod();
//        PowerMockito.verifyStatic(atLeastOnce());
//        byte[] bb = Base64.encode(bytes,Base64.DEFAULT);
//        String ss = Base64.encodeToString(bytes,Base64.DEFAULT);
//        String baseString = new String(android.util.Base64.encode(bytes, Base64.DEFAULT),"utf-8");
//        String s = HttpUtil.sendBase64Request(HttpUtil.URLCONFIG.batchAddMarkUrl,"senddata",baseString);
//        System.out.println(json.toString());
//        System.out.println(android.util.Base64.encodeToString(bytes,android.util.Base64.DEFAULT));
//        Assert.fail();
        PowerMockito.when(BaseActivity.sssss(123)).thenCallRealMethod();
        System.out.println(BaseActivity.sssss(123));
//        System.out.println(bb.length);
        System.out.println(reqString);
        System.out.println(Base64.CRLF);

        System.out.println(bytes.length);

    }

    public void test(){
        List<Bookmark> arr1 = new ArrayList<>();
        List<Bookmark> arr2 = new ArrayList<>();
        Bookmark b1 = new Bookmark(
                "http://pecl.php.net/package/yaf",
                "yaf 安装包 pecl",
                "70054821739fed2a7aedd9721fae91b0",
                "1"
        );
        Bookmark b2 = new Bookmark(
                "http://112.74.38.206:18983/solr/#/picture/query",
                "Solr Admin,搜索",
                "84aa8f6ab8b7bf72ebb235e0306011d1",
                "2"
        );
        Bookmark b3 = new Bookmark(
                "http://www.laruence.com/php-internal",
                "鸟哥-风雪之隅",
                "ce13e470b3b2ce96e29549693202f2d1",
                "3"
        );
        Bookmark b4 = new Bookmark(
                "https://laravel-china.org/topics/2723",
                "打造完美的 Ubuntu16.04 开发环境【持续更新】",
                "2942bc09095f878533288133267bf292",
                "4"
        );


        Bookmark b5 = new Bookmark(
                "http://112.74.38.206:18983/solr/#/picture/query",
                "Solr Admin,搜索",
                "70054821739fed2a7aedd9721fae91b0",
                "5"
        );
        Bookmark b6 = new Bookmark(
                "http://www.laruence.com/php-internal",
                "鸟哥-风雪之隅",
                "ce13e470b3b2ce96e29549693202f2d1",
                "6"
        );

        arr1.add(b1);
        //arr1.add(b2);
        arr1.add(b3);
        //arr1.add(b4);
        System.out.println(arr1.size());
        arr2.add(b5);
        arr2.add(b6);
        System.out.println(arr2.size());
        arr1.removeAll(arr2);
        System.out.println(arr1.size());
        for (Bookmark b : arr1){
            System.out.println(b);
        }
        //String siteurl,String sitename,String siteid,String siteindex
    }
}