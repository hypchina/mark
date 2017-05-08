package com.jgacq.mark.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jgacq.mark.R;
import com.jgacq.mark.adapter.RefreshAdapter;
import com.jgacq.mark.litepal.Bookmark;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BookMarkTitleListActivity extends AppCompatActivity {
    private PullToRefreshListView mPullRefreshListView;

    //普通的listview对象
    private ListView actualListView;
    //添加一个链表数组，来存放string数组，这样就可以动态增加string数组中的内容了
    private LinkedList<String> mListItems;
    //给listview添加一个普通的适配器
    private ArrayAdapter<String> mAdapter;

    //保存页码
    private int currentPage =1;
    //每次刷新添加的数量
    private int pageCount = 10;

    private List<Bookmark> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark_title_list);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        actualListView = mPullRefreshListView.getRefreshableView();
        mListItems = new LinkedList<String>();

        //获取数据-先测试
        datalist = DataSupport.findAll(Bookmark.class);

        //把string数组中的string添加到链表中
        for(int i=0;i<pageCount*currentPage;i++){
            mListItems.add(datalist.get(i).getSiteName());
        }

        //mAdapter = new RefreshAdapter(this,android.R.layout.simple_list_item_1,)
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mListItems);
        actualListView.setAdapter(mAdapter);

        //初始化配置
        initRefreshListViewConfig();
    }

    private void initRefreshListViewConfig(){
        //设置拉动监听器
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //生成一个时间字符串
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        for(int i=pageCount*currentPage;i<pageCount*(currentPage+1);i++){
                            mListItems.add(i+1+"、 "+datalist.get(i).getSiteName());
                        }

                        BookMarkTitleListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentPage++;
                                // 通知数据改变了
                                mAdapter.notifyDataSetChanged();
                                // 加载完成后停止刷新
                                mPullRefreshListView.onRefreshComplete();
                            }
                        });

                    }
                }).start();
            }
        });


        // 添加滑动到底部的监听器
//        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//
//            @Override
//            public void onLastItemVisible() {
//                Toast.makeText(getApplication(), "已经到底了", Toast.LENGTH_SHORT).show();
//            }
//        });

        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
        //在刷新时允许继续滑动
        mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
        //mPullRefreshListView.getMode();//得到模式
        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }
}
