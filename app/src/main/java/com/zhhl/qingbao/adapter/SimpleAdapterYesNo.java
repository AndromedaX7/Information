package com.zhhl.qingbao.adapter;

import android.view.View;
import android.widget.TextView;

import com.zhhl.qingbao.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by miao on 2019/1/18.
 */
public class SimpleAdapterYesNo extends BaseAdapter<SimpleAdapterYesNo.Str, SimpleAdapterYesNo.YesNoVH> {


    public SimpleAdapterYesNo(ArrayList<SimpleAdapterYesNo.Str> data) {
        super(data);
    }

    @Override
    protected YesNoVH create(View view, int itemViewType) {
        return new YesNoVH(view);
    }

    @Override
    protected void bindView(YesNoVH vh, int position, SimpleAdapterYesNo.Str item) {
        vh.content.setText(item.getName());
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.item_text;
    }

    public class YesNoVH extends BaseAdapter.ViewHolder {

        @BindView(R.id.text1)
        TextView content;

        public YesNoVH(View view) {
            super(view);
        }
    }

    public static class Str {
        private String name;
        private boolean flag;

        public Str(String name, boolean flag) {
            this.name = name;
            this.flag = flag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
