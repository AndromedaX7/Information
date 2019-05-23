package com.zhhl.qingbao.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhhl.qingbao.R;
import com.zhhl.qingbao.adapter.HistoryAdapter;
import com.zhhl.qingbao.app.App;
import com.zhhl.qingbao.data.HistoryData;
import com.zhhl.qingbao.utils.SoapFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HistoryActivity extends AppCompatActivity {

    Gson gson;
    @BindView(R.id.mHistoryList)
    RecyclerView mHistoryList;
    @BindView(R.id.mData)
    TextView mData;
    private ProgressDialog dialog;

    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载...");
        dialog.show();
        gson = new Gson();
        mHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new HistoryAdapter();
        mHistoryList.setAdapter(mAdapter);
//        Log.e("onCreate: ", App.app().getUserInfo().getUserInfo().getCode());
        Observable.create(new ObservableOnSubscribe<HistoryData>() {
            @Override
            public void subscribe(ObservableEmitter<HistoryData> e) throws Exception {
                e.onNext(gson.fromJson(SoapFactory.historyList(
                        "{\"policeCode\":\"" + App.app().getUserInfo().getUserInfo().getCode() + "\"}")
                        .toString().replaceAll("http://10.107.1.143:8789/", "http://192.168.20.228:7103/"), HistoryData.class));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::res, this::err, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        dialog.dismiss();
        mData.setText("服务器异常");
        mData.setVisibility(View.VISIBLE);
    }

    private void res(HistoryData s) {
        Log.e("res: ", s.toString());
        if (s.isSuccess()) {
            if (s.getObj().size() == 0) {
                mData.setText("暂无历史记录");
                mData.setVisibility(View.VISIBLE);
            }
            mAdapter.setData(s.getObj());
            mData.setVisibility(View.GONE);
        } else {
            mData.setText(s.getMsg());
            mData.setVisibility(View.VISIBLE);
        }
        dialog.dismiss();
    }
}
