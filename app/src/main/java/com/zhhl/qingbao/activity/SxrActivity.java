package com.zhhl.qingbao.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xdja.watermarklibrary.WaterMarkUtils;
import com.zhhl.qingbao.R;
import com.zhhl.qingbao.app.App;
import com.zhhl.qingbao.data.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SxrActivity extends AppCompatActivity {
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.sfzh)
    EditText sfzh;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.sName)
    TextView sName;
    @BindView(R.id.sCode)
    TextView sCode;
    @BindView(R.id.sPhone)
    TextView sPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sxr);
        ButterKnife.bind(this);
        if (App.app().getUserInfo() != null)
            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
//        sName.setText(Html.fromHtml("随行人姓名<font color= red>*</font>"));
//        sCode.setText(Html.fromHtml("身份证号<font color= red>*</font>"));
//        sPhone.setText(Html.fromHtml("手机号<font color= red>*</font>"));
        if (QingbaoActivity.txrUserInfo != null) {
            name.setText(QingbaoActivity.txrUserInfo.getName());
            phone.setText(QingbaoActivity.txrUserInfo.getPhone());
            sfzh.setText(QingbaoActivity.txrUserInfo.getSfzh());
        }
    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(sfzh.getText().toString())) {
            Toast.makeText(this, "请输入随行人员信息", Toast.LENGTH_SHORT).show();
            return;
        }
        QingbaoActivity.txrUserInfo = new UserInfo(name.getText().toString(), phone.getText().toString(), sfzh.getText().toString());


        setResult(RESULT_OK, new Intent().putExtra("name", name.getText().toString()));
        finish();
    }
}
