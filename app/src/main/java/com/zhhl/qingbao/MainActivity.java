//package com.zhhl.qingbao;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.provider.MediaStore;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.xdja.sslvpn.api.VpnApi50Impl;
//import com.xdja.uaac.api.UaacApi;
//import com.xdja.watermarklibrary.WaterMarkUtils;
//import com.zhhl.qingbao.activity.HistoryActivity;
//import com.zhhl.qingbao.activity.QingbaoActivity;
//import com.zhhl.qingbao.activity.SavedActivity;
//import com.zhhl.qingbao.app.App;
//import com.zhhl.qingbao.data.DepData;
//import com.zhhl.qingbao.data.LoginBean;
//import com.zhhl.qingbao.data.UserInfo;
//import com.zhhl.qingbao.utils.HttpTools;
//import com.zhhl.qingbao.utils.ILogUploadImpl;
//import com.zhhl.qingbao.utils.SoapFactory;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
//import static com.zhhl.qingbao.activity.QingbaoActivity.getUriForFile;
//
//public class MainActivity extends AppCompatActivity {
//
//    TextView mName;
//    TextView mPhone;
//    TextView mLevel;
//    TextView mCode;
//    LinearLayout mScan;
//    public static File file;
//    public static boolean hasPicture;
//    public static UserInfo aUserInfo;
//    public static DepData data;
//    private static Gson gson = new Gson();
//    @BindView(R.id.mHistory)
//    TextView mHistory;
//
//    @BindView(R.id.mExit)
//    TextView mtips;
//
//    @BindView(R.id.mSaved)
//    TextView mSaved;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        ButterKnife.bind(this);
//        Intent intent = getIntent();
//        mHistory.setVisibility(View.INVISIBLE);
//
//        mName = findViewById(R.id.mName);
//        mLevel = findViewById(R.id.mLevel);
//        mPhone = findViewById(R.id.mPhone);
//        mCode = findViewById(R.id.mCode);
//        mScan = findViewById(R.id.mScan);
//
//        LoginBean userInfo = App.app().getUserInfo();
//        mName.setText(userInfo.getUserInfo().getName());
//        mLevel.setText(getZhicheng(userInfo.getUserInfo().getPosition()));
//        mCode.setText(userInfo.getUserInfo().getCode());
//        mPhone.setText(userInfo.getUserInfo().getMobile());
//
//        mHistory.setVisibility(View.VISIBLE);
//
//        mtips.setText(Html.fromHtml("点击拍摄身份证照片进行采集<br/><font color=red>见面时拍摄身份证照片<br/>未见面时拍摄场所照片</font>"));
//        if (intent.getBooleanExtra("has", false)) {
//            mName.setText(intent.getStringExtra("name"));
//            mPhone.setText(intent.getStringExtra("phone"));
//            mLevel.setText(intent.getStringExtra("level"));
//            mCode.setText(intent.getStringExtra("code"));
//
//            aUserInfo = new UserInfo(intent.getStringExtra("name"), intent.getStringExtra("phone"), intent.getStringExtra("code"));
//        }
//
//        mScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0x1000);
//                    return;
//                }
//                openCamera();
////                startActivity(new Intent(MainActivity.this, QingbaoActivity.class));
//            }
//        });
//        mScan.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                startActivity(new Intent(MainActivity.this, QingbaoActivity.class).putExtra("idPicture", file.getName()));
//                return true;
//            }
//        });
//
//        WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
//
//        Observable.create(new ObservableOnSubscribe<DepData>() {
//            @Override
//            public void subscribe(ObservableEmitter<DepData> e) throws Exception {
//                e.onNext(gson.fromJson(SoapFactory.getDepartment().toString(), DepData.class));
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::bm, this::err, this::comp)
//                .isDisposed();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 0x1000) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openCamera();
//            } else {
//                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    private void openCamera() {
//        file = new File(getFilesDir(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(this, file));//将拍取的照片保存到指定URI
//        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
//            hasPicture = true;
//            Log.e("onActivityResult: ", file.getName());
//
//            mHistory.postDelayed(() ->
//                            startActivity(new Intent(MainActivity.this, QingbaoActivity.class).putExtra("idPicture", file.getName()))
//                    , 200);
//
//        }
//
//    }
//
//    private static final int REQUEST_CODE_TAKE_PICTURE = 0x0100;
//
//
//    private void comp() {
//
//    }
//
//    private void err(Throwable throwable) {
//        throwable.printStackTrace();
//    }
//
//    private void bm(DepData o) {
//        data = o;
//    }
//
//    public static void startCurrent(Context context) {
//        context.startActivity(new Intent(context, MainActivity.class));
//    }
//
//
//
//
//    private String getZhicheng(String position) {
//        int i = Integer.parseInt(position);
//        switch (i) {
//            case 3:
//                return "其他";
//            default:
//                return "";
//        }
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        if (System.currentTimeMillis() - lastTime <= 2000) {
//            UaacApi.notifyLogout(this);
//            super.onBackPressed();
//        } else {
//            lastTime = System.currentTimeMillis();
//            Toast.makeText(this, "请再按一次退出", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @OnClick(R.id.mHistory)
//    void mHistory() {
//        startActivity(new Intent(this, HistoryActivity.class));
//    }
//
//    private long lastTime;
//
//    @OnClick(R.id.mSaved)
//    void mSaved() {
//        startActivity(new Intent(this, SavedActivity.class));
//    }
//}
package com.zhhl.qingbao;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xdja.uaac.api.UaacApi;
import com.zhhl.qingbao.activity.HistoryActivity;
import com.zhhl.qingbao.activity.QingbaoActivity;
import com.zhhl.qingbao.activity.SavedActivity;
import com.zhhl.qingbao.app.App;
import com.zhhl.qingbao.data.DepData;
import com.zhhl.qingbao.data.LoginBean;
import com.zhhl.qingbao.data.UserInfo;
import com.zhhl.qingbao.utils.SoapFactory;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.zhhl.qingbao.activity.QingbaoActivity.getUriForFile;

public class MainActivity extends AppCompatActivity {

    TextView mName;
    TextView mPhone;
    TextView mLevel;
    TextView mCode;
    LinearLayout mScan;
    public static File file;
    public static boolean hasPicture;
    public static UserInfo aUserInfo;
    public static DepData data;
    private static Gson gson = new Gson();
    @BindView(R.id.mHistory)
    TextView mHistory;

    @BindView(R.id.mSaved)
    TextView mSaved;
    @BindView(R.id.mExit)
    TextView mtips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
//        UaacApi.getToken(this, ((App) getApplication()).getCallback());//      todo //
//        dialog = new ProgressDialog(this);//      todo //
//        dialog.setCancelable(false);//      todo //
//        dialog.setMessage("正在加载中...");//      todo //
//        dialog.show();//      todo //
//        Intent intent = getIntent();
        mHistory.setVisibility(View.INVISIBLE);

        mName = findViewById(R.id.mName);
        mLevel = findViewById(R.id.mLevel);
        mPhone = findViewById(R.id.mPhone);
        mCode = findViewById(R.id.mCode);
        mScan = findViewById(R.id.mScan);
        mtips.setText(Html.fromHtml("点击拍摄身份证照片进行采集<br/><font color=red>见面时拍摄身份证照片<br/>未见面时拍摄场所照片</font>"));
//        if (intent.getBooleanExtra("has", false)) {
//            mName.setText(intent.getStringExtra("name"));
//            mPhone.setText(intent.getStringExtra("phone"));
//            mLevel.setText(intent.getStringExtra("level"));
//            mCode.setText(intent.getStringExtra("code"));
//
//            aUserInfo = new UserInfo(intent.getStringExtra("name"), intent.getStringExtra("phone"), intent.getStringExtra("code"));
//        }


        setUserInfor();

        getDeptInfo();

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0x1000);
                    return;
                }
                openCamera();
