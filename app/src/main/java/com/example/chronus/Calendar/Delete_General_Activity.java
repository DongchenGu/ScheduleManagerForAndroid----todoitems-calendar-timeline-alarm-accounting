package com.example.chronus.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chronus.R;

public class Delete_General_Activity extends AppCompatActivity implements View.OnClickListener {
     private TextView back,delete,date;
     private EditText title,place,content;
     private static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //进入全屏
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.calendar_edit_schedule);
        initView();
        initDate();
    }
    private void initView(){
     back=findViewById( R.id.tv_return );
     delete=findViewById( R.id.tv_delete );
     date=findViewById( R.id.tv_date );
     title=findViewById( R.id.ADDID );
     place=findViewById(R.id.edit_place);
     content=findViewById(R.id.edit_content);
     back.setOnClickListener( this );
     delete.setOnClickListener( this );

    }

    private  void initDate(){
        Bundle bundle=getIntent().getExtras();
        int year=bundle.getInt("year1");
        int month=bundle.getInt("month1");
        int day=bundle.getInt("day1");
        int begin=bundle.getInt( "begin" );
        int end=bundle.getInt( "end" );
         id=bundle.getString( "id" );
        String a=parseStr( begin );
        String b=parseStr( end );
        date.setText( year+"年"+month+"月"+day+"日"+" "+a+":00-"+b+":00");
        place.setText(bundle.getString("place"));
        content.setText(bundle.getString("content"));
        title.setText( id);
    }
    public  String parseStr(int a){

        if(a<10) return ("0"+a);
        else return (""+a);
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(  );
        switch (view.getId()){
            case R.id.tv_return :{
                if(isUpdate()){
                    Bundle bundle1=new Bundle(  );
                    bundle1.putString( "UpdateTitle",title.getText().toString() );
                    bundle1.putString("UpdatePlace",place.getText().toString());
                    bundle1.putString("UpdateContent",content.getText().toString());
                    intent.putExtras( bundle1 );
                    setResult( Activity.RESULT_OK,intent);
                    finish();
                }
                setResult( Activity.RESULT_CANCELED,intent);
                finish();
            }

            case R.id.tv_delete: {
                setResult( Activity.RESULT_FIRST_USER,intent);
                finish();
            }
        }
    }

    public boolean isUpdate(){
        if(title.getText().toString().equals( id )) return false;
        else return true;
    }
}
