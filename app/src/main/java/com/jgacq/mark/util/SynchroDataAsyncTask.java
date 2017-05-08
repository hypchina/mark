package com.jgacq.mark.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jgacq.mark.gson.Response;
import com.jgacq.mark.litepal.Bookmark;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */
public class SynchroDataAsyncTask extends AsyncTask<Integer,Integer,Boolean> {
    final public static int FLAG_TO_SERVER = 1;//同步数据到服务器
    final public static int FLAG_TO_LOCAL = 2;//同步数据到本地
    final public static int FLAG_SERVER_LOCAL = 3;//同步到服务器和本地
    final public static int FLAG_ADD_MARK_TO_SERVER  = 4;//添加数据到服务器

    private OnSynchroDataListener listener;

    public interface OnSynchroDataListener{
        public void onStartSynchro();
        public void onFinishSynchro(boolean result);
    }

    public SynchroDataAsyncTask(){
    }

    public SynchroDataAsyncTask(OnSynchroDataListener listener){
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(listener != null){
            listener.onStartSynchro();
        }
    }

    @Override
    protected Boolean doInBackground(Integer... params) {


        switch (params[0].intValue()){
            case FLAG_TO_SERVER:{
                //获取本地数据
                List<Bookmark> localList = DataSupport.findAll(Bookmark.class);
                //从服务器获取数据
                String json = HttpUtil.sendRequest(HttpUtil.URLCONFIG.markListUrl);
                Response response = ParseJSON.getResponse(json);
                List<Bookmark> serverList =  response.getDataList();

                synchroDataToServer(localList,serverList);
                break;
            }
            case FLAG_TO_LOCAL:{
                //获取本地数据
                List<Bookmark> localList = DataSupport.findAll(Bookmark.class);
                //从服务器获取数据
                String json = HttpUtil.sendRequest(HttpUtil.URLCONFIG.markListUrl);
                Response response = ParseJSON.getResponse(json);
                List<Bookmark> serverList =  response.getDataList();

                synchroDataToLocal(localList,serverList);
                break;
            }
            case FLAG_SERVER_LOCAL:{
                //获取本地数据
                List<Bookmark> localList = DataSupport.findAll(Bookmark.class);
                //从服务器获取数据
                String json = HttpUtil.sendRequest(HttpUtil.URLCONFIG.markListUrl);
                Response response = ParseJSON.getResponse(json);
                List<Bookmark> serverList =  response.getDataList();

                List<Bookmark> copyList = new LinkedList<>();
                copyList.addAll(serverList);
                //同步到本地
                synchroDataToLocal(localList,serverList);
                //同步本地到服务器
                synchroDataToServer(localList,copyList);
                break;
            }
            case FLAG_ADD_MARK_TO_SERVER:{
                //md5 url
                break;
            }
        }
        publishProgress(params[0].intValue());
        return true;
    }

    /**
     * 同步数据到本地
     */
    private void synchroDataToLocal(List<Bookmark> localList,List<Bookmark> serverList){
        //服务器有的 但是本地没有的数据,保存到数据库
        serverList.removeAll(localList);
        if(serverList.size()>0){
            DataSupport.saveAll(serverList);
        }
    }

    /**
     * 同步数据到服务器
     */
    private void synchroDataToServer(List<Bookmark> localList,List<Bookmark> serverList){
        //服务器没有的 但是本地有的数据
        localList.removeAll(serverList);
        //调用接口
    }

    private void addBookMarkToServer(){
//        siteUrl:https://www.postgresql.org/
//        siteName:PostgreSQL: The world's most advanced open source database
//        siteIndex:0
//        sitePid:1
//        siteId:9898fa08ab53ad93fe00c36437b6a72b
//        siteTime:NaN
//        action:insert
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(listener != null){
            listener.onFinishSynchro(aBoolean);
        }
        Log.e("TTTTTTTTT","同步数据完成");
    }
}
