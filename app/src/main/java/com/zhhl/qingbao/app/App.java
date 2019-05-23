//package com.zhhl.qingbao.app;
//
//import android.app.Application;
//import android.os.Handler;
//import android.os.Looper;
//import android.widget.Toast;
//
//import com.xdja.uaac.api.UaacApi;
//import com.zhhl.qingbao.utils.HttpTools;
//import com.zhhl.qingbao.utils.ILogUploadImpl;
//import com.zhhl.qingbao.utils.Login;
//import com.zhhl.qingbao.data.LoginBean;
//import com.zhhl.qingbao.MainActivity;
//import com.zhhl.qingbao.data.StaticBufferData;
//
//import java.util.ArrayList;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
//
///**
// * Created by miao on 2018/9/21.
// */
//public class App extends Application {
//
//    public static ArrayList<StaticBufferData> sdata=new ArrayList<>();
//    private String Token;
//    private LoginBean userInfo;
//    private TokenCallback callback = new TokenCallback();
//
//    public String getToken() {
//        return Token;
//    }
//
//    public void setToken(String token) {
//        Token = token;
//    }
//
//    private static App app;
//
//    private ILogUploadImpl logReport;
//
//    public ILogUploadImpl getLogReport() {
//        return logReport;
//    }
//
//    public void setLogReport(ILogUploadImpl logReport) {
//        this.logReport = logReport;
//    }
//
//    public static App app() {
//        return app;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        app = this;
////        try {
////            Class<?> aClass = Class.forName(SevenData.class.getName());
////
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//
//    }
//
//    public void setUserInfo(LoginBean userInfo) {
//        this.userInfo = userInfo;
//    }
//
//    public LoginBean getUserInfo() {
//        return userInfo;
//    }
//
//    public TokenCallback getCallback() {
//        return callback;
//    }
//
//
//    public class TokenCallback implements com.xdja.uaac.api.TokenCallback {
//
//        @Override
//        public void onSuccess(String s, boolean b) {
//            setToken(s);
//
////            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
//            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//
//                Login login = HttpTools.buildLogin();
//
//                login.login(s)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(this::result, this::onError, this::comp)
//                        .isDisposed();
//            }, 4000);
//
//        }
//
//        private void onError(Throwable throwable) {
//            throwable.printStackTrace();
////            Toast.makeText(getContext(), "<<<<" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        private void comp() {
//
//        }
//
//        private void result(LoginBean loginBean) {
////            String message = loginBean.getMessage();
////            message="result:::"+message;
////            Log.e("result: ", message);
////            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//
//            setUserInfo(loginBean);
//            App.this.result();
//        }
//
//
//        @Override
//        public void onError(String s) {
//            if (s.equals("票据不存在")) {
//                new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                    UaacApi.getToken(App.this, this);
//                }, 2000);
//            }else {
//
//            }
//
//
//            Toast.makeText(App.this, ">>>" + s, Toast.LENGTH_SHORT).show();
//
//            Toast.makeText(App.this, "请登录统一认证系统", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    private void result() {
//        if (getUserInfo().getFlag() == 0) {
//            MainActivity.startCurrent(App.this);
//        } else {
//            Toast.makeText(App.this, "UserInfo ::" + getUserInfo().getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//}
package com.zhhl.qingbao.app;

import android.app.Application;

import com.xdja.sslvpn.api.VpnApi50Impl;
import com.zhhl.qingbao.data.LoginBean;
import com.zhhl.qingbao.data.StaticBufferData;
import com.zhhl.qingbao.utils.ILogUploadImpl;

import java.util.ArrayList;


/**
 * Created by miao on 2018/9/21.
 */
public class App extends Application {

    public static ArrayList<StaticBufferData> sdata = new ArrayList<>();
    private String Token;
    private LoginBean userInfo;
    private ILogUploadImpl iLogUpload;

    public ILogUploadImpl getLogUpload() {
        if (iLogUpload == null)
            iLogUpload = new ILogUploadImpl(new VpnApi50Impl(this));
        return iLogUpload;
    }

    public void setiLogUpload(ILogUploadImpl iLogUpload) {
        this.iLogUpload = iLogUpload;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    private static App app;

    public static App app() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public void setUserInfo(LoginBean userInfo) {
        this.userInfo = userInfo;
    }

    public LoginBean getUserInfo() {
        return userInfo;
    }


}