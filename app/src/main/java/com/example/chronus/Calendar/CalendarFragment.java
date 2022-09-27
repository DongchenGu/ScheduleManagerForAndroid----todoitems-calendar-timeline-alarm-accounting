package com.example.chronus.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalendarFragment extends Fragment implements CalendarView.OnDateSelectedListener, CalendarView.OnYearChangeListener{

    //日历布局的控件声明
    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private RelativeLayout mRelativeTool;
    private Activity activity;
    private CalendarLayout mCalendarLayout;
    private static MainActivity mainActivity;

    private Boolean ifFirstIn = true;
    //选中日期的年、月、日
    private static int mYear,mMonth,mDay;

    //选中日期的字符串表示
    private  static String date;

    //标记日期的数组
    private   List<Calendar> schemes= new ArrayList<>();

    //删除日程的起止范围
    private static int first,last;

    //新建日程的起始时间
    private int mHour;

    //Add_General_Activity返回的数据：事项名称、地点、详细内容
    private static String title,place,content;

    //Delete_General_Activity返回的数据：更新后的事项名称
    private static String update;

    //建立日程的起止范围
    public static int start,end,item;

    //24小时所对应的24个button
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,
            btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24;

    //24个按钮之间的23条下划线
    private View line1,line2,line3,line4,line5,line6,line7,line8,line9,line10,line11,line12,line13,line14,line15,line16,
            line17,line18,line19,line20,line21,line22,line23;

    //按钮点击事件监听器
    private View.OnClickListener listener;

    //按钮聚焦事件监听器
    private View.OnFocusChangeListener listener1;

    private static final String ARG_SHOW_TEXT = "text";

    private View view;


    public static CalendarFragment newInstance(String param1){
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取当前Fragment的Activity
        activity=getActivity();
        mainActivity=new MainActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_calendar,container,false);
        Log.d("顺序","onCreateView");
        //获取日历布局控件ID
        mTextMonthDay = view.findViewById(R.id.tv_month_day);
        mTextYear = view. findViewById(R.id.tv_year);
        mTextLunar =  view.findViewById(R.id.tv_lunar);
        mRelativeTool =  view.findViewById(R.id.rl_tool);
        mCalendarView = view. findViewById(R.id.calendarView);
        mTextCurrentDay = view.findViewById(R.id.tv_current_day);
        mCalendarLayout =view. findViewById(R.id.calendarLayout);

        //获取按钮ID
        btn1=view.findViewById(R.id.btn_1);
        btn2=view.findViewById(R.id.btn_2);
        btn3=view.findViewById(R.id.btn_3);
        btn4=view.findViewById(R.id.btn_4);
        btn5=view.findViewById(R.id.btn_5);
        btn6=view.findViewById(R.id.btn_6);
        btn7=view.findViewById(R.id.btn_7);
        btn8=view.findViewById(R.id.btn_8);
        btn9=view.findViewById(R.id.btn_9);
        btn10=view.findViewById(R.id.btn_10);
        btn11=view.findViewById(R.id.btn_11);
        btn12=view.findViewById(R.id.btn_12);
        btn13=view.findViewById(R.id.btn_13);
        btn14=view.findViewById(R.id.btn_14);
        btn15=view.findViewById(R.id.btn_15);
        btn16=view.findViewById(R.id.btn_16);
        btn17=view.findViewById(R.id.btn_17);
        btn18=view.findViewById(R.id.btn_18);
        btn19=view.findViewById(R.id.btn_19);
        btn20=view.findViewById(R.id.btn_20);
        btn21=view.findViewById(R.id.btn_21);
        btn22=view.findViewById(R.id.btn_22);
        btn23=view.findViewById(R.id.btn_23);
        btn24=view.findViewById(R.id.btn_24);

        //获取下划线ID
        line1=view.findViewById( R.id.ul_1 );
        line2=view.findViewById( R.id.ul_2 );
        line3=view.findViewById( R.id.ul_3);
        line4=view.findViewById( R.id.ul_4 );
        line5=view.findViewById( R.id.ul_5 );
        line6=view.findViewById( R.id.ul_6 );
        line7=view.findViewById( R.id.ul_7 );
        line8=view.findViewById( R.id.ul_8);
        line9=view.findViewById( R.id.ul_9 );
        line10=view.findViewById( R.id.ul_10 );
        line11=view.findViewById( R.id.ul_11);
        line12=view.findViewById( R.id.ul_12 );
        line13=view.findViewById( R.id.ul_13 );
        line14=view.findViewById( R.id.ul_14 );
        line15=view.findViewById( R.id.ul_15 );
        line16=view.findViewById( R.id.ul_16 );
        line17=view.findViewById( R.id.ul_17 );
        line18=view.findViewById( R.id.ul_18);
        line19=view.findViewById( R.id.ul_19 );
        line20=view.findViewById( R.id.ul_20 );
        line21=view.findViewById( R.id.ul_21 );
        line22=view.findViewById( R.id.ul_22 );
        line23=view.findViewById( R.id.ul_23 );

        //给页面右上角显示日期的图标添加点击事件
        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCalendarView.isYearSelectLayoutVisible()) {
                    mCalendarView.closeYearSelectLayout();
                    Toast.makeText( activity,"切换到月视图",Toast.LENGTH_SHORT ).show();
                }
                mCalendarView.scrollToCurrent();
            }
        });



        //给日历时间段按钮添加聚焦事件
        listener1=new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    switch (v.getId()){
                        case R.id.btn_1:    btn1.setText("+新建日程");mHour=0;return;
                        case R.id.btn_2:    btn2.setText("+新建日程");mHour=1;return;
                        case R.id.btn_3:    btn3.setText("+新建日程");mHour=2;return;
                        case R.id.btn_4:    btn4.setText("+新建日程");mHour=3;return;
                        case R.id.btn_5:    btn5.setText("+新建日程");mHour=4;return;
                        case R.id.btn_6:    btn6.setText("+新建日程");mHour=5;return;
                        case R.id.btn_7:    btn7.setText("+新建日程");mHour=6;return;
                        case R.id.btn_8:    btn8.setText("+新建日程");mHour=7;return;
                        case R.id.btn_9:    btn9.setText("+新建日程");mHour=8;return;
                        case R.id.btn_10:    btn10.setText("+新建日程");mHour=9;return;
                        case R.id.btn_11:    btn11.setText("+新建日程");mHour=10;return;
                        case R.id.btn_12:    btn12.setText("+新建日程");mHour=11;return;
                        case R.id.btn_13:    btn13.setText("+新建日程");mHour=12;return;
                        case R.id.btn_14:    btn14.setText("+新建日程");mHour=13;return;
                        case R.id.btn_15:    btn15.setText("+新建日程");mHour=14;return;
                        case R.id.btn_16:    btn16.setText("+新建日程");mHour=15;return;
                        case R.id.btn_17:    btn17.setText("+新建日程");mHour=16;return;
                        case R.id.btn_18:    btn18.setText("+新建日程");mHour=17;return;
                        case R.id.btn_19:    btn19.setText("+新建日程");mHour=18;return;
                        case R.id.btn_20:    btn20.setText("+新建日程");mHour=19;return;
                        case R.id.btn_21:    btn21.setText("+新建日程");mHour=20;return;
                        case R.id.btn_22:    btn22.setText("+新建日程");mHour=21;return;
                        case R.id.btn_23:    btn23.setText("+新建日程");mHour=22;return;
                        case R.id.btn_24:    btn24.setText("+新建日程");mHour=23;return;
                    }
                }
                else {
                    switch (v.getId()){
                        case R.id.btn_1:    if(isAdd( btn1 )) btn1.setText(null);return;
                        case R.id.btn_2:    if(isAdd( btn2)) btn2.setText(null);return;
                        case R.id.btn_3:    if(isAdd( btn3 )) btn3.setText(null);return;
                        case R.id.btn_4:    if(isAdd( btn4 )) btn4.setText(null);return;
                        case R.id.btn_5:    if(isAdd( btn5 )) btn5.setText(null);return;
                        case R.id.btn_6:    if(isAdd( btn6 )) btn6.setText(null);return;
                        case R.id.btn_7:    if(isAdd( btn7 )) btn7.setText(null);return;
                        case R.id.btn_8:    if(isAdd( btn8 )) btn8.setText(null);return;
                        case R.id.btn_9:    if(isAdd( btn9 )) btn9.setText(null);return;
                        case R.id.btn_10:   if(isAdd( btn10 )) btn10.setText(null);return;
                        case R.id.btn_11:   if(isAdd( btn11 )) btn11.setText(null);return;
                        case R.id.btn_12:   if(isAdd( btn12 ))  btn12.setText(null);return;
                        case R.id.btn_13:   if(isAdd( btn13 )) btn13.setText(null);return;
                        case R.id.btn_14:   if(isAdd( btn14 ))  btn14.setText(null);return;
                        case R.id.btn_15:   if(isAdd( btn15 )) btn15.setText(null);return;
                        case R.id.btn_16:   if(isAdd( btn16 )) btn16.setText(null);return;
                        case R.id.btn_17:   if(isAdd( btn17 )) btn17.setText(null);return;
                        case R.id.btn_18:    if(isAdd( btn18 )) btn18.setText(null);return;
                        case R.id.btn_19:    if(isAdd( btn19 )) btn19.setText(null);return;
                        case R.id.btn_20:    if(isAdd( btn20 )) btn20.setText(null);return;
                        case R.id.btn_21:    if(isAdd( btn21 )) btn21.setText(null);return;
                        case R.id.btn_22:   if(isAdd( btn22 ))  btn22.setText(null);return;
                        case R.id.btn_23:    if(isAdd( btn23 )) btn23.setText(null);return;
                        case R.id.btn_24:    if(isAdd( btn24 )) btn24.setText(null);return;
                    }
                }
            }
        };

        //给按钮添加聚焦事件监视器
        btn1.setOnFocusChangeListener(listener1);
        btn2.setOnFocusChangeListener(listener1);
        btn3.setOnFocusChangeListener(listener1);
        btn4.setOnFocusChangeListener(listener1);
        btn5.setOnFocusChangeListener(listener1);
        btn6.setOnFocusChangeListener(listener1);
        btn7.setOnFocusChangeListener(listener1);
        btn8.setOnFocusChangeListener(listener1);
        btn9.setOnFocusChangeListener(listener1);
        btn10.setOnFocusChangeListener(listener1);
        btn11.setOnFocusChangeListener(listener1);
        btn12.setOnFocusChangeListener(listener1);
        btn13.setOnFocusChangeListener(listener1);
        btn14.setOnFocusChangeListener(listener1);
        btn15.setOnFocusChangeListener(listener1);
        btn16.setOnFocusChangeListener(listener1);
        btn17.setOnFocusChangeListener(listener1);
        btn18.setOnFocusChangeListener(listener1);
        btn19.setOnFocusChangeListener(listener1);
        btn20.setOnFocusChangeListener(listener1);
        btn21.setOnFocusChangeListener(listener1);
        btn22.setOnFocusChangeListener(listener1);
        btn23.setOnFocusChangeListener(listener1);
        btn24.setOnFocusChangeListener(listener1);


        //给日历时间段按钮添加点击事件
        listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn=(Button) v;
                //判断该按钮是否已有事项
                if(isAdd( btn )){//未建事项，则进入Add_General_Activity
                    Intent intent=new Intent(activity,Add_General_Activity.class);
                    Bundle bundle=new Bundle();
                    //将年、月、日、开始时间存入Bundle
                    bundle.putInt("year",mYear);
                    bundle.putInt("month",mMonth);
                    bundle.putInt("day",mDay);
                    bundle.putInt("hour",mHour);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
                }

                else{//已建事项，则进入Delete_General_Activity
                    //获取该已建事项的时间段
                    first=rangFirst( btn );
                    last=rangeLast( btn );
                    String id=mainActivity.get_Schedule_ID(date,""+first,""+last);
                    String Place=mainActivity.get_Place_In_Schedule(id);
                    String Content=mainActivity.get_Content_In_Schedule(id);
                    Intent intent=new Intent(activity,Delete_General_Activity.class);
                    Bundle bundle=new Bundle();
                    //将年、月、日、开始时间、结束时间、事项标题、地点和详细信息存入Bundle
                    bundle.putInt("year1",mYear);
                    bundle.putInt("month1",mMonth);
                    bundle.putInt("day1",mDay);
                    bundle.putInt("begin",first);
                    bundle.putInt("end",last);
                    bundle.putString( "id",btn.getText().toString() );
                    bundle.putString("place",Place);
                    bundle.putString("content",Content);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
                }
            }
        };

        //给按钮添加点击事件监视器
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btn10.setOnClickListener(listener);
        btn11.setOnClickListener(listener);
        btn12.setOnClickListener(listener);
        btn13.setOnClickListener(listener);
        btn14.setOnClickListener(listener);
        btn15.setOnClickListener(listener);
        btn16.setOnClickListener(listener);
        btn17.setOnClickListener(listener);
        btn18.setOnClickListener(listener);
        btn19.setOnClickListener(listener);
        btn20.setOnClickListener(listener);
        btn21.setOnClickListener(listener);
        btn22.setOnClickListener(listener);
        btn23.setOnClickListener(listener);
        btn24.setOnClickListener(listener);

        mCalendarView.setSchemeDate(schemes);
        initView();
        return view;
    }




    @SuppressLint("SetTextI18n")
    protected void initView() {
        //初始化页面日期栏信息
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        //左上角月份显示图标点击事件
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout( mYear );
                }
                mCalendarView.showYearSelectLayout( mYear );
                mTextLunar.setVisibility( View.GONE );
                mTextYear.setVisibility( View.GONE );
                mTextMonthDay.setText( String.valueOf( mYear ) );
                if(!mCalendarView.isYearSelectLayoutVisible()){
                    Toast.makeText( activity, "切换到年视图", Toast.LENGTH_SHORT ).show();
                }
            }
        });

        //初始化页面
        //获取当天所有事项的ID
        List<String> list = new ArrayList<String>();


    }

