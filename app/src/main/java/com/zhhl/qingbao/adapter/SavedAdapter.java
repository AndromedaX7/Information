package com.zhhl.qingbao.adapter;

import android.view.View;
import android.widget.TextView;

import com.zhhl.qingbao.R;
import com.zhhl.qingbao.data.HistoryData;
import com.zhhl.qingbao.data.StaticBufferData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miao on 2019/1/7.
 */
public class SavedAdapter extends BaseAdapter<StaticBufferData, SavedAdapter.SavedViewHolder> {


    public SavedAdapter(ArrayList<StaticBufferData> data) {
        super(data);
    }

    @Override
    protected SavedViewHolder create(View view, int itemViewType) {
        return new SavedViewHolder(view);
    }

    @Override
    protected void bindView(SavedViewHolder vh, int position, StaticBufferData item) {
        vh.xm.setText(item.getXm() + " (" + item.getXb() + ")");
        vh.sfzh.setText(item.getSfzh());
        vh.flag.setText(item.isFlag() ? "是" : "否");
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.item_saved;
    }

    public class SavedViewHolder extends BaseAdapter.ViewHolder {
        @BindView(R.id.xm)
        TextView xm;
        @BindView(R.id.sfzh)
        TextView sfzh;

        @BindView(R.id.flag)
        TextView flag;


        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void setData(ArrayList<StaticBufferData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
}
