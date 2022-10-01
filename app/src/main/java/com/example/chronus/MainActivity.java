package com.example.chronus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.chronus.Reminders.RemindersFragment;
import com.example.chronus.Setting.SettingFragment;
import com.example.chronus.TimeLine.TimeLineFragment;
import com.example.chronus.Calendar.CalendarFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements View.OnClickListener , RemindersFragment.OnTitleSelectedListener{

    public ViewPager mViewPager;

    private List<Fragment>  mFragments;
    private FragmentPagerAdapter mAdapter;

    public static SQDB mDBHelper;
    //事项列表行数，用来跳转到详细信息页
    public static int Line;
    //事项类型，用来跳转到详细信息页
    public static String type;
    //用来确定所要修改的数据库条目
    public  static String Edit_ID;
    private static Context context;
    //用来存储当前用户的用户名
    public static String user_name = "admin";
    //用来给日历接口
    public static String type2;
    public static String title;
    public static String content;


    private ImageView iv_cal;
    private ImageView iv_rem;
    private ImageView iv_timeline;
    private ImageView iv_tom;
    private ImageView iv_set;
    static private Integer[] imgIds = new Integer[]{R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3,
            R.mipmap.lise_icon4, R.mipmap.lise_icon5, R.mipmap.lise_icon6};


    public static String host = "212.64.17.232";
    public static int port = 31247;
    public static MainActivity mainActivity;


    private int chooseTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进入全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);

        //getWindow().setExitTransition(slide);

        setContentView(R.layout.activity_main);



        //数据库
        mDBHelper = new SQDB(this);
        String localFileName = "test_demo.db";
        String remoteFileName = "test_demo.db";
        mainActivity =this;
        // Syn_To_Server Syn =new Syn_To_Server( mDBHelper.getDBPath());
        //Syn.start();
        user_name = "admin";
        initView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

    }

    private void initView(){
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        // find view
        mViewPager = findViewById(R.id.fragment_vp);

        // init layout_fragment 一级碎片
        mFragments = new ArrayList<>();
        mFragments.add(CalendarFragment.newInstance("日历"));
        mFragments.add(RemindersFragment.newInstance("事项"));
        mFragments.add(TimeLineFragment.newInstance("时间轴"));
        mFragments.add(TomatoFragment.newInstance("番茄"));
        mFragments.add(SettingFragment.newInstance("设置"));
        iv_cal = findViewById(R.id.calendar_tab);
        iv_rem = findViewById(R.id.remainder_tab);
        iv_timeline = findViewById(R.id.timeline_tab);
        iv_tom = findViewById(R.id.tomato_tab);
        iv_set = findViewById(R.id.settings_tab);

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        //默认选择 提醒事项
        mViewPager.setCurrentItem(1);
        iv_rem.setImageResource(R.drawable.reminderbar_fill);
        // register listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);

        final SharedPreferences SP_user = getSharedPreferences("user_name",MODE_PRIVATE);
        String user = SP_user.getString("user_name","admin");
        if(!user.equals("admin")){
            MainActivity.user_name = user;
            SettingFragment.setLoginTrue();
            Log.d("user",MainActivity.user_name);
            Toast.makeText(this, "Welcome Back！",Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.calendar_tab).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                chooseTab=1;
                mViewPager.setCurrentItem(0,false);
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar_fill);
                iv_rem.setImageResource(R.drawable.reminderbar);
                //iv_timeline.setImageResource(R.drawable.setting);
                iv_tom.setImageResource(R.drawable.clock);
                iv_cal.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_cal.setPadding(iv_rem.getPaddingLeft(),iv_rem.getPaddingTop(),iv_rem.getPaddingRight(),iv_rem.getPaddingBottom());
                        }

                }, 200);



            }
        });

        findViewById(R.id.timeline_tab).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mViewPager.setCurrentItem(2,false);
                chooseTab=3;
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar);
                //iv_timeline.setImageResource(R.drawable.setting);
                iv_tom.setImageResource(R.drawable.clock);
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
            }
        });

        findViewById(R.id.remainder_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTab=2;
                mViewPager.setCurrentItem(1,false);
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar_fill);
                //iv_timeline.setImageResource(R.drawable.setting);
                iv_tom.setImageResource(R.drawable.clock);
                iv_rem.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_rem.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                    }

                }, 200);

            }
        });
        findViewById(R.id.tomato_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTab=4;
                mViewPager.setCurrentItem(3,false);
                iv_set.setImageResource(R.drawable.set);
                iv_cal.setImageResource(R.drawable.calendar);
                iv_rem.setImageResource(R.drawable.reminderbar);
                //iv_timeline.setImageResource(R.drawable.setting);
                iv_tom.setImageResource(R.drawable.clock_fill);
                iv_tom.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_tom.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                    }

                }, 200);


            }
        });
        findViewById(R.id.settings_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTab=5;
                mViewPager.setCurrentItem(4,false);
                iv_set.setImageResource(R.drawable.set_fill);
                iv_cal.setImageResource(R.drawable.calendar);
               iv_rem.setImageResource(R.drawable.reminderbar);
                //iv_timeline.setImageResource(R.drawable.setting);
               iv_tom.setImageResource(R.drawable.clock);
                iv_set.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));
                MediaPlayer mMediaPlayer;
                mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                mMediaPlayer.start();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        iv_set.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                    }

                }, 200);


            }
        });
       //检测是否为第一次登陆
        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        Boolean isFirstIn = sharedPreferences.getBoolean("is_first_in_data",true);

        if(isFirstIn){
            initDateofFirstLogin();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_first_in_data",false);
            editor.apply();
        }else{
            Log.d("是否为第一次登陆","不是");
        }

    }



    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageSelected(int position) {
            MediaPlayer mMediaPlayer;
            switch (position){
                case 0 :
                    chooseTab=1;
                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar_fill);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    //iv_timeline.setImageResource(R.drawable.setting);
                    iv_tom.setImageResource(R.drawable.clock);
                    iv_cal.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_cal.setPadding(iv_rem.getPaddingLeft(),iv_rem.getPaddingTop(),iv_rem.getPaddingRight(),iv_rem.getPaddingBottom());
                        }

                    }, 200);
                    break;
                case 1 :
                    chooseTab=2;

                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar_fill);
                    //iv_timeline.setImageResource(R.drawable.setting);
                    iv_tom.setImageResource(R.drawable.clock);
                    iv_rem.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_rem.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                        }

                    }, 200);
                    break;
                case 2 :
                    chooseTab=3;
                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    //iv_timeline.setImageResource(R.drawable.setting);
                    iv_tom.setImageResource(R.drawable.clock);

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    break;
                case 3 :
                    chooseTab=4;

                    iv_set.setImageResource(R.drawable.set);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    //iv_timeline.setImageResource(R.drawable.setting);
                    iv_tom.setImageResource(R.drawable.clock_fill);
                    iv_tom.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_tom.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                        }

                    }, 200);
                    break;
                case 4 :
                    chooseTab=5;

                    iv_set.setImageResource(R.drawable.set_fill);
                    iv_cal.setImageResource(R.drawable.calendar);
                    iv_rem.setImageResource(R.drawable.reminderbar);
                    //iv_timeline.setImageResource(R.drawable.setting);
                    iv_tom.setImageResource(R.drawable.clock);
                    iv_set.setPadding((int)(iv_cal.getPaddingLeft()*0.8),(int)(iv_cal.getPaddingStart()*0.8),(int)(iv_cal.getPaddingRight()*0.8),(int)(iv_cal.getPaddingEnd()*0.8));

                    mMediaPlayer =  MediaPlayer.create(getApplication(),R.raw.navigation_forward_selection);
                    mMediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            iv_set.setPadding(iv_cal.getPaddingLeft(),iv_cal.getPaddingTop(),iv_cal.getPaddingRight(),iv_cal.getPaddingBottom());
                        }

                    }, 200);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class VerticalViewPager extends ViewPager {

        public VerticalViewPager(Context context) {
            super(context);
            init();
        }

        public VerticalViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            // 最重要的设置，将viewpager翻转
            //setPageTransformer(true, new VerticalPageTransformer());
            // 设置去掉滑到最左或最右时的滑动效果
            setOverScrollMode(OVER_SCROLL_NEVER);
        }

        private class VerticalPageTransformer implements ViewPager.PageTransformer {

            @Override
            public void transformPage(View view, float position) {

                if (position < -1) { // [-Infinity,-1)
                    // 当前页的上一页
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    view.setAlpha(1);

                    // 抵消默认幻灯片过渡
                    view.setTranslationX(view.getWidth() * -position);

                    //设置从上滑动到Y位置
                    float yPosition = position * view.getHeight();
                    view.setTranslationY(yPosition);

                } else { // (1,+Infinity]
                    // 当前页的下一页
                    view.setAlpha(0);
                }
            }
        }

        /**
         * 交换触摸事件的X和Y坐标
         */
        private MotionEvent swapXY(MotionEvent ev) {
            float width = getWidth();
            float height = getHeight();

            float newX = (ev.getY() / height) * width;
            float newY = (ev.getX() / width) * height;

            ev.setLocation(newX, newY);

            return ev;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev);
            return intercepted; //为所有子视图返回触摸的原始坐标
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {

            return super.onTouchEvent(swapXY(ev));
        }
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }



    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.calendar_tab:
                chooseTab=1;
                mViewPager.setCurrentItem(0,false);
               // Toast.makeText(getContext(),"Option 1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remainder_tab:
                chooseTab=2;
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.timeline_tab:
                chooseTab=3;
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.tomato_tab:
                chooseTab=4;
                mViewPager.setCurrentItem(3,false);
                break;
            case R.id.settings_tab:
                chooseTab=5;
                mViewPager.setCurrentItem(4,false);
                break;
        }
    }

    @Override
    public void onEventsSelected(int position){




    }

    //数据库操作