//    protected void initData() {
//
//        int year = mCalendarView.getCurYear();
//        int month = mCalendarView.getCurMonth();
//
//        schemes.add(getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        schemes.add(getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        schemes.add(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        schemes.add(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        schemes.add(getSchemeCalendar(year, month, 14, 0xFFedc56d, "阅"));
//        schemes.add(getSchemeCalendar(year, month, 15, 0xFFaacc44, "影"));
//        schemes.add(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        schemes.add(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//
//        mCalendarView.setSchemeDate(schemes);
//    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color,String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme( text );
//        calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, null);
//        calendar.addScheme(0xFF008800, null);
        return calendar;
    }

    //年份改变事件
    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }


    //从栈顶Activity返回到Add_General_activity
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){//从Add_General_activity回调

            if(resultCode==0){
                //初始化时间表
                List<String> list = new ArrayList<String>();
                //刷新时间表
                deleteView();
                refreshView(date,list);
                Toast.makeText( activity,"关闭",Toast.LENGTH_SHORT).show();
            }
            //新建事项
            if(resultCode==-1){
                new Bundle();
                Bundle bundle1=data.getExtras();
                title=bundle1.getString("title");
                place=bundle1.getString("place");
                content=bundle1.getString("content");
                item=bundle1.getInt("scheme");
                start=bundle1.getInt("begin");
                end=bundle1.getInt("end");

                addGeneral(title,start,end,item);
                addMark();
                //将新的日程添加到数据库
                String id=getNewId();
                mainActivity.Insert_Schedule(id,date,""+start,""+end,title,place,content,""+item);
                Toast.makeText( activity,"建立成功",Toast.LENGTH_SHORT).show();
            }}

        if(requestCode==1){//从Delete_General_activity回调
            //删除日程
            if(resultCode==1){
                //将该日程从数据库删除
                mainActivity.DELETE_Schedule(date,""+first,""+last);
                //获取当天所有事项的ID
                List<String> list = new ArrayList<String>();
                //刷新时间表
                deleteView();
                refreshView(date,list);
                deleteMark();
                Toast.makeText( activity,"删除成功",Toast.LENGTH_SHORT).show();
            }
            //更新日程
            if(resultCode==-1){
                String Title,Place,Content;
                new Bundle();
                Bundle bundle2=data.getExtras();
                Title=bundle2.getString( "UpdateTitle" );
                Place=bundle2.getString( "UpdatePlace" );
                Content=bundle2.getString( "UpdateContent" );
                //在数据库上更新该日程事项内容
                String id=mainActivity.get_Schedule_ID(date,""+first,""+last);
                mainActivity.Update_Schedule(Title,Place,Content,id);
                //初始化时间表
                List<String> list = new ArrayList<String>();
                //刷新时间表
                deleteView();
                refreshView(date,list);

            }
            //仅返回
            if(resultCode==0){
                //初始化时间表
                List<String> list = new ArrayList<String>();
                //刷新时间表
                deleteView();
                refreshView(date,list);
            }
        }
    }
    //获取btn的引用
    private Button getBtn(int a) {
        switch (a) {
            case 0: return btn1;
            case 1: return btn2;
            case 2: return btn3;
            case 3: return btn4;
            case 4: return btn5;
            case 5: return btn6;
            case 6: return btn7;
            case 7: return btn8;
            case 8: return btn9;
            case 9: return btn10;
            case 10: return btn11;
            case 11: return btn12;
            case 12: return btn13;
            case 13: return btn14;
            case 14: return btn15;
            case 15: return btn16;
            case 16: return btn17;
            case 17: return btn18;
            case 18: return btn19;
            case 19: return btn20;
            case 20: return btn21;
            case 21: return btn22;
            case 22: return btn23;
            case 23: return btn24;
        }
        return null;
    }
    //获取View（下划线）的引用
    private View getView(int a) {
        switch (a) {
            case 0: return line1;
            case 1: return line2;
            case 2: return line3;
            case 3: return line4;
            case 4: return line5;
            case 5: return line6;
            case 6: return line7;
            case 7: return line8;
            case 8: return line9;
            case 9: return line10;
            case 10: return line11;
            case 11: return line12;
            case 12: return line13;
            case 13: return line14;
            case 14: return line15;
            case 15: return line16;
            case 16: return line17;
            case 17: return line18;
            case 18: return line19;
            case 19: return line20;
            case 20: return line21;
            case 21: return line22;
            case 22: return line23;

        }
        return null;
    }

    //判断该按钮显示的文字是否为"+新建日程"
    public boolean isAdd(Button btn){
        return btn.getText().toString().equals( "+新建日程" );
    }

    //获取该按钮显示的开始时间
    public int rangFirst(Button btn){
        switch (btn.getId()){
            case R.id.btn_1:    return 0;
            case R.id.btn_2:   return 1;
            case R.id.btn_3:    return 2;
            case R.id.btn_4:    return 3;
            case R.id.btn_5:    return 4;
            case R.id.btn_6:    return 5;
            case R.id.btn_7:    return 6;
            case R.id.btn_8:    return 7;
            case R.id.btn_9:    return 8;
            case R.id.btn_10:   return 9;
            case R.id.btn_11:   return 10;
            case R.id.btn_12:   return 11;
            case R.id.btn_13:   return 12;
            case R.id.btn_14:   return 13;
            case R.id.btn_15:   return 14;
            case R.id.btn_16:   return 15;
            case R.id.btn_17:   return 16;
            case R.id.btn_18:    return 17;
            case R.id.btn_19:    return 18;
            case R.id.btn_20:    return 19;
            case R.id.btn_21:    return 20;
            case R.id.btn_22:   return 21;
            case R.id.btn_23:    return 22;
            case R.id.btn_24:    return 23;
        }
        return 0;
    }

    //获取该按钮的结束时间
    public int rangeLast(Button btn){
        int first=rangFirst( btn );
        int last=first+1;
        while(!getBtn( last ).isEnabled()){
            last++;
        }

        return last;
    }

    //初始化页面添加所有日期标记
    private void  addAllMark(){
        int year,month,day;
        String[] strs=new  String[3];
        List<String>list=new ArrayList<String>();
        list=mainActivity. Find_All_Date();
        Iterator iterator=list.iterator();
        if(list.isEmpty()){
            return;
        }
        while (iterator.hasNext()){
            strs=iterator.next().toString().split("-");
            year=Integer.parseInt(strs[0]);
            month=Integer.parseInt(strs[1]);
            day=Integer.parseInt(strs[2]);
            schemes.add(getSchemeCalendar(year ,month ,day , 0xFFFF7F00, " "));
        }
        mCalendarView.setSchemeDate(schemes);
    }
    //为未添加标记的新建事项增添标记
    private void addMark() {
        Calendar[] a=new Calendar[50];
        for(int j=0;j<schemes.size();j++){
            a[j]=schemes.get(j);
        }

        if(schemes.isEmpty()){
            schemes.add(getSchemeCalendar(mYear, mMonth, mDay, 0xFFFF7F00," "));
            mCalendarView.setSchemeDate(schemes);
            return;
        }

        for(int i=0;i<schemes.size();i++){
            boolean x=(a[i].getDay()==mDay)&&(a[i].getMonth()==mMonth)&&(a[i].getYear()==mYear);
            if((a[i].getDay()==mDay)&&(a[i].getMonth()==mMonth)&&(a[i].getYear()==mYear))
            {return;}
        }
        schemes.add(getSchemeCalendar(mYear, mMonth, mDay, 0xFFFF7F00," "));
        mCalendarView.setSchemeDate(schemes);
    }

    //事项消失标记消失
    protected void deleteMark() {
        List<String>list=new ArrayList<String>();
        list=mainActivity.find_ID_By_date(date);
        if(list.size()==0){
            schemes.remove(getSchemeCalendar(mYear, mMonth, mDay, 0xFFFF7F00," "));
            mCalendarView.setSchemeDate(schemes);
        }

    }

    //建立新日程时判断上下是否存在已有日程，设置相应下划线
    private  void setUnderLine(int begin,int finish){
        Button bt1,bt2;
        bt1=getBtn( begin -1);
        bt2=getBtn( finish);
        //在该日程的上部分是否存在旧事项
        if(!bt1.isEnabled()){
            getView( begin-1 ).setBackgroundResource( R.drawable.bg_underline1 );
        }
        else if((!bt1.getText().toString().equals( null ))&&(!isAdd( bt1 ))) {
            getView( begin-1 ).setBackgroundResource( R.drawable.bg_underline1 );
        }
        //在该日程的下部分是否存在旧事项
        if((!bt2.getText().toString().equals( null ))&&(!isAdd( bt2 ))){
            getView( finish-1 ).setBackgroundResource( R.drawable.bg_underline1 );
        }
    }


    //删除日程时判断上下是否存在已有日程，删除相应下划线
    private void deleteUnderLine(){
        Button bt1,bt2;
        bt1=getBtn( first -1);
        bt2=getBtn( last );
        if(!bt1.isEnabled()){
            getView( first-1 ).setBackgroundResource( R.drawable.bg_underline );
        }
        else if((!bt1.getText().toString().equals( null ))&&(!isAdd( bt1 ))) {
            getView( first-1 ).setBackgroundResource( R.drawable.bg_underline );
        }

        if((!bt2.getText().toString().equals( null ))&&(!isAdd( bt2 ))){
            getView( last-1 ).setBackgroundResource( R.drawable.bg_underline );
        }
    }

    public void setBgColor_head(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_head);break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_head);break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_head);break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_head);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_head);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_head);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_head);break;
        }
    }

    public void setBgColor_rang(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_rang );break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_rang );break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_rang );break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_rang);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_rang);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_rang);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_rang );break;
        }
    }

    public void setBgColor_tail(View view,int i){
        switch (i){
            case 0: view.setBackgroundResource( R.drawable.bg_red_tail);break;
            case 1: view.setBackgroundResource( R.drawable.bg_yellow_tail);break;
            case 2: view.setBackgroundResource( R.drawable.bg_orange_tail);break;
            case 3: view.setBackgroundResource( R.drawable.bg_green_tail);break;
            case 4: view.setBackgroundResource( R.drawable.bg_blue_tail);break;
            case 5: view.setBackgroundResource( R.drawable.bg_purper_tail);break;
            case 6: view.setBackgroundResource( R.drawable.bg_grey_tail);break;
        }
    }

    //建立日程
    public void addGeneral(String title,int start,int end,int item){
        //将建立好的事项在btn上显示
        getBtn(start).setText( title );
        setBgColor_head( getBtn( start ),item );
        setBgColor_tail( getBtn( end-1 ),item );
        if(start==end-1){

        }
       else{
            getBtn( end-1 ).setEnabled( false );
        }
        //给btn设置背景颜色
        for(int i=start+1;i<end-1;i++){
            setBgColor_rang( getBtn( i ),item );
            getBtn( i ).setEnabled( false );
        }
        //若事项包含多个按钮，使内部下划线跟按钮颜色同步
        for(int i=start;i<end-1;i++)
        {
            setBgColor_rang( getView( i ),item );
        }
        //使显示文字的按钮失去聚焦功能
        getBtn( start ).setFocusable( false );
        //设置下划线

        setUnderLine(start,end);
        //在日期上添加标记

    }

    //删除日程
    public void deleteGeneral(int first,int last){
        getBtn( first ).setText( null );
        getBtn( first ).setFocusableInTouchMode( true );
        getBtn( first ).setFocusable( true );
        //恢复按钮的点击功能
        for(int i=first;i<last;i++){
            getBtn( i ).setEnabled( true );
            getBtn( i ).setBackgroundResource( R.drawable.bg_btn );
        }
        //恢复下划线的颜色
        for(int i=first;i<last-1;i++){
            getView( i ).setBackgroundResource( R.drawable.bg_underline );
        }
        //删除下划线
        deleteUnderLine();
        //删除日期标记
    }

    //日期改变响应事件
    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        //获取被选中的日期：年、月、日
        mYear = calendar.getYear();
        mMonth=calendar.getMonth();
        mDay=calendar.getDay();

        //连接数据库的接口//
        //初始化与数据库时间的模糊匹配比较的时间date
        date = mYear+"-"+mMonth+"-"+mDay;//之所以加上“-”是因为在数据库里面时间日期之间是用“-"连接的

        if(ifFirstIn){
            addAllMark();
            ifFirstIn=false;
        }
        //获取当天所有事项的ID
        List<String> list = new ArrayList<String>();

        //刷新时间表
        deleteView();
        refreshView(date,list);
        if(isClick){

        }
    }

    //清空时间表
    public  void deleteView(){

        //恢复按钮的点击功能
        for(int i=0;i<24;i++){
            getBtn( i ).setEnabled(false );
            getBtn( i ).setEnabled( true );
            getBtn( i ).setFocusableInTouchMode( true );
            getBtn(i).clearFocus();
            getBtn(i).setText(null);
            getBtn( i ).setBackgroundResource( R.drawable.bg_btn );
        }
        //恢复下划线的颜色
        for(int i=0;i<23;i++){
            getView( i ).setBackgroundResource( R.drawable.bg_underline );
        }
    }
    //刷新时间表
    public void refreshView(String date,List<String> l){
        String id,title;
        int start,end,color;
        l=mainActivity.find_ID_By_date(date);
        Iterator iterator=l.iterator();

        while (iterator.hasNext()){
            id=iterator.next().toString();
            title=mainActivity.get_Title_In_Schedule(id);
            Log.d("title",title);
            start=mainActivity.get_StartTime_In_Schedule(id);
            end=mainActivity.get_EndTime_In_Schedule(id);
            color=mainActivity.get_Color_In_Schedule(id);
            addGeneral(title,start,end,color);
        }


    }
    //获取一个不重复的新id
    public String getNewId(){
        List<String>list=new ArrayList<String>();
        list=mainActivity.find_ID_By_date(date);
        int[] a=new int[24];
        a=getOldID();
        if(list.isEmpty()) return date+"-"+1;
        else{
            for(int i=1;i<=24;i++){
                if(!isOldID(a,i)) return date+"-"+i;
            }
        }
        return null;
    }

    //获取该date已有的id值
    private int[] getOldID(){
        List<String>list=new ArrayList<String>();
        list= mainActivity.find_ID_By_date(date);
        int[] a=new int[24];
        int i=0;
        Iterator iterator=list.iterator();
        while (iterator.hasNext()){
            a[i]=Integer.parseInt(getX(iterator.next().toString()));
            i++;
        }
        return a;
    }
    //获取id（mYear-mMonth-mDay-x）最后一项x的值
    public String getX(String id){
        String[] strings=new String[4];
        strings=id.split("-");
        return strings[3];
    }

    //判断该id是否已经存在
    private  boolean isOldID(int[] a,int i){
        for(int j=0;j<a.length;j++){
            if(a[j]==i) return true;
        }
        return false;
    }
}
