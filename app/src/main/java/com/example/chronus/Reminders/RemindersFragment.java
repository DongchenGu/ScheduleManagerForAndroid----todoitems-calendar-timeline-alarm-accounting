package com.example.chronus.Reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemindersFragment extends Fragment {

    private View view;
    private static final String ARG_SHOW_TEXT = "text";
    private String mContentText;
    private TextView edit_tv;
    private TextView addlist_tv;
    private TextView delete_tv;
    private ImageView search_iv;
    private EditText search_et;
    private Integer[] imgIds = new Integer[]{R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3,
            R.mipmap.lise_icon4, R.mipmap.lise_icon5, R.mipmap.lise_icon6,R.mipmap.lise_icon1, R.mipmap.lise_icon2, R.mipmap.lise_icon3};

    private int choose_img = R.drawable.radio_unselected;
    private int inf = R.drawable.ino_img;
    private String[] but_name = new String[]{"今天","计划","全部","星标"};
    private String[] but_num = new String[]{"2","3","0","2"};
    public ListView listView;
    public ArrayList<Integer> selseted_num = new ArrayList<Integer>();
    private RelativeLayout.LayoutParams params_lv;//列表高度
    private int lastID = 0;
    private int Temp_Color;

    public RemindersFragment() {
        // Required empty public constructor
    }

    public static RemindersFragment newInstance(String param1){
        RemindersFragment fragment = new RemindersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mContentText = getArguments().getString(ARG_SHOW_TEXT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.layout_reminders,container, false);

        String agrs1="text";
        if (getArguments() != null){
            Bundle bundle = getArguments();
            agrs1 = bundle.getString(ARG_SHOW_TEXT);
        }

        init();
        return view;
    }
    public void getListView(){
        //列表获取数据
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < MainActivity.get_ListCount(); i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("list_choose",choose_img);
            showitem.put("list_img", MainActivity.Show_List_Color_By_ID(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_name", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_num", MainActivity.Show_List_number(MainActivity.ShowLineID_inList(i)));
            listitem.add(showitem);
        }
        SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.layout_listview_a, new String[]{"list_choose","list_img", "list_name", "list_num"},
                new int[]{R.id.choose_img,R.id.list_img, R.id.list_name, R.id.list_num});
        listView = (ListView) view.findViewById(R.id.reminder_list);
        listView.setAdapter(myAdapter);
        //设置列表大小
        RelativeLayout.LayoutParams params_set = params_lv;
        //params_set.height= (int) listView.getAdapter().getCount() * 50 + 5;
        params_set.height = listView.getAdapter().getCount() * (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics());
        listView.setLayoutParams(params_set);
    }
    public void init(){
        //初始化控件
        search_iv = view.findViewById(R.id.search_iv);
        search_et = view.findViewById(R.id.search_et);
        search_et.clearFocus();
        //列表获取数据
        final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < MainActivity.get_ListCount(); i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("list_choose",choose_img);
            showitem.put("list_img", MainActivity.Show_List_Color_By_ID(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_name", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(i)));
            showitem.put("list_num", MainActivity.Show_List_number(MainActivity.ShowLineID_inList(i)));
            listitem.add(showitem);
        }
        //用来给ID值递增
        final Integer List_ID_number = listitem.size();

        SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.layout_listview_a, new String[]{"list_choose","list_img", "list_name", "list_num"},
                new int[]{R.id.choose_img,R.id.list_img, R.id.list_name, R.id.list_num});
        listView = (ListView) view.findViewById(R.id.reminder_list);
        listView.setAdapter(myAdapter);
        //设置列表大小

        params_lv = (RelativeLayout.LayoutParams) listView.getLayoutParams();
        RelativeLayout.LayoutParams params_set =params_lv;
        params_set.height= (int) listView.getAdapter().getCount() * params_lv.height ;
        listView.setLayoutParams(params_set);
        onClickListView();
        setListener();
        //计算列表id
        lastID = lastID + MainActivity.get_ListCount();
    }
    private void setListener(){

        //编辑
        edit_tv = view.findViewById(R.id.edit_tv);//编辑 标签
        edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_tv.getText().equals("编辑")){
                    edit_tv.setText("完成");
                    delete_tv.setVisibility(View.VISIBLE);
                    edit_tv.getPaint().setFakeBoldText(true);



                    final List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < MainActivity.get_ListCount(); i++) {
                        Map<String, Object> showitem = new HashMap<String, Object>();
                        showitem.put("list_choose",choose_img);
                        showitem.put("list_img", MainActivity.Show_List_Color_By_ID(MainActivity.ShowLineID_inList(i)));
                        showitem.put("list_name", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(i)));
                        showitem.put("list_num", MainActivity.Show_List_number(MainActivity.ShowLineID_inList(i)));
                        showitem.put("inf", inf);
                        listitem.add(showitem);
                    }
                    //创建一个simpleAdapter
                    SimpleAdapter myAdapter = new SimpleAdapter(getContext(), listitem, R.layout.layout_listview, new String[]{"list_choose","list_img", "list_name", "list_num","inf"},
                            new int[]{R.id.choose_img,R.id.list_img, R.id.list_name, R.id.list_num,R.id.inf});
                    listView = (ListView) view.findViewById(R.id.reminder_list);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            mCallback.onEventsSelected(position);
                            if(edit_tv.getText().equals("完成")){
                                if(!selseted_num.contains(position)){
                                    selseted_num.add(position);//选中后把当前项加入到队列中
                                    ImageView choose_img = view.findViewById(R.id.choose_img);
                                    choose_img.setImageResource(R.drawable.delete);
                                    choose_img.setPadding(5,5,5,5);
                                    Log.d("删除队列",selseted_num.toString());
                                }else {
                                    selseted_num.remove(selseted_num.indexOf(position));
                                    //从队列中移除
                                    ImageView choose_img = view.findViewById(R.id.choose_img);
                                    choose_img.setImageResource(R.drawable.radio_unselected);
                                    choose_img.setPadding(0,0,0,0);
                                }
                            }
                        }
                    });
                }else{
                    edit_tv.setText("编辑");
                    delete_tv.setVisibility(View.INVISIBLE);

                    edit_tv.getPaint().setFakeBoldText(false);
                    selseted_num.clear();
                    onClickListView();
                    getListView();
                }
            }
        });
        //删除
        delete_tv = view.findViewById(R.id.delete_tv);
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除列表的数据库
                //询问确认对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog alertDialog = builder
                        .setTitle("系统提示")
                        .setMessage("你确定要删除列表吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int n) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int n) {
                                if(selseted_num.isEmpty()){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    AlertDialog alertDialog = builder
                                            .setTitle("系统提示")
                                            .setMessage("没有选择要删除的列表")
                                            .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int n) {

                                                }
                                            }).create();
                                    alertDialog.show();
                                }else {
                                    MediaPlayer mMediaPlayer;
                                    mMediaPlayer =  MediaPlayer.create(getContext(),R.raw.delete);
                                    mMediaPlayer.start();
                                    for(int i=(selseted_num.size()-1);i>=0;i--){
                                        int line = selseted_num.get(i);
                                        MainActivity.DELETE_LIST_By_ID(MainActivity.ShowLineID_inList(line));
                                        Log.d("Delete",String.valueOf(line));
                                    }
                                    edit_tv.setText("编辑");
                                    delete_tv.setVisibility(View.INVISIBLE);

                                    edit_tv.getPaint().setFakeBoldText(false);
                                    selseted_num.clear();
                                    onClickListView();
                                    getListView();

                                }

                            }
                        }).create();
                alertDialog.show();



            }
        });
        //添加列表
        addlist_tv = view.findViewById(R.id.addlist_tv);
        addlist_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出添加列表菜单或对话框，对话框里面有写列表名字，选择颜色。
                //final  EditText et = new EditText(getActivity());
                //final ImageView imageView = new ImageView(getActivity());

                //et.setText("提醒列表");
                //et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                //imageView.setImageResource(R.mipmap.lise_icon1);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("新建列表");

                final View add_view = LayoutInflater.from(getActivity()).inflate(R.layout.reminder_add_layout,null,false);
                builder.setView(add_view);
                final EditText et = add_view.findViewById(R.id.add_et);
                final ImageView imageView = add_view.findViewById(R.id.add_im);
                RadioGroup group = add_view.findViewById(R.id.add_rbg);
                Temp_Color = 0;
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Integer id_rb[] = new Integer[]{R.id.rb1,R.id.rb2,R.id.rb3,R.id.rb4,R.id.rb5,R.id.rb6};
                        for(int i = 0;i<id_rb.length;i++){
                            if(checkedId == id_rb[i]){
                                Temp_Color = i;
                                imageView.setImageResource(imgIds[i]);

                            }
                        }
                    }
                });
                        //builder.setView(imageView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MediaPlayer mMediaPlayer;
                        mMediaPlayer =  MediaPlayer.create(getContext(),R.raw.confirm_up);
                        mMediaPlayer.start();
                        //Integer list_id_number = List_ID_number+1;
                        Integer list_id_number = ++lastID;
                        //添加列表的数据库
                        MainActivity.INSERT_List(list_id_number.toString(),et.getText().toString(),imgIds[Temp_Color].toString(),"0" );
                        Toast.makeText(getActivity(), "成功新建列表 "+et.getText().toString(), Toast.LENGTH_LONG).show();
                        //列表刷新
                        getListView();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create();
                builder.show();
            }
        });
        // 列表项目长按
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //长按显示修改列表名称
//                //需要数据库同步
//                final  EditText et = new EditText(getActivity());
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("修改列表");
//                builder.setView(et);
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        //数据获取
//                        Toast.makeText(getActivity(), et.getText().toString(), Toast.LENGTH_LONG).show();
//                        //修改列表的数据库
//                        Toast.makeText(getActivity(), "成功修改列表 "+et.getText().toString(), Toast.LENGTH_SHORT).show();
//                        MediaPlayer mMediaPlayer;
//                        mMediaPlayer =  MediaPlayer.create(getContext(),R.raw.confirm_up);
//                        mMediaPlayer.start();
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(), "取消修改列表", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                builder.create();
//                builder.show();
//                return true;
//            }
//        });
        //搜索 区域被点击
        search_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_et.setText("");
                search_et.setTextColor(Color.parseColor("#000000"));
            }
        });
