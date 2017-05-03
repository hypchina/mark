package com.jgacq.mark;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jgacq.mark.adapter.MarkListAdapter;
import com.jgacq.mark.litepal.Bookmark;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BookMarkActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    private List<Bookmark> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        fab = (FloatingActionButton)findViewById(R.id.float_btn);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        data = DataSupport.findAll(Bookmark.class);
        MarkListAdapter adapter = new MarkListAdapter(data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_btn:{
                //recyclerView.scrollToPosition(0);
                recyclerView.smoothScrollToPosition(0);
                break;
            }
        }
    }
}
