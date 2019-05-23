package com.zhhl.qingbao.adapter;

import android.view.View;
import android.widget.TextView;

import com.zhhl.qingbao.data.DepData;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by miao on 2019/1/17.
 */
public class SimpleAdapter extends BaseAdapter<DepData.ObjBean, SimpleAdapter.DepVH> {


    public SimpleAdapter(ArrayList<DepData.ObjBean> data) {
        super(data);
    }

    @Override
    protected DepVH create(View view, int itemViewType) {
        return new DepVH(view);
    }

    @Override
    protected void bindView(DepVH vh, int position, DepData.ObjBean item) {
        vh.content.setText(item.getName());
    }

    public int indexOf(String name) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int getDefaultLayout() {
        return android.R.layout.simple_list_item_1;
    }

    public class DepVH extends BaseAdapter.ViewHolder {

        @BindView(android.R.id.text1)
        TextView content;

        public DepVH(View view) {
            super(view);
        }
    }
}