//初始化化以及格式化时间
    private static SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");
    private static Date date = new Date();
    private static String Day = formatt.format(date);

    //插入数据
    public static void INSERT(String type, String id, String title,String m,String time) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();//获取可写数据库实例

        db.execSQL("INSERT INTO Remind_List(Type,ID,TITLE,DAY,Content,Checked,User_name) values(?,?,?,?,?,?,?)",
                new String[]{type, id,title,time,m,"0",user_name});

    }

    //删除数据
    public static void DELETE(String id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Remind_List WHERE ID = ?", new String[]{id});
    }

    //修改数据
    public static void update(String type, String  id,String content) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET Content =?,Type =? WHERE ID = ? AND User_name =?",
                new String[]{content,type,id,user_name});
    }
    //根据TITLE查询事项内容
    public static String FIND_Content_By_Title(String title) {

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE TITLE = ?",
                new String[]{title});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex("Content"));


            cursor.close();
            return  result.append(content).toString();
        } else {
            cursor.close();
           // return result.append("该ID下没有信息，出现未知错误").toString();
            return result.append("You can only check the unfinished items!").toString();
        }
    }
    //显示数据库里指定行的ID
    public  static String ShowLineID(int i){

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("Remind_List",null,null,null,null,null,null,null);

        if (cursor.getCount()<=0){
            result.append("无信息");
            cursor.close();
            return result.toString();
        }
        else if(i<cursor.getCount())
        {
            cursor.moveToPosition(i);
            String id = cursor.getString(cursor.getColumnIndex("ID"));

            cursor.close();
            return id;
        }
        else{
            result.append("无信息");
            cursor.close();
            return result.toString();
        }

    }
    public  static String ShowLineTitle(int i){

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("Remind_List",null,null,null,null,null,null,null);

        if (cursor.getCount()<=0){
            result.append("无信息");
            cursor.close();
            return result.toString();
        }
        else if(i<cursor.getCount())
        {
            cursor.moveToPosition(i);
            String title = cursor.getString(cursor.getColumnIndex("TITLE"));

            cursor.close();
            return title;
        }
        else{
            result.append("无信息");
            cursor.close();
            return result.toString();
        }

    }
    //在类型中根据行数查询每一条的TITLE
    public  static String ShowLineTitle_In_Type(int i,String type,String T) {
        StringBuilder title = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE Type =? AND Checked=? AND User_name =?", new String[]{type,T,user_name});


        if (cursor.getCount() <= 0) {
            title.append("无信息");
            cursor.close();
            return title.toString();
        } else {
            cursor.moveToPosition(i);
            title.append(cursor.getString(cursor.getColumnIndex("TITLE")));

            cursor.close();
            return title.toString();
        }
    }


    //根据TITLE查询时间
    public static String ShowDate_By_Title(String title) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE TITLE = ? AND User_name =?", new String[]{title,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex("DAY"));

            cursor.close();
            return result.append(data).toString();
        } else {
            cursor.close();
            return result.append("该ID下没有信息，出现未知错误").toString();
        }
    }
    //判断数据库条目数
    public static int getCount( )
    {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM Remind_List",null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    //判断数据库里相应类型下的条目数
    public static int getCount_By_Type(String type,String T)
    {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM Remind_List WHERE Type=? AND Checked=? AND User_name =?",new String[]{type,T,user_name});
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }
    //根据ID查询数据
    public static String FIND(String id) {

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE ID = ? AND User_name =?",
                new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex("Content"));


            cursor.close();
            return  result.append(content).toString();
        } else {
            cursor.close();
            return result.append("该ID下没有信息，出现未知错误").toString();
        }
    }


    //根据ID查询时间
    public static String ShowDate(String id) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE ID = ? AND User_name =?", new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String data = cursor.getString(cursor.getColumnIndex("DAY"));

            cursor.close();
            return result.append(data).toString();
        } else {
            cursor.close();
            return result.append("该ID下没有信息，出现未知错误").toString();
        }
    }

    //根据TITLE设置事项已完成
    public static void SetFinished_By_Title(String title)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET Checked =? WHERE TITLE = ?AND User_name =?",
                new String[]{"1",title,user_name});


    }
    //根据TITLE检查Checked是否完成
    public static String Is_Finished_By_Title(String title){
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE TITLE = ?AND User_name =?", new String[]{title,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String checked = cursor.getString(cursor.getColumnIndex("Checked"));

            cursor.close();
            return result.append(checked).toString();
        } else {
            cursor.close();
            return result.append("该ID下没有信息，出现未知错误").toString();
        }

    }
    ////根据TITLE设置事项没有完成
    public static void Set_UnFinished_By_Title(String title)
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET Checked =? WHERE TITLE = ?AND User_name = ?",
                new String[]{"0",title,user_name});


    }






    /////////////////////////////////////////////////////////////////////////////分界线
    //判断列表类别数量
    public static int get_ListCount( ){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT COUNT (*) FROM List WHERE User_name =?",new String[]{user_name});
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }
    //显示列表里指定行的ID
    public  static String ShowLineID_inList(int i){

        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.query("List",new String[]{"User_name","ID"},"User_name = ?",new String[]{user_name},null,null,null);

        if (cursor.getCount()<=0){
            result.append("无信息");
            cursor.close();
            return result.toString();
        }
        else if(i<cursor.getCount())
        {
            cursor.moveToPosition(i);
            String id = cursor.getString(cursor.getColumnIndex("ID"));

            cursor.close();
            return id;
        }
        else{
            result.append("无信息");
            cursor.close();
            return result.toString();
        }
    }

    //根据ID查询列表名
    public static String Show_List_name(String id) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM List WHERE ID = ?AND User_name =?",
                new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));

            cursor.close();
            return result.append(name).toString();
        } else {
            cursor.close();
            return result.append("该ID下没有信息，出现未知错误").toString();
        }
    }
    //根据ID查询列表下的事项数量
    public static String Show_List_number(String id) {
        StringBuilder result = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM List WHERE ID = ?AND User_name =?",
                new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            String number = cursor.getString(cursor.getColumnIndex("number"));

            cursor.close();
            return result.append(number).toString();
        } else {
            cursor.close();
            return result.append("该ID下没有信息，出现未知错误").toString();
        }
    }
    //在列表数据表中插入数据
    public static void INSERT_List(String id, String name,String icon_color, String number) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();//获取可写数据库实例

        db.execSQL("INSERT INTO List(ID,name,icon_color,number,User_name) values(?,?,?,?,?)",
                new String[]{id, name,icon_color,number,user_name});
    }
    //修改数据列表书库对应ID的数据
    public static void update_List(String name, String  id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE List SET name =?  WHERE ID = ?AND User_name =?",
                new String[]{name,id,user_name});
    }
    //每当添加新事项时，相应类型的列表数据库中的事项数就会加1
    public static void Increase_List_Number(String type)
    {
        String number_before;
        SQLiteDatabase db1 = mDBHelper.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM List WHERE name = ?AND User_name =?",
                new String[]{type,user_name});//先提取之前的数据再递增
        if (cursor.moveToFirst()) {
            number_before = cursor.getString(cursor.getColumnIndex("number"));

            cursor.close();

        } else {
            number_before = "0";
            cursor.close();

        }
        db1.close();
        Integer i = Integer.parseInt(number_before);
        i++;
        SQLiteDatabase db2 = mDBHelper.getWritableDatabase();
        db2.execSQL("UPDATE List SET number =?  WHERE name = ?AND User_name =?",
                new String[]{i.toString(),type,user_name});
    }
    //根据ID查询列表的条目前面的图标的颜色
    public static int Show_List_Color_By_ID(String id)
    {
        String color;
        SQLiteDatabase db =mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM List WHERE ID =?AND User_name =?", new String[]{id,user_name});
        if (cursor.moveToFirst()) {
            color = cursor.getString(cursor.getColumnIndex("icon_color"));

            cursor.close();
            return  Integer.parseInt(color);
        } else {
            cursor.close();
            return 0;
        }
    }

    //删除列表和对应的事项数据
    public static void DELETE_LIST_By_ID(String id) {
        //删除列表和列表里面的值
        //注意这里有顺序问题，要先删除事项的数据，再删除列表数据，不然无法从对应的类型名找到对应的事项
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Remind_List WHERE Type = ?AND User_name =?", new String[]{Show_List_name(id),user_name});

        db.execSQL("DELETE FROM List WHERE ID = ?AND User_name =?", new String[]{id,user_name});

    }
    //列表类型名搜索
    public static void Search_Title(String search){

    }

    //以下代码登陆时使用
    ////////////////////////////////////////////////////////////////////////////
