package com.example.chronus.Setting;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

public class LoginActivity extends AppCompatActivity   {
    TextInputEditText user_name;
    TextInputEditText password;
    TextInputLayout username_layout;
    TextInputLayout password_layout;
    Button sure_but;
    Button cancel_but;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //进入全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


        //加载视图
        setContentView(R.layout.login_activity);

        user_name = findViewById(R.id.username_et);
        username_layout = findViewById(R.id.uesrname_layout);
        password = findViewById(R.id.password_et);
        password_layout = findViewById(R.id.password_layout);
        sure_but = findViewById(R.id.sure_button);
        cancel_but = findViewById(R.id.cancel_button);

        setListener();
    }


    private void setListener(){
        sure_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isPasswordValid(password.getText())) {
                    password_layout.setError("密码不能少于8位");
                    Log.d("password_error", "密码长度不正确");
                }
                else{
                    if(!MainActivity.is_User_name_Ampty(user_name.getText().toString())) {
                        //检测用户是否存在，如果不存在执行以下步骤
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        AlertDialog alertDialog = builder
                                .setTitle("系统提示")
                                .setMessage("当前用户不存在，是否用当前输入用户名和密码创建账户？")
                                .setNegativeButton("不创建", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int n) {

                                    }
                                })
                                .setPositiveButton("创建", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int n) {
                                        MainActivity.CreateUser(user_name.getText().toString(), password.getText().toString());
                                        Toast.makeText(LoginActivity.this, "欢迎你，新用户！", Toast.LENGTH_SHORT).show();
                                        final SharedPreferences SP_user = getSharedPreferences("user_name",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = SP_user.edit();
                                        editor.putString("user_name",user_name.getText().toString());
                                        editor.apply();
                                        Log.d("CreateUser", "创建用户账号完成");
                                        //自动切换为登陆后的用户
                                        MainActivity.user_name =user_name.getText().toString();
                                        SettingFragment.setLoginTrue();
                                        //是否保留示例数据
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(LoginActivity.this);
                                        AlertDialog alertDialog2 = builder2
                                                .setTitle("示例数据")
                                                .setMessage("是否保留示例数据")
                                                .setNegativeButton("不保留", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int n) {
                                                        finish();
                                                    }
                                                })
                                                .setPositiveButton("保留", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int n) {
                                                        MainActivity.Update_User_Inf();
                                                        finish();
                                                    }
                                                }).create();
                                        alertDialog2.show();

                                    }
                                }).create();
                        alertDialog.show();


                    }
                    if(MainActivity.isUser_Password_Match(password.getText().toString(), user_name.getText().toString())){
                        //验证用户名和密码是否对应，如果匹配即登录成功执行以下代码
                        MainActivity.user_name = user_name.getText().toString();
                        final SharedPreferences SP_user = getSharedPreferences("user_name",MODE_PRIVATE);
                        SharedPreferences.Editor editor = SP_user.edit();
                        editor.putString("user_name",user_name.getText().toString());
                        editor.apply();



                        LoginSuccess();
                        password_layout.setError(null); // Clear the error
                        Log.d("判断密码：","密码正确");
                        finish();
                    } else {
                        //password_layout.setError(null); // Clear the error
                        password_layout.setError("密码不正确");
                        Log.d("判断密码：","密码不正确");
                    }
                }

            }//这个是else的大括号
        });
        // Clear the error once more than 8 characters are typed.
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(password.getText())) {

                    password_layout.setError(null); //Clear the error
                }
                return false;
            }
        });
        cancel_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
    private boolean isPasswordValid(@Nullable Editable text) {
        //判断密码正确
        return text != null && text.length() >= 8;//这里是判断密码长度的



    }

    private void LoginSuccess(){
        //登陆成功之后的处理
        SettingFragment.setLoginTrue();
        Toast.makeText(this, "欢迎回来！",Toast.LENGTH_SHORT).show();

    }
}
