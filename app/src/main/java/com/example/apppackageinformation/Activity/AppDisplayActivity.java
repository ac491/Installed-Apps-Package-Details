package com.example.apppackageinformation.Activity;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.apppackageinformation.Adapter.AppAdapter;
import com.example.apppackageinformation.Model.AppDetails;
import com.example.apppackageinformation.R;

import java.util.ArrayList;
import java.util.List;

public class AppDisplayActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgress;
    private ArrayList<AppDetails> installedAppDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_display);

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgress = findViewById(R.id.progress);
        mRecyclerView.setVisibility(View.INVISIBLE);
        installedAppDetails = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                installedAppDetails = getInstalledAppsDetails();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final AppAdapter adapter = new AppAdapter(this, installedAppDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

    private ArrayList<AppDetails> getInstalledAppsDetails(){
        ArrayList<AppDetails> installedAppDetails = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo applicationInfo: packages){
            String appName = (String) applicationInfo.loadLabel(packageManager);
            String packageName = applicationInfo.packageName;

            //get app icon
            Drawable appIcon = null;
            try {
                appIcon = packageManager.getApplicationIcon(applicationInfo.packageName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String[] requestedPermissions = null;
            //get permissions
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                requestedPermissions = packageInfo.requestedPermissions;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            //app receivers
            ActivityInfo[] appReceivers = null;
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_RECEIVERS);
                appReceivers = packageInfo.receivers;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            //app services
            ServiceInfo[] appServices = null;
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, PackageManager.GET_SERVICES);
                appServices = packageInfo.services;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            AppDetails appDetails = new AppDetails(appName, packageName, appIcon, appReceivers, appServices, requestedPermissions);
            installedAppDetails.add(appDetails);
        }
        return installedAppDetails;
    }


}
