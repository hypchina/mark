package com.jgacq.mark;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Base64;
import android.util.Log;

import com.jgacq.mark.util.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    public ApplicationTest() throws IOException {
        super(Application.class);
        Context context = getContext();
    }
}