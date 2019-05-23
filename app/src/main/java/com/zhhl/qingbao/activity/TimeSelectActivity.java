package com.zhhl.qingbao.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhhl.qingbao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeSelectActivity extends AppCompatActivity {


    @BindView(R.id.r1)
    RadioButton r1;
    @BindView(R.id.r2)
    RadioButton r2;
    @BindView(R.id.r3)
    RadioButton r3;
//    @BindView(R.id.r4)
//    RadioButton r4;
    @BindView(R.id.r5)
    RadioButton r5;
    @BindView(R.id.r6)
    RadioButton r6;
    @BindView(R.id.things)
    EditText things;
    @BindView(R.id.r0)
    RadioGroup r0;
    @BindView(R.id.mQuery)
    TextView mQuery;

    String selectText;
    private int checkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        setContentView(R.layout.activity_time_select);
        ButterKnife.bind(this);
        r1.setChecked(true);
        selectText=r1.getText().toString();
        checkId=r1.getId();
        r0.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkId = checkedId;
                if (checkedId != r6.getId()) {
                    selectText = ((RadioButton) findViewById(checkedId)).getText().toString();
                    things.setVisibility(View.INVISIBLE);
                } else {
                    things.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @OnClick(R.id.mQuery)
    public void onMQueryClicked() {
        if (checkId == r6.getId()) {
            selectText = things.getText().toString();
        }
        Intent intent = new Intent();
        intent.putExtra("things", selectText);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();

    }


}
