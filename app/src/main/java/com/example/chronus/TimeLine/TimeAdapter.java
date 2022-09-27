package com.example.chronus.TimeLine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chronus.MainActivity;
import com.example.chronus.R;

import java.util.List;


public class TimeAdapter extends RecyclerView.Adapter{

    private MainActivity activity;
    private List<TimeData> data;

    public TimeAdapter(MainActivity mainActivity, List<TimeData> list) {
        this.activity = mainActivity;
        this.data = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_timeline, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setPosition(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txt_date_title;
        private TextView txtDate;
        private TextView text_cal_title;
        private RelativeLayout rlTitle;
        private View vLine;
        private int position;
        private TimeData timeData;
        private ImageView im_pod ;
        private TextView underline_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            rlTitle = (RelativeLayout) itemView.findViewById(R.id.rl_title);
            vLine = itemView.findViewById(R.id.v_line);
            txtTime = (TextView) itemView.findViewById(R.id.txt_date_time);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date_time1);
            txt_date_title = (TextView) itemView.findViewById(R.id.txt_date_title);
            text_cal_title = (TextView) itemView.findViewById(R.id.txt_date_title1);
            im_pod = itemView.findViewById(R.id.im_pod);
            underline_tv = itemView.findViewById(R.id.underline_tv);
        }

        public void setPosition(int position) {
            this.position = position;
            timeData = data.get(position);
            //时间轴竖线的layoutParams,用来动态的添加竖线
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vLine.getLayoutParams();
            //获取时间轴显示标题的位置数据
            RelativeLayout.LayoutParams layoutParams_reminder_title = (RelativeLayout.LayoutParams) txt_date_title.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams_calender_title = (RelativeLayout.LayoutParams) text_cal_title.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams_underline = (RelativeLayout.LayoutParams) underline_tv.getLayoutParams();



            if (position == 0) {
                txt_date_title.setBackgroundResource(R.drawable.message_sys_bg);
                text_cal_title.setBackgroundResource(R.drawable.message_shadow_bg);

                layoutParams.setMargins(0, DensityUtil.dip2px(vLine.getContext(), 25), 0, 0);

                im_pod.setVisibility(View.INVISIBLE);
                rlTitle.setVisibility(View.VISIBLE);
                txtTime.setText(TimeFormat.toTime(timeData.getPostDate()));
                txtDate.setText(TimeFormat.toDate(timeData.getPostDate()));
                //代码设置是px
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, R.id.rl_title);
                layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.txt_date_title);
            } else if (position < data.size() - 1) {
                if (timeData.getPostDate().equals(data.get(position - 1).getPostDate())) {
                        rlTitle.setVisibility(View.GONE);
                        //layoutParams.setMargins(0, DensityUtil.dip2px(vLine.getContext(), 15), 0, 0);
                        txt_date_title.setBackgroundResource(R.drawable.message_sys_bg);
                        text_cal_title.setBackgroundResource(R.drawable.message_shadow_bg);
                        layoutParams_reminder_title.setMargins(layoutParams_reminder_title.getMarginStart(), DensityUtil.dip2px(txt_date_title.getContext(), 10),0,0);
                        layoutParams_calender_title.setMargins(0, DensityUtil.dip2px(text_cal_title.getContext(), 10),layoutParams_calender_title.getMarginEnd(),0);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.txt_date_title);
                        layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.txt_date_title);
                } else {

                    txt_date_title.setBackgroundResource(R.drawable.message_sys_bg);
                    text_cal_title.setBackgroundResource(R.drawable.message_shadow_bg);

                    layoutParams.setMargins(0, DensityUtil.dip2px(vLine.getContext(), 0), 0, 0);
                    rlTitle.setVisibility(View.VISIBLE);
                    txtTime.setText(TimeFormat.toTime(timeData.getPostDate()));
                    txtDate.setText(null);
                    layoutParams.addRule(RelativeLayout.ALIGN_TOP, R.id.rl_title);
                    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.txt_date_title);
                }

            } else {
                if (!timeData.getPostDate().equals(data.get(position - 1).getPostDate())) {
                    txt_date_title.setBackgroundResource(R.drawable.message_sys_bg);
                    text_cal_title.setBackgroundResource(R.drawable.message_shadow_bg);
                    rlTitle.setVisibility(View.VISIBLE);
                    txtTime.setText(TimeFormat.toTime(timeData.getPostDate()));
                    txtDate.setText(null);
                    layoutParams.setMargins(0,0, 0,0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.rl_title);
                    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.txt_date_title);
                } else {
                    rlTitle.setVisibility(View.GONE);
                    txt_date_title.setBackgroundResource(R.drawable.message_sys_bg);
                    text_cal_title.setBackgroundResource(R.drawable.message_shadow_bg);
                    txtTime.setText(TimeFormat.toTime(timeData.getPostDate()));
                    txtDate.setText(null);
                    layoutParams.setMargins(0, DensityUtil.dip2px(vLine.getContext(), 0), 0, 0);
                    layoutParams_reminder_title.setMargins(layoutParams_reminder_title.getMarginStart(), DensityUtil.dip2px(txt_date_title.getContext(), 10),0,DensityUtil.dip2px(txt_date_title.getContext(),0));
                    layoutParams_calender_title.setMargins(0, DensityUtil.dip2px(text_cal_title.getContext(), 10),layoutParams_calender_title.getMarginEnd(),DensityUtil.dip2px(text_cal_title.getContext(), 0));
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.txt_date_title);
                    layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.txt_date_title);
                }
                //underline_tv.setVisibility(View.VISIBLE);
                //layoutParams_underline.setMargins(0,DensityUtil.dip2px(underline_tv.getContext(), 20),0,0);

            }


            vLine.setLayoutParams(layoutParams);


            if(isEmpty(timeData.getTitle()))
            {
                txt_date_title.setVisibility(View.INVISIBLE);
            }else{
                txt_date_title.setText(timeData.getTitle());
            }
            if(isEmpty(timeData.getDesc()))
            {
                text_cal_title.setVisibility(View.INVISIBLE);
            }else{
                text_cal_title.setText(timeData.getDesc());
            }
        }


    }
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

}
