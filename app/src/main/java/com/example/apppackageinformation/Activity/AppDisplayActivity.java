package com.example.apppackageinformation.Activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
    ProgressDialog mProgressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_display);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setVisibility(View.INVISIBLE);

        new AppsFetchAsync().execute();
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

    public class AppsFetchAsync extends AsyncTask<Void, Integer, ArrayList<AppDetails>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDoalog = new ProgressDialog(AppDisplayActivity.this);
            mProgressDoalog.setMessage("Loading installed apps' details...");
            mProgressDoalog.setTitle("Loading");
            mProgressDoalog.setCancelable(false);
            mProgressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDoalog.show();
        }

        @Override
        protected ArrayList<AppDetails> doInBackground(Void... voids) {
            return getInstalledAppsDetails();
        }

        @Override
        protected void onPostExecute(ArrayList<AppDetails> appDetails) {
            super.onPostExecute(appDetails);
            mProgressDoalog.dismiss();
            final AppAdapter adapter = new AppAdapter(AppDisplayActivity.this, appDetails);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(AppDisplayActivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setAdapter(adapter);
                }
            });
        }
    }

}
