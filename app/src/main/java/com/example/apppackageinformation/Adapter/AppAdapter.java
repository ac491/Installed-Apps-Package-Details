package com.example.apppackageinformation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apppackageinformation.Activity.MainActivity;
import com.example.apppackageinformation.Model.AppDetails;
import com.example.apppackageinformation.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {

    private Context mContext;
    private ArrayList<AppDetails> mAppDetailsList;

    public AppAdapter(Context mContext, ArrayList<AppDetails> mAppDetailsList) {
        this.mContext = mContext;
        this.mAppDetailsList = mAppDetailsList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.package_card, viewGroup, false);

        return new AppViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder appViewHolder, final int i) {
        final AppDetails appDetails = mAppDetailsList.get(i);
        appViewHolder.appName.setText(appDetails.getAppName());
        appViewHolder.packageName.setText(appDetails.getPackageName());
        appViewHolder.appIcon.setImageDrawable(appDetails.getAppIcon());
        appViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to main activity
                Log.d("TAG", "Clicked");
                Bitmap bitmap = ((BitmapDrawable)appDetails.getAppIcon()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();
                Intent intent = new Intent(mContext, MainActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("appDetails", (Parcelable) appDetails);
                b.putByteArray("icon", bitmapdata);
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppDetailsList.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        public TextView appName, packageName;
        public ImageView appIcon;
        public CardView cardView;

        public AppViewHolder(View view) {
            super(view);
            appName = (TextView) view.findViewById(R.id.app_name);
            packageName = (TextView) view.findViewById(R.id.app_package);
            appIcon = (ImageView) view.findViewById(R.id.app_icon);
            cardView = (CardView) view.findViewById(R.id.package_card_view);
        }
    }
}
