package com.zhhl.qingbao.adapter;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.zhhl.qingbao.R;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by miao on 2018/12/13.
 */
public class GridViewAdapter extends BaseAdapter<String, GridViewAdapter.GridImageViewHolder> {

    public GridViewAdapter(ArrayList<String> data) {
        super(data);
    }

    @Override
    protected GridImageViewHolder create(View view, int itemViewType) {
        return new GridImageViewHolder(view);
    }

    @Override
    protected void bindView(GridImageViewHolder vh, int position, String item) {
        switch (item) {
//            GlideApp.with(context).load(R.mipmap.ic_launcher).into(vh.imageView);
            case "":
                vh.imageView.setImageResource(R.drawable.cut);
                break;
            case "1":
                vh.imageView.setImageResource(R.drawable.ic_c1);
                break;
            case "2":
                vh.imageView.setImageResource(R.drawable.ic_c2);
                break;
            case "3":
                vh.imageView.setImageResource(R.drawable.ic_c3);
                break;
            case "4":
                vh.imageView.setImageResource(R.drawable.ic_c4);
                break;
            case "5":
                vh.imageView.setImageResource(R.drawable.ic_c5);
                break;
            default: {
//            GlideApp.with(context).load(new File(URI.create(item))).placeholder(R.mipmap.ic_launcher).into(vh.imageView);
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inSampleSize = 2;

                vh.imageView.setImageBitmap(BitmapFactory.decodeFile(new File(URI.create(item)).getAbsolutePath(), o));
            }
        }
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.item_img;
    }

    public void clear() {
        data.clear();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("");
        notifyDataSetChanged();
    }

    class GridImageViewHolder extends BaseAdapter.ViewHolder {
        ImageView imageView;

        public GridImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.src);
        }
    }

    public void setItem(int position, String item) {
        data.set(position, item);
        notifyDataSetChanged();
    }

    public void clearAll() {
        data.clear();
        notifyDataSetChanged();
    }

    public void add(String item) {
        data.add(item);
        notifyDataSetChanged();
    }

}
