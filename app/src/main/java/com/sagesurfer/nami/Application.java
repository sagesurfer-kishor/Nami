package com.sagesurfer.nami;

import android.content.Context;

import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import com.sagesurfer.nami.directory.DirectoryList;
import com.sagesurfer.nami.observer.AppObserver;

import java.io.File;

/**
 * Created by Kailash on 7/18/2018.
 */

public class Application extends MultiDexApplication {
    private static Application instance;
    private static AppObserver observer;

    @Override
    protected void attachBaseContext(Context newBase) {
        MultiDex.install(newBase);
        super.attachBaseContext(newBase);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        observer = new AppObserver(this);
    }

    public AppObserver getObserver() {
        return observer;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static synchronized Application getInstance() {
        if (instance == null) {
            instance = new Application();
            if (observer == null) {
                observer = new AppObserver(instance);
            }
        }
        return instance;
    }

    public void clearApplicationData() {
        File root = new File(DirectoryList.DIR_MAIN);
        if (root.exists()) {
            String[] children = root.list();
            if (children != null) {
                for (String s : children) {
                    if (!s.equals("lib")) {
                        deleteDir(new File(root, s));
                        Log.i("TAG", "File /Collaborative Care/" + s + " DELETED");
                    }
                }
            } else {
                Log.e("TAG", "LogOut Successfully");
            }

        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}