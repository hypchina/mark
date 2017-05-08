package com.jgacq.mark.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.jgacq.mark.litepal.Bookmark;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
public class RefreshAdapter extends ArrayAdapter<Bookmark>{
    private List<Bookmark> datas;
    public RefreshAdapter(Context context, int resource, List<Bookmark> objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
