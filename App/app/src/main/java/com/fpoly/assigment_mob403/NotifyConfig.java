package com.fpoly.assigment_mob403;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class NotifyConfig extends Application {
    public static final String CHANEL_ID = "SocketASM";

    @Override
    public void onCreate() {
        super.onCreate();
        config();
    }

    void config() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "comic";

            String mota = "postcomic";
            int do_uu_tien = NotificationManager.IMPORTANCE_DEFAULT;

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();


            NotificationChannel channel = new NotificationChannel(CHANEL_ID, name, do_uu_tien);

            channel.setDescription(mota);

            channel.setSound(uri, audioAttributes);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