//        search_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                search_et.setText("");
//                search_et.setTextColor(Color.parseColor("#000000"));
//            }
//        });
//        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//
//                if (null != keyEvent && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()) {
//                    switch (keyEvent.getAction()) {
//                        case KeyEvent.ACTION_UP:
//                            //开始搜索
//                            Log.d("key","search");
//                            Intent intent = new Intent(getContext(), ReminderItemsActivity.class);
//                            startActivity(intent);
//                            return true;
//                        default:
//                            return true;
//                    }
//                }
//
//                return false;
//            }
//        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView tv, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    Log.d("tag:",search_et.getText().toString());
                    if(tv.getText()==null){
                        Toast.makeText(getActivity(), "未输入查询信息", Toast.LENGTH_LONG).show();
                    }else{
                        //在这里加入搜索数据库指令
                        //Intent intent = new Intent(getContext(), ReminderItemsActivity.class);
                        //intent.putExtra("Line", );
                        //intent.putExtra("ListId",);
                        //startActivity(intent);
                    }

                    return true;
                }
                return false;

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        //列表刷新 解决从事项列表添加事项后返回提醒列表时数量统计的刷新问题
        getListView();
    }



    OnTitleSelectedListener mCallback;
    public interface OnTitleSelectedListener{
        public void onEventsSelected(int position);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            mCallback = (OnTitleSelectedListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "必须实现OnTitleSelectedListener接口");
        }
    }
    public void onClickListView(){
        //列表中的项目被点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long l) {
                mCallback.onEventsSelected(position);
                edit_tv = view.findViewById(R.id.edit_tv);
                if(edit_tv.getText().equals("编辑")){
                    Intent intent = new Intent(getContext(), ReminderItemsActivity.class);
                    intent.putExtra("Line", MainActivity.Show_List_name(MainActivity.ShowLineID_inList(position)));
                    intent.putExtra("ListId",MainActivity.ShowLineID_inList(position));
                    TextView tv_back = view.findViewById(R.id.item_back);
                    startActivity(intent);
                }

            }
        });
    }

}
