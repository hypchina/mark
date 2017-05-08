package com.jgacq.mark;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jgacq.mark.adapter.MarkListAdapter;
import com.jgacq.mark.litepal.Bookmark;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookMarkActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView recyclerView;

    private MarkListAdapter adapter;

    private FloatingActionButton fab;

    private EditText searchEdit;
    private Button searchBtn;

    private List<Bookmark> data;

    List<Bookmark> lastSearchData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        searchEdit = (EditText)((ViewGroup)findViewById(R.id.search_input)).getChildAt(0);
        searchBtn = (Button)findViewById(R.id.search_btn);
        fab = (FloatingActionButton)findViewById(R.id.float_btn);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);


        data = DataSupport.findAll(Bookmark.class);
        lastSearchData = new LinkedList<Bookmark>();
        lastSearchData.addAll(data);

        adapter = new MarkListAdapter(data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_btn:{
                //recyclerView.scrollToPosition(0);
                recyclerView.smoothScrollToPosition(0);
                break;
            }
            case R.id.search_btn:{
                String kw = searchEdit.getText().toString();
                List<Bookmark> newData = null;
                if(kw == null || kw == ""){

                    newData = DataSupport.findAll(Bookmark.class);
                    //如果 和上一次的查询结果相同 就什么都不做
                    if(equalList(newData,lastSearchData)){
                        return;
                    }
                    data.clear();//清空数据源
                    data.addAll(newData);
                }else{
                    newData = DataSupport.where("siteName like ?","%"+kw+"%").find(Bookmark.class);
                    //如果 和上一次的查询结果相同 就什么都不做
                    if(equalList(newData,lastSearchData)){
                        return;
                    }
                    //移除多余的itemView
                    adapter.notifyItemRangeRemoved(newData.size(),adapter.getItemCount());
                    data.clear();//清空数据源
                    data.addAll(newData);//添加新数据
                }
                //记录查询出来的结果
                lastSearchData = newData;
                //adapter.notifyItemRangeChanged(0,data.size());//item的位置发生变化的时候调用
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private boolean equalList(List<Bookmark> list01,List<Bookmark> list02 ){
        if(list01 == null || list02 == null){
            return false;
        }
        if(list01 == list02){
            return true;
        }
        if(list01.size() != list02.size()){
            return false;
        }
        if(list01.containsAll(list02)){
            return true;
        }else{
            return false;
        }
    }
}
