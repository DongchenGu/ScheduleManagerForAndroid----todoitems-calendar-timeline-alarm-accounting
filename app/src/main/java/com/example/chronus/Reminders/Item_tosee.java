package com.example.chronus.Reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;
import com.example.chronus.Reminders.Update_DATA_Activity;

public class Item_tosee extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //进入全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tosee);


        String id = MainActivity.ShowLineID(MainActivity.Line);
        String title = MainActivity.ShowLineTitle_In_Type(MainActivity.Line,MainActivity.type,"0");
        String content = MainActivity.FIND(id);
        String date = MainActivity.ShowDate(id);


        TextView textView1 = (TextView)findViewById(R.id.todo_ID) ;
        TextView textView2 = (TextView)findViewById(R.id.todo_content);
        TextView textView3 = (TextView)findViewById(R.id.todo_date);
        textView1.setText(id);
        textView2.setText(content);
        textView3.setText(date);
    }

    public void Delete(View view){
        //根据列表索引的行数找到数据库ID
        String id = MainActivity.ShowLineID(MainActivity.Line);
        MainActivity.DELETE(id);
        Toast.makeText(getApplicationContext(), "相关事项已删除",
                Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public  void To_edit(View view){
        MainActivity.Edit_ID = MainActivity.ShowLineID(MainActivity.Line);
        Intent intent = new Intent(this, Update_DATA_Activity.class);


        startActivity(intent);
        this.finish();
    }

}
