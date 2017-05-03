package com.jgacq.mark;

import com.jgacq.mark.gson.Response;
import com.jgacq.mark.litepal.Bookmark;
import com.jgacq.mark.util.HttpUtil;
import com.jgacq.mark.util.ParseJSON;

import org.junit.Test;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        //List<Bookmark> localList = DataSupport.findAll(Bookmark.class);
        //从服务器获取数据
        String json = HttpUtil.sendRequest(HttpUtil.URLCONFIG.markListUrl);
        Response response = ParseJSON.getResponse(json);
        List<Bookmark> serverList =  response.getDataList();



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

        List<Bookmark> localList = new ArrayList<>();
        localList.add(b1);
        localList.add(b2);
        localList.add(b3);
        localList.add(b4);
        //serverList.removeAll(localList);

        //System.out.println(" type :"+ (serverList instanceof ArrayList));
        for (Bookmark a : serverList){
            for (Bookmark b : localList){
                if(a.equals(b)){
                    System.out.println("a : "+a);
                    System.out.println("b : "+b);
                }else{
                    //System.out.println("result: "+a.equals(b));
                }
            }
        }
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