//                startActivity(new Intent(MainActivity.this, QingbaoActivity.class));
            }
        });
        mScan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, QingbaoActivity.class).putExtra("idPicture", file.getName()));
                return true;
            }
        });
    }

    private void getDeptInfo() {
        Observable.create(new ObservableOnSubscribe<DepData>() {
            @Override
            public void subscribe(ObservableEmitter<DepData> e) throws Exception {
                e.onNext(gson.fromJson(SoapFactory.getDepartment().toString(), DepData.class));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::bm, this::err, this::comp)
                .isDisposed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openCamera() {
        file = new File(getFilesDir(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(this, file));//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
            hasPicture = true;
            Log.e("onActivityResult: ", file.getName());

            mHistory.postDelayed(() ->
                            startActivity(new Intent(MainActivity.this, QingbaoActivity.class).putExtra("idPicture", file.getName()))
                    , 200);

        }

    }

    private static final int REQUEST_CODE_TAKE_PICTURE = 0x0100;


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (App.app().getToken() != null && !App.app().getToken().equals("") && App.app().getUserInfo() == null) { //      todo //
//            HttpTools.buildLogin() //      todo //
//                    .login(App.app().getToken())//      todo //
//                    .observeOn(AndroidSchedulers.mainThread())    //      todo //
//                    .subscribeOn(Schedulers.io())//      todo //
//                    .subscribe(this::callResult, this::onErr, this::onComplete)//      todo //
//                    .isDisposed(); //      todo //
//        } //      todo //
//
//        if (App.app().getUserInfo() != null) {//      todo //
//            setUserInfor();  //      todo //
//            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
//

//        }
//    }

    private void comp() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void bm(DepData o) {
        data = o;
    }

//    public static void startCurrent(Context context) {
//        context.startActivity(new Intent(context, MainActivity.class));
//    }

//    private void onComplete() {
//
//    }

//    private void onErr(Throwable throwable) {
//        throwable.printStackTrace();
//    }

//    public void callResult(LoginBean bean) {
//        App.app().setUserInfo(bean);
//        setUserInfor();
//        dialog.dismiss();
//        mHistory.setVisibility(View.VISIBLE);
//        MainActivity.startCurrent(this);
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (flag == 1 && App.app().getUserInfo() == null) {  //      todo //
//            MainActivity.startCurrent(this);  //      todo //
//        }//      todo //
//    }

    private void setUserInfor() {
        LoginBean userInfo = ((App) getApplication()).getUserInfo();//      todo //
        if (userInfo != null) { //      todo //
            mName.setText(userInfo.getUserInfo().getName());
            mLevel.setText(getZhicheng(userInfo.getUserInfo().getPosition()));
            mCode.setText(userInfo.getUserInfo().getCode());
            mPhone.setText(userInfo.getUserInfo().getMobile());
//            if (dialog.isShowing()) //      todo //
//                dialog.dismiss(); //      todo //
            mHistory.setVisibility(View.VISIBLE);
        } //      todo //
    }

    private String getZhicheng(String position) {
        int i = Integer.parseInt(position);
        switch (i) {
            case 3:
                return "其他";
            default:
                return "";
        }
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (App.app().getUserInfo() != null)   //      todo //
//            flag = 2; //      todo //
//        else //      todo //
//            flag = 1; //      todo //
//    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime <= 2000) {
            UaacApi.notifyLogout(this);
            super.onBackPressed();
        } else {
            lastTime = System.currentTimeMillis();
            Toast.makeText(this, "请再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.mHistory)
    void mHistory() {
        startActivity(new Intent(this, HistoryActivity.class));
    }

//    private ProgressDialog dialog;
//    private int flag;
    private long lastTime;

    @OnClick(R.id.mSaved)
    void mSaved() {
        startActivity(new Intent(this, SavedActivity.class));
    }
}