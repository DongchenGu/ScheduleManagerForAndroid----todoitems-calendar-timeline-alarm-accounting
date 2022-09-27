package com.example.chronus;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.chronus.Setting.SettingFragment;

public class TransitionActivity extends Activity {

    boolean isFirstIn = false;
    private Intent intent;
    private Integer[] imgIds = new Integer[]{R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3,
            R.mipmap.lise_icon4, R.mipmap.lise_icon5, R.mipmap.lise_icon6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进入全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.start_activity);

        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        final SharedPreferences SP_user = getSharedPreferences("user_name",MODE_PRIVATE);


        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);
        String user_name = SP_user.getString("user_name","admin");
        if(!user_name.equals("admin")){
            MainActivity.user_name = user_name;
            SettingFragment.setLoginTrue();
            Log.d("user",MainActivity.user_name);
            Toast.makeText(this, "欢迎回来！",Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isFirstIn) {
                    Toast.makeText(TransitionActivity.this, "欢迎来到时间Box", Toast.LENGTH_LONG).show();
                    //用在Main里面数据库初始化完成后再设置为false
                    intent = new Intent(TransitionActivity.this, MainActivity.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstIn",false);
                    editor.apply();



                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();
                } else {
                    intent = new Intent(TransitionActivity.this, MainActivity.class);
                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();
                }
            }
        }, 1000);

    }

}
