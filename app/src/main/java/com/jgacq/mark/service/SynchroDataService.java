package com.jgacq.mark.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;

import com.jgacq.mark.util.SynchroDataAsyncTask;

public class SynchroDataService extends Service {
    private SynchroDataServiceBinder binder = new SynchroDataServiceBinder();
    public SynchroDataService() {
    }

    public class SynchroDataServiceBinder extends Binder{
        //同步数据-到本地
        public void synchroDataToLocal(){
            new SynchroDataAsyncTask().execute(SynchroDataAsyncTask.FLAG_TO_LOCAL);
        }

        //同步数据-到服务器
        public void synchroDataToServer(){
            new SynchroDataAsyncTask().execute(SynchroDataAsyncTask.FLAG_TO_SERVER);
        }
        //同步数据-同步进行
        public void synchroData(){
            new SynchroDataAsyncTask().execute(SynchroDataAsyncTask.FLAG_SERVER_LOCAL);
        }
    }

    @Override
    public SynchroDataServiceBinder onBind(Intent intent) {
        return binder;
    }
}
