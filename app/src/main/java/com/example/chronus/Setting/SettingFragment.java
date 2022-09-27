package com.example.chronus.Setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.chronus.MainActivity;
import com.example.chronus.R;
import com.example.chronus.Server.Syn_From_Server;
import com.example.chronus.Server.Syn_To_Server;


public class SettingFragment extends Fragment{
    private static final String ARG_SHOW_TEXT = "text";
    private  String mContentText;
    private View view;

    private RelativeLayout rl_login;
    private RelativeLayout rl_push;
    private RelativeLayout rl_pull;
    private RelativeLayout rl_clean;
    private ImageView avatar_img;
    private TextView user_name;
    public static boolean isLogin = false;


    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance(String param1){
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.setting_layout, container, false);
        initView();
        init();
        setListener();


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        init();//重新刷新用户
    }

    private void init(){
        //如果用户已经创建用户就显示已创建的用户
        if(MainActivity.user_name.equals("admin") )
        {
            user_name.setText("登陆或注册");
            Log.d("Setting-UserName",MainActivity.user_name);
            //user_name.setTextColor(0X8E8F90);
        } else{
            user_name.setText(MainActivity.user_name);
            //user_name.setTextColor(0X000000);
            Log.d("Setting-UserName-in",MainActivity.user_name);
        }

    }

    private void initView(){
        //初始化控件
        rl_login = view.findViewById(R.id.rl_login);
        rl_pull = view.findViewById(R.id.rl_pull);
        rl_push = view.findViewById(R.id.rl_push);
        rl_clean = view.findViewById(R.id.rl_clean);
        avatar_img = view.findViewById(R.id.user_avatar);
        user_name = view.findViewById(R.id.user_name);

    }

    private void setListener(){
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SettingFragment.isLogin){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog alertDialog = builder
                            .setTitle("系统提示")
                            .setMessage("您已经登陆，要退出？")
                            .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int n) {
                                    MainActivity.user_name ="admin";
                                    setLoginFalse();
                                    Log.d("Back", "退出登陆");
                                    init();//刷新用户
                                    Toast.makeText(getContext(), "您已登出", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("保持登陆", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int n) {

                                }
                            }).create();
                    alertDialog.show();

                }
                else {
                    Log.d("test:","跳转到登陆页面");



                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        rl_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据从服务器端同步到本地
                if(MainActivity.user_name.equals("admin")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog alertDialog = builder
                            .setTitle("系统提示")
                            .setMessage("请在登录后尝试此操作。")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int n) {

                                }
                            }).create();
                    alertDialog.show();
                }else{
                    new Syn_From_Server(MainActivity.mDBHelper.getDBPath()).start();
                }

            }
        });
        rl_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据从本地同步到服务器
                if(MainActivity.user_name.equals("admin")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    AlertDialog alertDialog = builder
                            .setTitle("系统提示")
                            .setMessage("请在登录后尝试此操作。")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int n) {

                                }
                            }).create();
                    alertDialog.show();
                }else{
                    Syn_To_Server syn_to_server = new Syn_To_Server( MainActivity.mDBHelper.getDBPath());
                    syn_to_server.start();
                }

            }
        });
        rl_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("test:",Boolean.toString(getisLogin()));
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog alertDialog = builder
                        .setTitle("系统提示")
                        .setMessage("确定要清空所有数据吗，此操作不可复原！")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int n) {
                                MainActivity.Delete_All();
                                Toast.makeText(getContext(), "已清除用户所有数据", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int n) {

                            }
                        }).create();
                alertDialog.show();

            }
        });
    }
    public static void setLoginTrue(){
        isLogin = true;
    }
    public static void setLoginFalse(){
        isLogin = false;
    }
    public static boolean getisLogin(){
        return isLogin;
    }
}
