package com.example.chronus.AlarmNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //ToastUtil.showShort(context, "从服务启动广播：at " + DateTimeUtil.getCurrentDateTimeString());
        String title = intent.getStringExtra("title");
        ToastUtil.showNotifiction(context,title,"reminder");

        //Log.d("Alarm", "从服务启动广播：at " + DateTimeUtil.getCurrentDateTimeString());
    }

}