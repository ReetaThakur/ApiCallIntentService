package com.example.apicallintentservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("reeta");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v("reeta","onHandlerIntent");
        ApiService apiService=Network.getRetrofitInstance().create(ApiService.class);
        String EnterId=intent.getStringExtra("enterId");
        int userid=Integer.parseInt(EnterId);

        apiService.getUser(userid).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel model=response.body();
                String title1 =model.getTitle();
                int userId1=model.getUserId();
                int id1=model.getId();
                String body1=model.getBody();
                Intent intent1=new Intent("com.reeta.service");
                intent1.putExtra("sendTitle",title1);
                sendBroadcast(intent1);
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        showNotificationAndStartForeGround();
        Log.v("reeta","onCreate");
    }

    private void showNotificationAndStartForeGround() {
        createChannel();

        NotificationCompat.Builder notificationBuilder = null;
        notificationBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setContentTitle("Api Call")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Notification notification = null;
        notification = notificationBuilder.build();
        startForeground(120, notification);
    }



    /*
Create noticiation channel if OS version is greater than or eqaul to Oreo
*/
    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Call Notifications");
            Objects.requireNonNull(this.getSystemService(NotificationManager.class)).createNotificationChannel(channel);
        }
    }
}

