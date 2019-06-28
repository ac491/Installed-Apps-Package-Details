package com.example.apppackageinformation.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apppackageinformation.Model.AppDetails;
import com.example.apppackageinformation.R;

public class MainActivity extends AppCompatActivity {

    private ImageView mIconImageView;
    private TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIconImageView = findViewById(R.id.image_icon);
        mAppNameTextView = findViewById(R.id.AppTitle);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AppDisplayActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        AppDetails appDetails = null;
        Drawable appIcon = null;
        if(extras != null){
            appDetails = (AppDetails) extras.getParcelable("appDetails");
            byte[] byteArray = getIntent().getByteArrayExtra("icon");
            if(byteArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                appIcon = new BitmapDrawable(getResources(), bitmap);
            }
        }
        if (appDetails != null) {
            Log.d("TAG", "name" + appDetails.getAppName());
            mIconImageView.setImageDrawable(appIcon);
            mAppNameTextView.setText(appDetails.getAppName());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
