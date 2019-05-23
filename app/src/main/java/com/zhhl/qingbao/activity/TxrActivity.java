package com.zhhl.qingbao.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xdja.watermarklibrary.WaterMarkUtils;
import com.zhhl.qingbao.R;
import com.zhhl.qingbao.data.UserInfo;
import com.zhhl.qingbao.app.App;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TxrActivity extends AppCompatActivity {

    //    @BindView(R.id.mUserList)
//    RecyclerView mUserList;
    @BindView(R.id.mName)
    EditText mName;
    @BindView(R.id.mCode)
    EditText mCode;
    @BindView(R.id.mPhone)
    EditText mPhone;
    @BindView(R.id.mSave)
    TextView mSave;
    @BindView(R.id.sName)
    TextView sName;
    @BindView(R.id.sCode)
    TextView sCode;
    @BindView(R.id.sPhone)
    TextView sPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txr);
        ButterKnife.bind(this);
        if (App.app().getUserInfo() != null)
            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);

        if (QingbaoActivity.aUserInfo != null) {
            mCode.setText(QingbaoActivity.aUserInfo.getSfzh());
            mPhone.setText(QingbaoActivity.aUserInfo.getPhone());
            mName.setText(QingbaoActivity.aUserInfo.getName());
        }

//        sName.setText(Html.fromHtml("民警姓名<font color= red>*</font>"));
//        sCode.setText(Html.fromHtml("警号<font color= red>*</font>"));
//        sPhone.setText(Html.fromHtml("手机号<font color= red>*</font>"));
//        mUserList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mUserList.setAdapter(mAdapter);
    }

    @OnClick(R.id.mSave)
    public void onSave() {
        if (TextUtils.isEmpty(mCode.getText()) || TextUtils.isEmpty(mName.getText()) || TextUtils.isEmpty(mPhone.getText())) {
            Toast.makeText(this, "请输入同行民警信息", Toast.LENGTH_SHORT).show();
            return;
        }
        QingbaoActivity.aUserInfo = new UserInfo(mName.getText().toString(), mPhone.getText().toString(), mCode.getText().toString());
        Toast text = Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT);
        text.setGravity(Gravity.CENTER, 0, 0);
        text.show();

        setResult(RESULT_OK, new Intent().putExtra("name", mName.getText().toString()));
        finish();
    }

}
