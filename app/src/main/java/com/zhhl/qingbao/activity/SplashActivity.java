package com.zhhl.qingbao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.xdja.uaac.api.TokenCallback;
import com.xdja.uaac.api.UaacApi;
import com.zhhl.qingbao.MainActivity;
import com.zhhl.qingbao.R;
import com.zhhl.qingbao.app.App;
import com.zhhl.qingbao.data.LoginBean;
import com.zhhl.qingbao.utils.HttpTools;
import com.zhhl.qingbao.utils.ICheckPermission;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.information)
    TextView information;
    private boolean finish;
    @BindView(R.id.root)
    ConstraintLayout splashView;

    @BindView(R.id.indicator)
    ProgressBar indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initData();
    }


    public void showMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void initData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        description.setText("①对“流入人员”进行核录\n" +
//                "②通过拍照方式对人员身份信息进行采集\n" +
//                "③结合后台管理系统对民警采集信息进行统计比对");
//
//        information.setText("如需开通权限请联系系统管理员\n" +
//                "吉林省智慧互联信息科技有限公司\n" +
//                "技术联系电话18043134285");
        UaacApi.getToken(this, new TokenCallback() {
            @Override
            public void onSuccess(String token, boolean b) {
                if (b) login(token);
                else
                    new Handler(Looper.getMainLooper()).postDelayed(() -> login(token), 5000);
            }

            @Override
            public void onError(String s) {
                if (s.equals("票据不存在")) {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> UaacApi.getToken(SplashActivity.this, this), 2000);
                }
            }
        });
    }


    public void login(String token) {
        HttpTools.buildLogin()
                .login(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::callResult, this::onError, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void callResult(LoginBean loginBean) {
        App.app().setUserInfo(loginBean);
//        App.app().setLogReport(new ILogUploadImpl(new VpnApi50Impl(this)));
        HttpTools.build(ICheckPermission.class, "http://192.168.20.228:7098/")
                .checkPermission(loginBean.getUserInfo().getCode(), this.getPackageName())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::checkResult, this::onError, this::onComplete)
                .isDisposed();
//        mRootView.showMain();

    }

    private void checkResult(boolean o) {
        if (o)
            showMain();
        else
            dismissIndicator();
    }

    public void dismissIndicator() {
        indicator.setVisibility(View.INVISIBLE);
        Snackbar.make(splashView, "您的账户没有在系统注册,请与管理员联系", Snackbar.LENGTH_LONG).show();

    }
}
