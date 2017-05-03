package com.jgacq.mark;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgacq.mark.adapter.MarkListAdapter;
import com.jgacq.mark.litepal.Bookmark;
import com.jgacq.mark.service.SynchroDataService;
import com.jgacq.mark.util.ActivityController;
import com.jgacq.mark.util.HttpUtil;
import com.jgacq.mark.util.ParseJSON;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final int ALBUM_REQUEST_CODE = 1;
    private static final String AVATAR_IMG_PATH  = "avatar_path";
    private List<Bookmark> marks = new ArrayList<Bookmark>();

    private DrawerLayout mDrawerLayout;

    private ListView markListView;

    private CircleImageView circleImageView;

    private ImageButton downBtn;

    private TextView localCountText;
    private TextView serverCountText;

    private long mExitTime = 0;

    private SynchroDataService.SynchroDataServiceBinder binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (SynchroDataService.SynchroDataServiceBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.drawer_toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        downBtn = (ImageButton)findViewById(R.id.down_data);
        localCountText = (TextView)findViewById(R.id.local_count_text);
        serverCountText = (TextView)findViewById(R.id.server_count_text);

        View headerLayout = ((NavigationView)findViewById(R.id.nav_layout)).getHeaderView(0);
        circleImageView = (CircleImageView)headerLayout.findViewById(R.id.circle_img);
        LinearLayout showBookMark = (LinearLayout)headerLayout.findViewById(R.id.show_book_mark);


        //设置头像
        String circlePath = PreferenceManager.getDefaultSharedPreferences(this).getString(AVATAR_IMG_PATH,null);
        if(circlePath != null){
            File f = new File(circlePath);
            if(f.exists()){
                circleImageView.setImageURI(Uri.fromFile(new File(circlePath)));
            }
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.left_home);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("书签同步助手");
        }


        circleImageView.setOnClickListener(this);
        showBookMark.setOnClickListener(this);
        downBtn.setOnClickListener(this);


        //启动同步数据的服务
        Intent intent = new Intent(this, SynchroDataService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);

        //本机数量
        localCountText.setText("本机："+DataSupport.count(Bookmark.class));

        HttpUtil.sendRequest(HttpUtil.URLCONFIG.markListUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final int count = ParseJSON.getResponse(response.body().string()).getDataList().size();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //网络数量
                        serverCountText.setText("网络："+count);
                    }
                });
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.show_book_mark:{

            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_img:{
                if(ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED
                        )
                {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    //调起相册
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType( "image/*");
                    startActivityForResult(intent, ALBUM_REQUEST_CODE);
                }
                break;
            }
            case R.id.down_data:{
                binder.synchroDataToLocal();
                Log.e("TTTTTTTTTT","点击down_data");
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ALBUM_REQUEST_CODE:{
                    Uri u = data.getData();
                    SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor edit = s.edit();
                    edit.putString(AVATAR_IMG_PATH,u.getPath());
                    edit.apply();
                    circleImageView.setImageURI(u);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出书签同步助手", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityController.finishAll();
            System.exit(0);
        }
    }
}
