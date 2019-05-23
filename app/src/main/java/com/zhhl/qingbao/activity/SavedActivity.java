package com.zhhl.qingbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;

import com.zhhl.qingbao.R;
import com.zhhl.qingbao.adapter.SavedAdapter;
import com.zhhl.qingbao.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class SavedActivity extends AppCompatActivity {

    @BindView(R.id.mSavedList)
    ListView mSavedList;
    private SavedAdapter mAdapter;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        ButterKnife.bind(this);
        mAdapter = new SavedAdapter(App.sdata);
        mSavedList.setAdapter(mAdapter);
        if (App.sdata.size() == 0) {
            if (dialog == null)
                dialog = new AlertDialog.Builder(this).setTitle("暂无数据").setCancelable(false).setMessage("页面将自动关闭").create();
            dialog.show();
            new Handler().postDelayed(() -> {
                dialog.dismiss();
                finish();
            }, 2000);
        }
    }

    @OnItemClick(R.id.mSavedList)
    void onItemClick(int i) {
        Intent intent = new Intent(this, QingbaoActivity.class);
        intent.putExtra("savedId", i);
        intent.putExtra("idPicture",App.sdata.get(i).getIdPicture());
        startActivity(intent);
        finish();
    }
}
