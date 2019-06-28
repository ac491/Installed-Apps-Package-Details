package com.example.apppackageinformation.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apppackageinformation.Model.AppDetails;
import com.example.apppackageinformation.R;

import org.xml.sax.XMLReader;

public class MainActivity extends AppCompatActivity {

    private ImageView mIconImageView;
    private TextView mAppNameTextView;
    private TextView mPermissionsList;
    private TextView mRecieversList;
    private TextView mServicesList;
    private TextView mPermissionTextView;
    private TextView mRecieversTextView;
    private TextView mServicesTextView;
    private View mLine1;
    private View mLine2;
    private View mLine3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIconImageView = findViewById(R.id.image_icon);
        mAppNameTextView = findViewById(R.id.AppTitle);
        mPermissionsList = findViewById(R.id.permissionsLv);
        mRecieversList = findViewById(R.id.recieversLv);
        mServicesList = findViewById(R.id.serviceLv);
        mPermissionTextView = findViewById(R.id.permissionsTv);
        mRecieversTextView = findViewById(R.id.recieversTv);
        mServicesTextView = findViewById(R.id.serviceTv);
        mLine1 = findViewById(R.id.lineView1);
        mLine2 = findViewById(R.id.lineView2);
        mLine3 = findViewById(R.id.lineView3);

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
            } else {
                mIconImageView.setVisibility(View.GONE);
            }
        }
        if (appDetails != null) {
            Log.d("TAG", "name" + appDetails.getAppName());
            mIconImageView.setImageDrawable(appIcon);
            mAppNameTextView.setText(appDetails.getAppName());

            if (appDetails.getAppPermissions() != null) {
                //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appDetails.getAppPermissions());
                String message = "<html><body>";
                for (String permission : appDetails.getAppPermissions()) {
                    message += "- " + permission + "<br/><br/>";
                }
                message += "</body></html>";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mPermissionsList.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    mPermissionsList.setText(Html.fromHtml(message));
                }
            } else {
                mPermissionTextView.setVisibility(View.GONE);
                mPermissionsList.setVisibility(View.GONE);
                mLine1.setVisibility(View.GONE);
            }

            if (appDetails.getAppRecievers() != null) {
                String recievers[] = new String[appDetails.getAppRecievers().length];
                int i = 0;
                for (ActivityInfo activityInfo : appDetails.getAppRecievers()) {
                    recievers[i] = activityInfo.name;
                    i++;
                }
                //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recievers);
                String message = "<html><body>";
                for (String reciever : recievers) {
                    message += "- " + reciever + "<br/><br/>";
                }
                message += "</body></html>";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mRecieversList.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    mRecieversList.setText(Html.fromHtml(message));
                }
            } else {
                mRecieversTextView.setVisibility(View.GONE);
                mRecieversList.setVisibility(View.GONE);
                mLine2.setVisibility(View.GONE);
            }

            if (appDetails.getAppServices() != null) {
                String services[] = new String[appDetails.getAppServices().length];
                int i = 0;
                for (ServiceInfo serviceInfo : appDetails.getAppServices()) {
                    services[i] = serviceInfo.name;
                    i++;
                }
                //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, services);
                String message = "<html><body>";
                for (String service : services) {
                    message += "- " + service + "<br/><br/>";
                }
                message += "</body></html>";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mServicesList.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    mServicesList.setText(Html.fromHtml(message));
                }
            } else {
                mServicesTextView.setVisibility(View.GONE);
                mServicesList.setVisibility(View.GONE);
                mLine3.setVisibility(View.GONE);
            }
        } else {
            mRecieversList.setVisibility(View.GONE);
            mRecieversTextView.setVisibility(View.GONE);
            mServicesTextView.setVisibility(View.GONE);
            mServicesList.setVisibility(View.GONE);
            mPermissionsList.setVisibility(View.GONE);
            mPermissionTextView.setVisibility(View.GONE);
            mLine1.setVisibility(View.GONE);
            mLine2.setVisibility(View.GONE);
            mLine3.setVisibility(View.GONE);
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

    public class UlTagHandler implements Html.TagHandler{
        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            if(tag.equals("ul") && !opening) output.append("\n");
            if(tag.equals("li") && opening) output.append("\n\t•");
        }
    }
}
