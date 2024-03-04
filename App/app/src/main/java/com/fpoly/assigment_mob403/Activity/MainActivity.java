package com.fpoly.assigment_mob403.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Notification;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fpoly.assigment_mob403.Adapter.AdapterFagment;
import com.fpoly.assigment_mob403.ContainAPI;
import com.fpoly.assigment_mob403.DTO.User;
import com.fpoly.assigment_mob403.Fragment.ReadStoryFragment;
import com.fpoly.assigment_mob403.Fragment.UserFragment;
import com.fpoly.assigment_mob403.GeneralFunc;


import com.fpoly.assigment_mob403.NotifyConfig;
import com.fpoly.assigment_mob403.R;
import com.fpoly.assigment_mob403.ValuesSave;
import com.fpoly.assigment_mob403.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private String TAG = "TAGHELLO";
    private ActivityMainBinding binding;
    private AdapterFagment adapter_fragment;
    private io.socket.client.Socket socket;
    {
        try {
            socket = IO.socket("http://10.0.2.2:9000");


        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Init();

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.d("Socket.IO", "Trạng thái kết nối Socket: " + socket.connected());
            }
        });

        socket.on("new-todo", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Socket.IO", "Received new-todo event");


                        JSONObject jsonObject = (JSONObject) args[0];
                        String task = jsonObject.optString("name", "");
                        String content = jsonObject.optString("describe", "");
                        String bg = jsonObject.optString("background","");
                        Log.d("zzzz","log"+task+content+bg);


                        postNotify(task, content,bg);
                    }
                });
            }
        });
        socket.connect();
        Log.d("Socket.IO", "Socket connection status: " + socket.connected());
    }


    private void Init() {
        AddViewPager();
        AddTabLayout();
    }

    private void AddTabLayout() {
        new TabLayoutMediator(binding.actiMainTab, binding.actiMainViewPg2, (tab, position) -> {
            if(position == 0){
                tab.setIcon(R.drawable.auto_stories);
            }else
                tab.setIcon(R.drawable.account_circle);
        }).attach();
    }

    private void AddViewPager() {
        adapter_fragment = new AdapterFagment(this,
                new Fragment[]{ReadStoryFragment.newInstance(), UserFragment.newInstance()});
        binding.actiMainViewPg2.setAdapter(adapter_fragment);
    }

    void postNotify(String title, String content, String bgUrl) {

        Picasso.get().load(bgUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int icon_resource = R.drawable.spm;

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    icon_resource = R.drawable.spm;
                }
                Notification customNotification = new NotificationCompat.Builder(MainActivity.this, NotifyConfig.CHANEL_ID)
                        .setSmallIcon((icon_resource))
                        .setContentTitle(title)
                        .setContentText(content)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                        .setAutoCancel(true)
                        .build();


                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 999999);
                    Toast.makeText(MainActivity.this, "Chưa cấp quyền", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id_notify = (int) new Date().getTime();
                notificationManagerCompat.notify(id_notify, customNotification);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                Log.e(TAG, "Failed to load image from URL: " + bgUrl, e);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

}