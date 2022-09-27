package com.example.chronus;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;


public class TomatoFragment extends Fragment {
    private View view;
    private static final String ARG_SHOW_TEXT = "text";
    private  String mContentText;
    private TextView textView;
    private TomatoView clockView;

    public TomatoFragment() {
        // Required empty public constructor
    }
    public static TomatoFragment newInstance(String param1){
        TomatoFragment fragment = new TomatoFragment();
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
        view = inflater.inflate(R.layout.layout_tomato,container, false);
        init();
        return view;
    }
    private void init(){
        clockView = view.findViewById(R.id.clockView);
        textView = view.findViewById(R.id.tv_start);
        final TextView tv_exp = view.findViewById(R.id.tv_exp);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textView.getText().toString().equals("开始专注")){
                    textView.setText("放弃番茄");
                    textView.setTextColor(Color.parseColor("#9FA0A1"));
                    tv_exp.setVisibility(View.INVISIBLE);
                    clockView.start();
                }else{
                    textView.setText("开始专注");
                    textView.setTextColor(Color.parseColor("#AAE3170D"));
                    tv_exp.setVisibility(View.VISIBLE);
                    clockView.stop();
                }

            }
        });
    }


}
