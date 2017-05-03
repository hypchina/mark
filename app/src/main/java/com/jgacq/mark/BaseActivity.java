package com.jgacq.mark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jgacq.mark.util.ActivityController;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
