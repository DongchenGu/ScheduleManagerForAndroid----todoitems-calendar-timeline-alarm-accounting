package com.example.chronus.Reminders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.chronus.AlarmNotification.AlarmService;
import com.example.chronus.AlarmNotification.DateTimeUtil;
import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.util.Calendar;


public class ADD_DATA_Activity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener{

    private Integer year, month, day, hour, minute;
    //创建两个StringBuffer变量，用于拼接获取到的时间数据。
    private StringBuffer date, time;
    private String pre_type;
    private Context context;
    private RelativeLayout llDate, llTime;
    private TextView tvDate, tvTime;
    private Integer number;
    public static final String INTENT_ALARM_LOG = "intent_alarm_log";

    //在TextView上显示的字符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进入全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.reminder_new_item);
        Intent intent = getIntent();//获取Intent对象
        number = intent.getIntExtra("number",0);
        pre_type =intent.getStringExtra("type");
        context = this;
        date = new StringBuffer();
        time = new StringBuffer();
        initDateTime();
        initView();

    }

    public void Store(View view){
        MediaPlayer mMediaPlayer;
        mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.confirm_up);
        mMediaPlayer.start();


        //找到所有控件
        EditText editText = (EditText ) findViewById(R.id.ADDContent);
        EditText editText2 = (EditText) findViewById(R.id.AddTitle);



        String content = editText.getText().toString();

        if(content == null){
            content=" ";
        }

        Integer tem = number+1;
        String id = tem.toString();
        final String title = editText2.getText().toString();

        Integer Infactmonth = month+1;

        String Alerttime = null;

        Boolean Notife_or_not = false;


        //如果没设置日期
        if(tvDate.getText().equals("点击添加时间")){
            //没设置日期，不设置提醒，时间为空
            Alerttime = "未设置时间";
            Notife_or_not =false;
        }else if(tvTime.getText().equals("点击添加时间")){
            //设置了日期没设置时刻，那按照9：00计算
            Alerttime = year.toString()+"-"+(month)+"-"+
                    day.toString()+"-"+"9"+"-"+"0";
            Notife_or_not = true;
        }else if(tvTime.getText() != null){
            Alerttime = year.toString()+"-"+(Infactmonth).toString()+"-"+
                    day.toString()+"-"+hour.toString()+"-"+minute.toString();
            Notife_or_not = true;
        }
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        //Date date = null;
        if(!title.equals("")){
            //将信息写入数据库
            MainActivity.INSERT(pre_type,id,title,content,Alerttime);
            Log.d("insert_rem",id+": "+Alerttime);
            MainActivity.Increase_List_Number(pre_type);//相应类型事项数增加
            this.finish();
        }else{
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("提醒");
            builder.setMessage("未填写提醒事项标题");
            builder.create();
            builder.show();
        }
        if(Notife_or_not){
            //设置了时间才进行通知
            Calendar settingTime = Calendar.getInstance();
            settingTime.set(Calendar.YEAR,year);
            settingTime.set(Calendar.MONTH,month);
            settingTime.set(Calendar.DAY_OF_MONTH,day);
            settingTime.set(Calendar.HOUR_OF_DAY,hour);
            settingTime.set(Calendar.MINUTE,minute);
            settingTime.set(Calendar.SECOND,0);
            settingTime.set(Calendar.MILLISECOND, 0);
            int day = settingTime.get(Calendar.DAY_OF_YEAR);
            int day_now = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            int hour = settingTime.get(Calendar.HOUR_OF_DAY);
            int hour_now = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int min = settingTime.get(Calendar.MINUTE);
            int min_now = Calendar.getInstance().get(Calendar.MINUTE);
            int sec = settingTime.get(Calendar.SECOND);
            int sec_now = Calendar.getInstance().get(Calendar.SECOND);
            int count_time;
            if(day>day_now){
                count_time = sec - sec_now + (min - min_now)*60 + (24 - hour_now + hour + 24 * (day-day_now-1))*3600 ;
            }else{
                count_time = sec - sec_now + (min - min_now)*60 + (hour - hour_now)*3600 ;
            }

            //通知服务
            Intent i = new Intent(getBaseContext(), AlarmService.class);
            // 获取i秒之后的日期时间字符串
            i.putExtra("alarm_time", DateTimeUtil.getNLaterDateTimeString(Calendar.SECOND,count_time));
            i.putExtra("task_id", id);
            i.putExtra("title",title);
            startService(i);
        }

    }
        //初始化控件，并为两个LinearLayout设置监听事件：
    private void initView() {
        llDate =  findViewById(R.id.ll_date);
        tvDate = (TextView) findViewById(R.id.tv_date);//日期
        llTime =  findViewById(R.id.ll_time);
        tvTime = (TextView) findViewById(R.id.tv_time);//时间
        llDate.setOnClickListener(this);
        llTime.setOnClickListener(this);
        tvDate.setText("点击添加时间");
        tvDate.setTextColor(Color.parseColor("#A4A2A4"));
        tvTime.setText("点击添加时间");
        tvTime.setTextColor(Color.parseColor("#A4A2A4"));
        TextView cancel_tv = findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.confirm_down);
                mMediaPlayer.start();
                finish();
            }
        });
        TextView list_name_tv = findViewById(R.id.list_name_tv);
        list_name_tv.setText(pre_type);
    }
    //使用Calendar类获取当前的日期时间。
    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear+1;
        this.day = dayOfMonth;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (date.length() > 0) { //清除上次记录的日期
                            date.delete(0, date.length());
                        }
                        tvDate.setText(date.append(String.valueOf(year)).append("年").append(String.valueOf(month+1)).append("月").append(day).append("日"));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(context, R.layout.dialog_date, null);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);

                dialog.setTitle("设置日期");
                dialog.setView(dialogView);
                dialog.show();
                //初始化日期监听事件
                datePicker.init(year, month , day, this);//
                break;
            case R.id.ll_time:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (time.length() > 0) { //清除上次记录的日期
                            time.delete(0, time.length());
                        }
                        tvTime.setText(time.append(String.valueOf(hour)).append("时").append(String.valueOf(minute)).append("分"));
                        dialog.dismiss();
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2 = builder2.create();
                View dialogView2 = View.inflate(context, R.layout.dialog_time, null);
                TimePicker timePicker = (TimePicker) dialogView2.findViewById(R.id.timePicker);
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
                timePicker.setIs24HourView(true); //设置24小时制
                timePicker.setOnTimeChangedListener(this);
                dialog2.setTitle("设置时间");
                dialog2.setView(dialogView2);
                dialog2.show();
                break;
            case R.id.add_tv:
                 Store(v);
                 break;
        }
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }



}