//判断用户名和密码是否匹配
    public  static boolean isUser_Password_Match(String password,String username) {

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE User_name =?", new String[]{username});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String password_inDB = cursor.getString(cursor.getColumnIndex("Password"));

            cursor.close();
            if (password_inDB.equals(password)) {
                return true;
            }

        } else {
            cursor.close();
            return false;
        }
        return false;
    }

    //判断用户名是否为空
    public static boolean is_User_name_Ampty(String username){

        SQLiteDatabase db =mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE User_name =?", new String[]{username});

        //检查用户数据库里是否有该用户
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;

        } else {
            cursor.close();
            return false;
        }
    }


    //注册用户，将用户名和密码导入数据库
    public static  void CreateUser(String user_name,String password){

        SQLiteDatabase db =mDBHelper.getWritableDatabase();
        db.execSQL("INSERT INTO User(User_name,Password) values(?,?)",
                new String[]{user_name,password});


    }
// 以下用于日历接口
////////////////////////////////////////////////////////////////////////////

    //根据日期从数据库里找到当天的所有事项ID
    public static List<String> find_ID_By_date(String date) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String pattern = ".*" + date + ".*";//进行模糊匹配
        String day;//用来从数据库里读出时间来挨个进行模糊匹配
        List<String> a =new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM Schedule WHERE User_name =? ", new String[]{MainActivity.user_name});
        //第一个if判断是否能找到相应数据
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                day = cursor.getString(cursor.getColumnIndex("Date"));
                //如果模糊匹配成功就把相应事项的ID加入集合
                if (Pattern.matches(pattern, day)) {
                    a.add(cursor.getString(cursor.getColumnIndex("ID")));
                }

            }
            cursor.close();
            return a;
        } else {
            cursor.close();
            return a;
        }

    }

    //获取日程数据库中的所有的date值返回List
    public static List<String> Find_All_Date()
    {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * FROM Schedule WHERE User_name =?",new String[]{user_name});

        List<String> a =new ArrayList<String>();
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String temp= cursor.getString(cursor.getColumnIndexOrThrow("Date"));
                if(a.contains(temp))continue;
                a.add(temp);

            }
            cursor.close();
            return a;
        } else {
            cursor.close();
            return a;
        }
    }

    //向日程中添加事项
    public static void Insert_Schedule(String id, String date, String start_time, String end_time, String title,String place, String content,String bg_color){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("INSERT INTO Schedule(ID ,Date,Start_Time,End_Time,Title ,Place," +
                        "Content,bg_Color,User_name) values(?,?,?,?,?,?,?,?,?)",
                new String[]{id,date,start_time,end_time,title,place,content,bg_color,user_name});

    }
    //根据ID从数据库中读出TITLE
    public static String get_Title_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("Title"));

            cursor.close();
            return title;
        } else {
            cursor.close();
            return "查询不到内容，出现意外错误";
        }
    }
    //根据ID从数据库中读出开始时间
    public static int get_StartTime_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String start_time = cursor.getString(cursor.getColumnIndex("Start_Time"));
            Integer s = Integer.parseInt(start_time);
            cursor.close();
            return s;
        } else {
            cursor.close();
            return 0;
        }
    }

    //根据ID从数据库中读出结束时间
    public static int get_EndTime_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String end_time = cursor.getString(cursor.getColumnIndex("End_Time"));
            Integer s = Integer.parseInt(end_time);
            cursor.close();
            return s;
        } else {
            cursor.close();
            return 0;
        }
    }


    //根据ID从数据库中读出地点
    public static String get_Place_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String place = cursor.getString(cursor.getColumnIndex("Place"));

            cursor.close();
            return place;
        } else {
            cursor.close();
            return "查询不到内容，出现意外错误";
        }
    }


    //根据ID从数据库中读出详细内容
    public static String get_Content_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String content = cursor.getString(cursor.getColumnIndex("Content"));

            cursor.close();
            return content;
        } else {
            cursor.close();
            return "查询不到内容，出现意外错误";
        }
    }

    //根据ID从数据库中读出背景颜色
    public static int get_Color_In_Schedule(String id){


        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND ID =? ", new String[]{MainActivity.user_name,id});

        //能查询到用户才返回
        if (cursor.moveToFirst()) {
            String bg_color = cursor.getString(cursor.getColumnIndex("bg_Color"));
            Integer s = Integer.parseInt(bg_color);
            cursor.close();
            return s;
        } else {
            cursor.close();
            return 0;
        }
    }

    //根据date，和开始结束时间匹配删除
    public static void DELETE_Schedule(String date_time, String start_time,String end_time){

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Schedule WHERE Date = ?AND Start_Time =? AND End_Time =?AND User_name =?", new String[]{date_time,start_time,end_time,MainActivity.user_name});

    }

    //修改函数根据ID值修改相应日程
    public static void Update_Schedule(String title,String content,String place,String id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Schedule SET Title =? ,Content =?,Place =? WHERE ID = ? AND User_name =?",
                new String[]{title,content,place,id,user_name});
    }

    //根据date，start_time，end_time获取日程事项的iD
    public static String get_Schedule_ID(String date,String start_time,String end_time){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Schedule WHERE User_name =?AND Date =?AND Start_Time =? AND End_Time =? ", new String[]{user_name, date,start_time,end_time});
        //能查询到id才返回
        if (cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("ID"));

            cursor.close();
            return id;
        } else {
            cursor.close();
            return "找不到对应的ID，出现意外错误";
        }

    }
    //9.24添加
    //*********//提醒事项列表根据行来获取ID值
    public static String ShowLineID_inType(int i,String type,String T){

        StringBuilder id = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE Type =? AND Checked=? AND User_name =?", new String[]{type,T,user_name});
        if (cursor.getCount() <= 0) {
            id.append("无信息");
            cursor.close();
            return id.toString();
        } else {
            cursor.moveToPosition(i);
            id.append(cursor.getString(cursor.getColumnIndex("ID")));

            cursor.close();
            return id.toString();
        }
    }
    //*********//根据ID值来获取时间
    public static String Get_Day_by_ID(String id ){
        StringBuilder day = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE ID =?  AND User_name =?", new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            day.append( cursor.getString(cursor.getColumnIndex("DAY")));
            cursor.close();
            return day.toString();
        } else {
            cursor.close();
            return day.append("该ID下没有信息，出现未知错误").toString();
        }
    }
    //*********//根据ID值来获取详细内容
    public static String Get_Title_by_ID(String id ){
        StringBuilder content = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE ID =?  AND User_name =?", new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            content.append( cursor.getString(cursor.getColumnIndex("TITLE")));
            cursor.close();
            return content.toString();
        } else {
            cursor.close();
            return content.append("该ID下没有信息，出现未知错误").toString();
        }
    }
    //*********//根据ID值来获取详细内容
    public static String Get_Content_by_ID(String id ){
        StringBuilder content = new StringBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery
                ("SELECT * FROM Remind_List WHERE ID =?  AND User_name =?", new String[]{id,user_name});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            content.append( cursor.getString(cursor.getColumnIndex("Content")));
            cursor.close();
            return content.toString();
        } else {
            cursor.close();
            return content.append("该ID下没有信息，出现未知错误").toString();
        }
    }

    //****************//修改提醒事项的数据
    public static void Update_Remind_by_ID(String type, String  id,String title, String content) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET TITLE = ?, Content = ? WHERE Type =? AND ID = ? AND User_name =?",
                new String[]{title,content,type,id,user_name});

    }

    //根据日期找到该日期所有的id
    public static List<String> find_ID_By_DAY(String day) //参数的格式必须为（假如今天是9月9号）
    {
        List<String> a= new ArrayList<String>();
        SQLiteDatabase db =mDBHelper.getReadableDatabase();
        String pattern = ".*" + day + ".*";//进行模糊匹配
        String date;//用来从数据库里读出时间来挨个进行模糊匹配
        Cursor cursor = db.rawQuery("SELECT * FROM Remind_List WHERE User_name =? AND Checked =?", new String[]{user_name,"0"});
        //存在数据才返回
        if (cursor.moveToFirst()) {
            for(int i =0;i<cursor.getCount();i++) {
                cursor.moveToPosition(i);
                date =cursor.getString(cursor.getColumnIndex("DAY"));
                String id = cursor.getString(cursor.getColumnIndex("ID"));
                if (Pattern.matches(pattern, date)) {
                    a.add(id);
                }
            }
            cursor.close();
            return a;
        } else {
            cursor.close();
            return a;
        }
    }
    //当前用户数据清除
    public static void Delete_All()
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Remind_List WHERE User_name = ?", new String[]{user_name});
        db.execSQL("DELETE FROM List WHERE User_name = ?", new String[]{user_name});
        //db.execSQL("DELETE FROM User WHERE User_name = ?", new String[]{user_name});
        db.execSQL("DELETE FROM Schedule WHERE User_name = ?", new String[]{user_name});

    }
    //将用户名替换
    public static void Update_User_Inf()
    {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL("UPDATE Remind_List SET User_name = ? WHERE User_name = ?", new String[]{user_name,"admin"});
        db.execSQL("UPDATE List SET User_name = ? WHERE User_name = ?", new String[]{user_name,"admin"});
        db.execSQL("UPDATE Schedule SET User_name = ? WHERE User_name = ?", new String[]{user_name,"admin"});

    }
    static public void initDateofFirstLogin(){
        //初始化示例数据库
        Calendar calendar = Calendar.getInstance();

        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String date = year + "-" + month + "-" + day;
        MainActivity.INSERT_List(String.valueOf(9999),"ExampleList",imgIds[3].toString(),"5" );
        MainActivity.INSERT_List(String.valueOf(9998),"ReminderList",imgIds[0].toString(),"3" );
        MainActivity.INSERT_List(String.valueOf(9997),"memorandum",imgIds[2].toString(),"5" );
        MainActivity.INSERT_List(String.valueOf(9996),"ShoppingList",imgIds[5].toString(),"6" );
        MainActivity.INSERT_List(String.valueOf(9995),"BirthdayParty",imgIds[2].toString(),"5" );
        MainActivity.INSERT_List(String.valueOf(9994),"Work",imgIds[4].toString(),"5" );


        MainActivity.INSERT("ExampleList",String.valueOf(8999),"All your reminders(unfinished items) are shown here"," "," ");
        MainActivity.INSERT("ExampleList",String.valueOf(8998),"Click the circle on the left, if task is completed"," "," ");
        MainActivity.INSERT("ExampleList",String.valueOf(8997),"Click again if you made mistake"," "," ");
        MainActivity.INSERT("ExampleList",String.valueOf(8996),"Long press to view the details of an item","Details of the schedule is shown here"," ");
        MainActivity.INSERT("ExampleList",String.valueOf(8995),"Click on the upper right corner to see what has been done"," "," ");

        MainActivity.INSERT("ReminderList",String.valueOf(8989),"Go the restaurant at 5 pm"," ",date+"-17-0");
        MainActivity.INSERT("ReminderList",String.valueOf(8988),"Return my book the the Library"," "," ");
        MainActivity.INSERT("ReminderList",String.valueOf(8987),"Pick up clothes from dry cleaners"," ",date+"-19-0");

        MainActivity.INSERT("memorandum",String.valueOf(8979),"call Alex"," "," ");
        MainActivity.INSERT("memorandum",String.valueOf(8978),"Prepare for the party"," "," ");
        MainActivity.INSERT("memorandum",String.valueOf(8977),"wash my bike"," "," ");
        MainActivity.INSERT("memorandum",String.valueOf(8976),"appointment with Lily"," "," ");
        MainActivity.INSERT("memorandum",String.valueOf(8975),"Pick up flowers"," ",date+"-15-35");

        MainActivity.INSERT("ShoppingList",String.valueOf(8969),"Milk"," "," ");
        MainActivity.INSERT("ShoppingList",String.valueOf(8968),"Egg"," "," ");
        MainActivity.INSERT("ShoppingList",String.valueOf(8967),"Bread"," "," ");
        MainActivity.INSERT("ShoppingList",String.valueOf(8966),"StrawBerry"," "," ");
        MainActivity.INSERT("ShoppingList",String.valueOf(8965),"Tissue"," "," ");
        MainActivity.INSERT("ShoppingList",String.valueOf(8964),"toothpaste"," "," ");

        MainActivity.INSERT("BirthdayParty",String.valueOf(8959),"birthday cake"," "," ");
        MainActivity.INSERT("BirthdayParty",String.valueOf(8958),"toys"," "," ");
        MainActivity.INSERT("BirthdayParty",String.valueOf(8957),"Brand New Laptop"," "," ");
        MainActivity.INSERT("BirthdayParty",String.valueOf(8956),"presents"," "," ");
        MainActivity.INSERT("BirthdayParty",String.valueOf(8955),"taking photos"," "," ");

        MainActivity.INSERT("Work",String.valueOf(8949),"Organize actual cases"," "," ");
        MainActivity.INSERT("Work",String.valueOf(8948),"caffe time"," "," ");
        MainActivity.INSERT("Work",String.valueOf(8947),"Complete the proposal document","Documentation needs to be submitted by tomorrow"," ");
        MainActivity.INSERT("Work",String.valueOf(8946),"Schedule staff meeting times"," "," ");
        MainActivity.INSERT("Work",String.valueOf(8945),"send party invitations"," "," ");
        Insert_Schedule("999",date,""+9,""+10,"Marketing team meeting","","",""+0);
        Insert_Schedule("998",date,""+11,""+12,"Zoom meeting","","",""+2);
        Insert_Schedule("997",date,""+13,""+14,"Conference call with Alex","","",""+4);
        Insert_Schedule("996",date,""+15,""+17,"Project design conference","","",""+1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        //Date date = new Date(System.currentTimeMillis());
    }

     public void LogOut(){
        final SharedPreferences SP_user = getSharedPreferences("user_name",MODE_PRIVATE);
        SharedPreferences.Editor editor = SP_user.edit();
        editor.putString("user_name","admin");
        editor.apply();
    }

}