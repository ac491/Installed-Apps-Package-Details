package com.example.apppackageinformation.Model;

import android.content.pm.ActivityInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class AppDetails implements Parcelable {

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

    protected AppDetails(Parcel in) {
        appName = in.readString();
        packageName = in.readString();
        appRecievers = in.createTypedArray(ActivityInfo.CREATOR);
        appServices = in.createTypedArray(ServiceInfo.CREATOR);
        appPermissions = in.createStringArray();
    }

    public static final Creator<AppDetails> CREATOR = new Creator<AppDetails>() {
        @Override
        public AppDetails createFromParcel(Parcel in) {
            return new AppDetails(in);
        }

        @Override
        public AppDetails[] newArray(int size) {
            return new AppDetails[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appName);
        parcel.writeString(packageName);
        parcel.writeTypedArray(appRecievers, i);
        parcel.writeTypedArray(appServices, i);
        parcel.writeStringArray(appPermissions);
    }
}
