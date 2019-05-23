package com.zhhl.qingbao.adapter;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhhl.qingbao.R;
import com.zhhl.qingbao.app.GlideApp;
import com.zhhl.qingbao.data.HistoryData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miao on 2019/1/7.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> implements View.OnClickListener {


    private int openId = -1;

    private ArrayList<HistoryData.ObjBean> data = new ArrayList<>();

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder vh, int i) {
        HistoryData.ObjBean item = data.get(i);
        vh.xm.setText(item.getXm() + " (" + item.getXb() + ")");
        vh.sfzh.setText(item.getSfzh());
        vh.csmc.setText(item.getCsmc());
        vh.date.setText(item.getCjsj());
        vh.shiyou.setText(item.getLjsy());
        vh.show.setTag(i);
        vh.show.setOnClickListener(this);
        vh.mHidden.setTag(i);
        vh.mHidden.setOnClickListener(this);


        if (openId == i) {
            vh.show.setVisibility(View.INVISIBLE);
            vh.mHidden.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(item.getTxr())) {
                vh.sxrItem.setVisibility(View.GONE);
            } else {
                vh.sxrItem.setVisibility(View.VISIBLE);
                vh.sxrName.setText(item.getTxr());
            }
            vh.mjxx.setVisibility(View.VISIBLE);
            vh.mjName1.setText(item.getMjxm());
            vh.mjName2.setText(item.getTxmjxm());
            vh.dwbm.setText(item.getMjssbm());
            vh.code.setText(item.getMjjh());
            vh.code2.setText(item.getTxmjjh());
            vh.phone.setText(item.getMjdh());
            vh.phone2.setText(item.getTxmjdh());

            vh.img0.setVisibility(View.VISIBLE);
            GlideApp.with(vh.imageView0).load(item.getZpRy()).into(vh.imageView0);
            GlideApp.with(vh.imageView1).load(item.getZpXdwp()).into(vh.imageView1);
            GlideApp.with(vh.imageView2).load(item.getZpXd()).into(vh.imageView2);
            GlideApp.with(vh.imageView3).load(item.getZpCl()).into(vh.imageView3);
            if (!TextUtils.isEmpty(item.getZpTxrsfz()) || !TextUtils.isEmpty(item.getZpDemp1()) || !TextUtils.isEmpty(item.getZpDemp2()) || !TextUtils.isEmpty(item.getZpDemp3())) {
                vh.img1.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(item.getZpTxrsfz())) {
                    vh.imageView4.setVisibility(View.INVISIBLE);
                } else {
                    vh.imageView4.setVisibility(View.VISIBLE);
                    GlideApp.with(vh.imageView4).load(item.getZpTxrsfz()).into(vh.imageView4);
                }

                if (TextUtils.isEmpty(item.getZpDemp1())) {
                    vh.imageView5.setVisibility(View.INVISIBLE);
                } else {
                    vh.imageView5.setVisibility(View.VISIBLE);
                    GlideApp.with(vh.imageView5).load(item.getZpDemp1()).into(vh.imageView5);
                }
                if (TextUtils.isEmpty(item.getZpDemp2())) {
                    vh.imageView6.setVisibility(View.INVISIBLE);
                } else {
                    vh.imageView6.setVisibility(View.VISIBLE);
                    GlideApp.with(vh.imageView6).load(item.getZpDemp2()).into(vh.imageView6);
                }
                if (TextUtils.isEmpty(item.getZpDemp3())) {
                    vh.imageView7.setVisibility(View.INVISIBLE);
                } else {
                    vh.imageView7.setVisibility(View.VISIBLE);
                    GlideApp.with(vh.imageView7).load(item.getZpDemp3()).into(vh.imageView7);
                }


            } else {
                vh.img1.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(item.getZpDemp4()) || !TextUtils.isEmpty(item.getZpDemp5())) {
                vh.img2.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(item.getZpDemp4())) {
                    vh.imageView8.setVisibility(View.INVISIBLE);
                } else {
                    vh.imageView8.setVisibility(View.VISIBLE);
                    GlideApp.with(vh.imageView8).load(item.getZpDemp4()).into(vh.imageView8);
                }
                if (TextUtils.isEmpty(item.getZpDemp5())) {
                    vh.imageView9.setVisibility(View.INVISIBLE);
                } else {
                    vh.imageView9.setVisibility(View.VISIBLE);
                    GlideApp.with(vh.imageView9).load(item.getZpDemp5()).into(vh.imageView9);
                }
            } else {
                vh.img2.setVisibility(View.GONE);
            }

        } else {
            vh.mHidden.setVisibility(View.GONE);
            vh.show.setVisibility(View.VISIBLE);
            vh.sxrItem.setVisibility(View.GONE);
            vh.mjxx.setVisibility(View.GONE);
            vh.img0.setVisibility(View.GONE);
            vh.img1.setVisibility(View.GONE);
            vh.img2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View showOrHidden) {
        int tag = (int) showOrHidden.getTag();

        if (showOrHidden.getId() == R.id.show) {
            if (openId == -1) {
                openId = tag;
                notifyItemChanged(openId);
            } else {
                notifyItemChanged(openId);
                openId = tag;
                notifyItemChanged(openId);
            }
        } else if (showOrHidden.getId() == R.id.mHidden) {
            openId = -1;
            notifyItemChanged(tag);
        }

    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.xm)
        TextView xm;
        @BindView(R.id.sfzh)
        TextView sfzh;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.textView7)
        TextView textView7;
        @BindView(R.id.shiyou)
        TextView shiyou;
        @BindView(R.id.csmc)
        TextView csmc;
        @BindView(R.id.show)
        TextView show;
        @BindView(R.id.sx)
        TextView sx;
        @BindView(R.id.sxrName)
        TextView sxrName;
        @BindView(R.id.sxrItem)
        ConstraintLayout sxrItem;
        @BindView(R.id.code2)
        TextView code2;
        @BindView(R.id.mj_name_2)
        TextView mjName2;
        @BindView(R.id.phone2)
        TextView phone2;
        @BindView(R.id.mj)
        TextView mj;
        @BindView(R.id.mj_name_1)
        TextView mjName1;
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.dwbm)
        TextView dwbm;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.textView21)
        TextView textView21;
        @BindView(R.id.mjxx)
        ConstraintLayout mjxx;
        @BindView(R.id.imageView0)
        ImageView imageView0;
        @BindView(R.id.imageView1)
        ImageView imageView1;
        @BindView(R.id.imageView2)
        ImageView imageView2;
        @BindView(R.id.imageView3)
        ImageView imageView3;
        @BindView(R.id.img0)
        LinearLayout img0;
        @BindView(R.id.imageView4)
        ImageView imageView4;
        @BindView(R.id.imageView5)
        ImageView imageView5;
        @BindView(R.id.imageView6)
        ImageView imageView6;
        @BindView(R.id.imageView7)
        ImageView imageView7;
        @BindView(R.id.img1)
        LinearLayout img1;
        @BindView(R.id.imageView8)
        ImageView imageView8;
        @BindView(R.id.imageView9)
        ImageView imageView9;
        @BindView(R.id.img_2)
        LinearLayout img2;
        @BindView(R.id.mHidden)
        TextView mHidden;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(List<HistoryData.ObjBean> data) {
        this.data.clear();
        Collections.sort(data);
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
