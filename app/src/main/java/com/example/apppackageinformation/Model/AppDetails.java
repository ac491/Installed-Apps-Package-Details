package com.example.apppackageinformation.Model;

import android.content.pm.ActivityInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;

public class AppDetails {

    private String appName;
    private String packageName;
    private Drawable appIcon;
    private ActivityInfo[] appRecievers;
    private ServiceInfo[] appServices;
    private String[] appPermissions;

    public AppDetails(String appName, String packageName, Drawable appIcon, ActivityInfo[] appRecievers, ServiceInfo[] appServices, String[] appPermissions) {
        this.appName = appName;
        this.packageName = packageName;
        this.appIcon = appIcon;
        this.appRecievers = appRecievers;
        this.appServices = appServices;
        this.appPermissions = appPermissions;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public ActivityInfo[] getAppRecievers() {
        return appRecievers;
    }

    public ServiceInfo[] getAppServices() {
        return appServices;
    }

    public String[] getAppPermissions() {
        return appPermissions;
    }
}
