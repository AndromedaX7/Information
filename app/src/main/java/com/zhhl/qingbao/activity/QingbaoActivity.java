package com.zhhl.qingbao.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xdja.watermarklibrary.WaterMarkUtils;
import com.zhhl.qingbao.BuildConfig;
import com.zhhl.qingbao.MainActivity;
import com.zhhl.qingbao.R;
import com.zhhl.qingbao.adapter.GridViewAdapter;
import com.zhhl.qingbao.adapter.SimpleAdapter;
import com.zhhl.qingbao.adapter.SimpleAdapterYesNo;
import com.zhhl.qingbao.app.App;
import com.zhhl.qingbao.data.DepData;
import com.zhhl.qingbao.data.ImageData;
import com.zhhl.qingbao.data.RespData;
import com.zhhl.qingbao.data.RespDataWrapper;
import com.zhhl.qingbao.data.StaticBufferData;
import com.zhhl.qingbao.data.UserInfo;
import com.zhhl.qingbao.utils.DialogUtils;
import com.zhhl.qingbao.utils.SoapFactory;

import org.ksoap2.serialization.SoapPrimitive;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class QingbaoActivity extends AppCompatActivity {

    EditText mName;
    EditText mSfzh;
    EditText mPhone;
    EditText mImei;
    EditText mCsmc;
    GridView gridView;
    FrameLayout txr;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.textView11)
    TextView things;
    //    @BindView(R.id.scan_hjdz)
//    ImageView scan_hjdz;
//    @BindView(R.id.sfzh_scan)
//    ImageView sfzhScan;
    @BindView(R.id.sxr)
    LinearLayout sxr;
    @BindView(R.id.mCommit)
    TextView mCommit;
    @BindView(R.id.mXxdz)
    EditText xxdz;
    @BindView(R.id.sTxmj)
    TextView sTxmj;
    @BindView(R.id.sName)
    TextView sName;
    @BindView(R.id.sSfzh)
    TextView sSfzh;
    @BindView(R.id.sPhone)
    TextView sPhone;
    @BindView(R.id.sImei)
    TextView sImei;
    @BindView(R.id.sWhere)
    TextView sWhere;
    @BindView(R.id.sLjsy)
    TextView sLjsy;
    @BindView(R.id.sSxr)
    TextView sSxr;
    @BindView(R.id.sAddress)
    TextView sAddress;
    @BindView(R.id.mSave)
    TextView mSave;
    @BindView(R.id.txmj_name)
    TextView txmj_name;
    @BindView(R.id.sxrName)
    TextView sxrName;
    @BindView(R.id.mDepNameList)
    Spinner mDepNameList;
    @BindView(R.id.tipBz)
    TextView tipBz;
    @BindView(R.id.yesNo)
    Spinner yesNo;
    @BindView(R.id._3_1)
    ConstraintLayout _3_1;
    @BindView(R.id.mBz)
    EditText mBz;
    @BindView(R.id._3_2)
    LinearLayout _3_2;
    @BindView(R.id.mBase)
    FrameLayout mBorder;

    private String idPath;
    private GridViewAdapter adapter = new GridViewAdapter(new ArrayList<>());
    private final static int REQUEST_CODE_TAKE_PICTURE = 0x100;
    private int selectPosition = -1;

    int yes;
    int no;

    private boolean flag = true;

    private File file;

    private File idPictureFile;
    private int saveDateId = -1;
    public static UserInfo aUserInfo;
    public static UserInfo txrUserInfo;
    private String bm;
    private Gson gson;
    private HashMap<Integer, String> pathMap = new HashMap<>();
    private HashMap<Integer, String> fileMap = new HashMap<>();
    private SimpleAdapter mAdapter;
    private SimpleAdapterYesNo yesNoAdapter = new SimpleAdapterYesNo(new ArrayList<>());

    private String resultIdPicturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qingbao_2);
        ButterKnife.bind(this);

        idPath = getIntent().getStringExtra("idPicture");
        idPictureFile = new File(getFilesDir().getAbsolutePath() + "/temp", idPath);


        zipIdPic();

        uploadIdPicture();
//        Log.e( "onCreate: ", idPictureFile.getAbsolutePath()+":::"+idPath+">>>"+idPictureFile.exists());

        mTipDialog = DialogUtils.createTips(this, "返回到首页", "数据将会丢失,是否继续", "回到首页", "取消", (d, w) -> finish(), null, false);
        saveDateId = getIntent().getIntExtra("savedId", -1);
        mAdapter = new SimpleAdapter(new ArrayList<>());
        DepData.ObjBean objBean = new DepData.ObjBean();
        objBean.setName("请选择");
        mAdapter.addItem(objBean);
        if (MainActivity.data != null) {
            mAdapter.addData(new ArrayList<>(MainActivity.data.getObj()));
            bm = mAdapter.getData().get(0).getName();
        }
        mDepNameList.setAdapter(mAdapter);
        mDepNameList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bm = mAdapter.getData().get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gson = new Gson();
//        mSwitch.setChecked(true);
        yesNoAdapter.addItem(new SimpleAdapterYesNo.Str("是", true));
        yesNoAdapter.addItem(new SimpleAdapterYesNo.Str("否", false));
        yesNo.setAdapter(yesNoAdapter);

        if (App.app().getUserInfo() != null)
            WaterMarkUtils.addWaterMark(this, App.app().getUserInfo().getUserInfo().getName() + " " + App.app().getUserInfo().getUserInfo().getCode(), 270 + 45, getResources().getColor(R.color.wt), 60);
        mName = findViewById(R.id.mName);
        mSfzh = findViewById(R.id.mSfzh);
        mPhone = findViewById(R.id.mPhone);
        mImei = findViewById(R.id.mImei);
        mCsmc = findViewById(R.id.mCsmc);
        gridView = findViewById(R.id.gridView);
        txr = findViewById(R.id.txr);
        txrUserInfo = null;
        sSfzh.setText(Html.fromHtml("身份证号<font color=red>*</font>"));
        sName.setText(Html.fromHtml("姓名<font color=red>*</font>"));
        sPhone.setText(Html.fromHtml("手机号<font color=red>*</font>"));
        sImei.setText(Html.fromHtml("手机串码<font color=red>*</font>"));
        sWhere.setText(Html.fromHtml("场所名称<font color=red>*</font>"));
        sAddress.setText(Html.fromHtml("户籍地址<font color=red>*</font>"));
        sLjsy.setText(Html.fromHtml("来吉事由<font color=red>*</font>"));
        tipBz.setText(Html.fromHtml("备注<font color=red>*</font>"));
//        sSxr.setText(Html.fromHtml("随行人<font color=red>*</font>"));
        txr.setOnClickListener(v -> startActivityForResult(new Intent(QingbaoActivity.this, TxrActivity.class), 0x12));

        gridView.setAdapter(adapter);
        adapter.addItem("1");
        adapter.addItem("2");
        adapter.addItem("3");
        adapter.addItem("4");
        adapter.addItem("5");
        adapter.addItem("");
        if (saveDateId != -1) {
            StaticBufferData s = App.sdata.get(saveDateId);
            if (s != null) {
                mName.setText(s.getXm());
                mSfzh.setText(s.getSfzh());
                mPhone.setText(s.getSjh());
                mImei.setText(s.getSjch());
                mCsmc.setText(s.getCsmc());
                things.setText(s.getLjsy());
                xxdz.setText(s.getXxdz());
                if (!TextUtils.isEmpty(s.getTxmjxm())) {
                    aUserInfo = new UserInfo(s.getTxmjxm(), s.getTxmjdh(), s.getTxmjjh());
                    txmj_name.setText(s.getTxmjxm());
                }
                if (!TextUtils.isEmpty(s.getTxr())) {
                    txrUserInfo = new UserInfo();
                    txrUserInfo.setPhone(s.getTxrPhone());
                    txrUserInfo.setSfzh(s.getTxrSfzh());
                    txrUserInfo.setName(s.getTxr());
                    sxrName.setText(s.getTxr());
                }
                flag = s.isFlag();
                _3_1.post(() -> {
                    changeStateDebug(s.isFlag());
                    yesNo.setSelection(flag ? 0 : 1);
                });
                bm = s.getBm();
                mBz.setText(s.getBz());
                mDepNameList.setSelection(mAdapter.indexOf(bm));
                pathMap.put(0, s.getZpRy());
                pathMap.put(1, s.getZpXdwp());
                pathMap.put(2, s.getZpXd());
                pathMap.put(3, s.getZpCl());
                pathMap.put(4, s.getZpTxrsfz());
                pathMap.put(5, s.getZpDemp1());
                pathMap.put(6, s.getZpDemp2());
                pathMap.put(7, s.getZpDemp3());
                pathMap.put(8, s.getZpDemp4());
                pathMap.put(9, s.getZpDemp5());

                fileMap.put(0, s.getFl0());
                fileMap.put(1, s.getFl1());
                fileMap.put(2, s.getFl2());
                fileMap.put(3, s.getFl3());
                fileMap.put(4, s.getFl4());
                fileMap.put(5, s.getFl5());
                fileMap.put(6, s.getFl6());
                fileMap.put(7, s.getFl7());
                fileMap.put(8, s.getFl8());
                fileMap.put(9, s.getFl9());
                adapter.clearAll();
                for (int i = 0; i < 10; i++) {
                    if (TextUtils.isEmpty(fileMap.get(i))) {
                        switch (i) {
                            case 0:
                                adapter.add("1");
                                break;
                            case 1:
                                adapter.add("2");
                                break;
                            case 2:
                                adapter.add("3");
                                break;
                            case 3:
                                adapter.add("4");
                                break;
                            case 4:
                                adapter.add("5");
                                break;
                            default:
                                adapter.add("");
                        }
                    } else
                        adapter.add(fileMap.get(i));
                }

            }
        }
        gridView.setOnItemClickListener((parent, view, position, id) -> {
                    selectPosition = position;
                    if (ContextCompat.checkSelfPermission(QingbaoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(QingbaoActivity.this, new String[]{Manifest.permission.CAMERA}, 0x1000);
                        return;
                    }
                    openCamera();
                }
        );

        yesNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flag = yesNoAdapter.getData().get(position).isFlag();
                changeState(flag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        mSwitch.setOnCheckedChangeListener((v, b) -> {
//            flag = b;
//            Log.e("onCreate: ", "" + flag);
//            changeStateCompat(b);
//        });

        _3_1.post(() -> {
            yes = _3_1.getMeasuredHeight();
            changeStateCompat(true);
        });
        _3_2.post(() -> {
            no = _3_2.getMeasuredHeight();
        });

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

    public static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, "com.fish.qingbao", file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {

            if (/*!adapter.getItem(selectPosition).equals("")&&*/!adapter.getItem(selectPosition).equals("1") && !adapter.getItem(selectPosition).equals("2") && !adapter.getItem(selectPosition).equals("3") && !adapter.getItem(selectPosition).equals("4") && !adapter.getItem(selectPosition).equals("5")) {
                adapter.addItem("");
            }
            Log.e("onActivityResult: ", ":" + file.length());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            options.outWidth = options.outWidth / 2;
            options.outHeight = options.outHeight / 2;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            ByteArrayOutputStream buffStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, buffStream);

            try {
                FileOutputStream fout = new FileOutputStream(file);
                fout.write(buffStream.toByteArray());
                fout.flush();
                fout.close();
                Log.e("onActivityResult: ", ":" + file.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap.recycle();

            int imageType = 0;
            adapter.setItem(selectPosition, file.toURI().toString());

            if (selectPosition >= 0 && selectPosition < 5) {
                imageType = selectPosition + 1;
            } else if (selectPosition >= 5) {
                imageType = 6;
            }
//                pathMap.put(selectPosition, file.getAbsolutePath());

            if (dialog == null)
                dialog = DialogUtils.createProgressDialog(this, "正在上传图片,请稍后...");
            dialog.show();
            int finalImageType = imageType;
            Observable.create(new ObservableOnSubscribe<RespDataWrapper>() {
                @Override
                public void subscribe(ObservableEmitter<RespDataWrapper> e) throws Exception {

                    long length = file.length();
                    byte buff[] = new byte[(int) length];
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.read(buff);
                    fileInputStream.close();
                    Log.e("subscribe: ", gson.toJson(new ImageData(finalImageType, new String(Base64.encode(buff, Base64.DEFAULT)).replaceAll("\n", ""))));
                    SoapPrimitive soapPrimitive = SoapFactory.newImage((gson.toJson(new ImageData(finalImageType, new String(Base64.encode(buff, Base64.DEFAULT)).replaceAll("\n", "")))));
                    RespData respData = gson.fromJson(soapPrimitive.toString(), RespData.class);
                    RespDataWrapper respDataWrapper = new RespDataWrapper();
                    respDataWrapper.data = respData;
                    respDataWrapper.filePath = file.toURI().toString();
                    e.onNext(respDataWrapper);

                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::res, this::err, this::onComplete)
                    .isDisposed();

//            if (selectPosition == adapter.getCount() - 1)
//                adapter.addItem("");
        }

        if (requestCode == reqTran && resultCode == RESULT_OK) {
            things.setText(data.getStringExtra("things"));
        } else if (reqTran == requestCode && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "您取消了事由设置", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 0x12 && RESULT_OK == resultCode) {
            txmj_name.setText(data.getStringExtra("name"));
        }
        if (requestCode == 0x13 && RESULT_OK == resultCode) {
            sxrName.setText(data.getStringExtra("name"));
        }
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        if (dialog != null) dialog.dismiss();
        Toast.makeText(this, "服务器异常,请重试", Toast.LENGTH_SHORT).show();
    }

    private void res(RespDataWrapper respData) {
        if (dialog != null) dialog.dismiss();
        Log.e("res: ", respData.data.getMsg());
        if (respData.data.isSuccess()) {
            pathMap.put(selectPosition, respData.data.getObj());
            fileMap.put(selectPosition, respData.filePath);
        }
        File file = new File(URI.create(respData.filePath));
        if (file.exists()){
            try {
                FileInputStream fin =new FileInputStream(file);
                int available = fin.available();
                byte[] buff = new byte[available];
                fin.read(buff);
                fin.close();
                App.app().getLogUpload().log( new String(Base64.encode(buff, Base64.DEFAULT)).replaceAll("\n", ""),"/saveInformationImage",gson.toJson(respData.data));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String AUTHORITY = "com.fish.qingbao";

    @OnClick(R.id.constraintLayout)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, TimeSelectActivity.class), reqTran);
    }

    private
    static final int reqTran = 0x101;


    private void zipIdPic() {
        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.outWidth = options.outWidth / 2;
        options.outHeight = options.outHeight / 2;
        Bitmap bitmap = BitmapFactory.decodeFile(idPictureFile.getAbsolutePath(), options);
        Log.e( "unzipSize: ","size"+idPictureFile.length() );
        try {
            FileOutputStream out =new FileOutputStream(idPictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,10 ,out);
            Log.e( "unzipSize: ","size"+idPictureFile.length() );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void uploadIdPicture() {
        Observable.create(new ObservableOnSubscribe<RespDataWrapper>() {
            @Override
            public void subscribe(ObservableEmitter<RespDataWrapper> e) throws Exception {
                Gson gson =new Gson();
                long length = idPictureFile.length();
                byte buff[] = new byte[(int) length];
                FileInputStream fileInputStream = new FileInputStream(idPictureFile);
                fileInputStream.read(buff);
                fileInputStream.close();
                Log.e("subscribe: ", gson.toJson(new ImageData(6,  new String(Base64.encode(buff, Base64.DEFAULT)).replaceAll("\n", ""))));
                SoapPrimitive soapPrimitive = SoapFactory.newImage((gson.toJson(new ImageData(6, new String(Base64.encode(buff, Base64.DEFAULT)).replaceAll("\n", "")))));
                RespData respData = gson.fromJson(soapPrimitive.toString(), RespData.class);
                RespDataWrapper respDataWrapper = new RespDataWrapper();
                respDataWrapper.data = respData;
                respDataWrapper.filePath = idPictureFile.toURI().toString();
                e.onNext(respDataWrapper);

            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::idRes, this::err, this::onComplete)
                .isDisposed();
    }

    private void idRes(RespDataWrapper respDataWrapper) {
        Log.e( "idRes: ", respDataWrapper.data.getMsg());
        if (respDataWrapper.data.isSuccess()) {
            Toast.makeText(this, "身份证照片已上传", Toast.LENGTH_SHORT).show();
            resultIdPicturePath = respDataWrapper.data.getObj();
        }
    }

    @OnClick(R.id.sxr)
    public void onViewClickedSxr() {
        startActivityForResult(new Intent(this, SxrActivity.class), 0x13);
    }

    @OnClick(R.id.mSave)
    void save() {
        StaticBufferData s = new StaticBufferData();
        s.setFlag(flag);
        s.setBz(mBz.getText().toString());
        s.setBm(bm);
        s.setXm(mName.getText().toString());
        s.setSfzh(mSfzh.getText().toString());
        s.setIdPicture(idPath);
        if (s.getSfzh().length() == 18)
            s.setXb(Integer.parseInt(s.getSfzh().substring(16, 17)) % 2 == 0 ? "女" : "男");
        else if (s.getSfzh().length() == 15) {
            s.setXb(Integer.parseInt(s.getSfzh().substring(14)) % 2 == 0 ? "女" : "男");
        } else {
            s.setXb("女");
        }
        s.setSjh(mPhone.getText().toString());
        s.setSjch(mImei.getText().toString());
        s.setCsmc(mCsmc.getText().toString());
        s.setLjsy(things.getText().toString());
        s.setXxdz(xxdz.getText().toString());
        if (aUserInfo != null) {
            s.setTxmjxm(aUserInfo.getName());
            s.setTxmjjh(aUserInfo.getSfzh());
            s.setTxmjdh(aUserInfo.getPhone());
        }

        if (txrUserInfo != null) {
            s.setTxr(txrUserInfo.getName());
            s.setTxrPhone(txrUserInfo.getPhone());
            s.setTxrSfzh(txrUserInfo.getSfzh());
        }
        if (MainActivity.aUserInfo != null) {
            s.setMjxm(MainActivity.aUserInfo.getName());
            s.setMjjh(MainActivity.aUserInfo.getSfzh());
            s.setMjdh(MainActivity.aUserInfo.getPhone());
            if (App.app().getUserInfo() != null)
                s.setMjssbm((App.app().getUserInfo().getUserInfo().getDepcode()));
        } else {
            s.setMjxm(App.app().getUserInfo().getUserInfo().getName());
            s.setMjjh(App.app().getUserInfo().getUserInfo().getCode());
            s.setMjdh(App.app().getUserInfo().getUserInfo().getMobile());
            s.setMjssbm(App.app().getUserInfo().getUserInfo().getDepcode());
        }
        String p1 = pathMap.get(0);
        if (!"1".equals(p1)) {
            s.setZpRy(p1);
            s.setFl0(fileMap.get(0));
        }
        String p2 = pathMap.get(1);
        if (!"2".equals(p2)) {
            s.setZpXdwp(p2);
            s.setFl1(fileMap.get(1));
        }
        String p3 = pathMap.get(2);
        if (!"3".equals(p3)) {
            s.setZpXd(p3);
            s.setFl2(fileMap.get(2));
        }
        String p4 = pathMap.get(3);
        if (!"4".equals(p4)) {
            s.setZpCl(p4);
            s.setFl3(fileMap.get(3));
        }
        String p5 = pathMap.get(4);
        if (!"5".equals(p5)) {
            s.setZpTxrsfz(p5);
            s.setFl4(fileMap.get(4));
        }

        String p6 = pathMap.get(5);
        if (!"".equals(p6)) {
            s.setZpDemp1(p6);
            s.setFl5(fileMap.get(5));
        }
        String p7 = pathMap.get(6);
        if (!"".equals(p7)) {
            s.setZpDemp2(p7);
            s.setFl6(fileMap.get(6));
        }
        String p8 = pathMap.get(7);
        if (!"".equals(p8)) {
            s.setZpDemp3(p8);
            s.setFl7(fileMap.get(7));
        }
        String p9 = pathMap.get(8);
        if (!"".equals(p9)) {
            s.setZpDemp4(p9);
            s.setFl8(fileMap.get(8));
        }
        String p10 = pathMap.get(9);
        if (!"".equals(p10)) {
            s.setZpDemp5(p10);
            s.setFl9(fileMap.get(9));
        }
        App.sdata.add(s);
        clear();

        if (saveDateId != -1) {
            App.sdata.remove(saveDateId);
        }
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    private void clear() {
        mName.setText("");
        mSfzh.setText("");
        mPhone.setText("");
        mImei.setText("");
        mCsmc.setText("");
        xxdz.setText("");
        mBz.setText("");
        pathMap.clear();
        fileMap.clear();
        adapter.clear();
        sxrName.setText("点击设置随行人信息");
        things.setText("请选择来吉事由");
        txrUserInfo = null;
    }

    @OnClick(R.id.mCommit)
    public void onCommit() {
        if (TextUtils.isEmpty(bm) || bm.equals("请选择")) {
            Toast.makeText(this, "请选择部门信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mName.getText().toString())) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mSfzh.getText().toString())) {
            Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
            return;
        }


        if (flag) {
            if (TextUtils.isEmpty(xxdz.getText().toString())) {
                Toast.makeText(this, "请输入户籍地址", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(mPhone.getText().toString())) {
                Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mImei.getText().toString())) {
                Toast.makeText(this, "请输入手机串码", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(mCsmc.getText().toString())) {
                Toast.makeText(this, "请输入场所名称", Toast.LENGTH_SHORT).show();
                return;
            }
            if (things.getText().toString().equals("请选择来吉事由")) {
                Toast.makeText(this, "请选择来吉事由", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pathMap.get(0) == null) {
                Toast.makeText(QingbaoActivity.this, "请拍摄被采集人照片", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pathMap.get(1) == null) {
                Toast.makeText(QingbaoActivity.this, "请拍摄被采集人物品照片", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pathMap.get(2) == null) {
                Toast.makeText(QingbaoActivity.this, "请拍摄被采集人鞋及鞋底照片", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            if (TextUtils.isEmpty(mBz.getText().toString())) {
                Toast.makeText(this, "请输入备注", Toast.LENGTH_SHORT).show();
                return;
            }

        }


        if (commitDialog == null)
            commitDialog = DialogUtils.createProgressDialog(this, "正在提交,请稍后...");
        commitDialog.show();
        if (flag) {

//        if (pathMap.get(3) == null) {
//            commitDialog.dismiss();
//            Toast.makeText(QingbaoActivity.this, "请拍摄被采集人车辆照片", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (pathMap.get(4) == null) {
//            commitDialog.dismiss();
//            Toast.makeText(QingbaoActivity.this, "请拍摄被采集人同行人身份证照片", Toast.LENGTH_SHORT).show();
//            return;
//        }

            Observable.create(new ObservableOnSubscribe<RespData>() {
                @Override
                public void subscribe(ObservableEmitter<RespData> e) throws Exception {

                    StaticBufferData s = new StaticBufferData();
                    s.setBm(bm);
                    s.setSfjm("Y");
                    s.setXm(mName.getText().toString());
                    s.setSfzh(mSfzh.getText().toString());
                    if (s.getSfzh().length() == 18)
                        s.setXb(Integer.parseInt(s.getSfzh().substring(16, 17)) % 2 == 0 ? "女" : "男");
                    else if (s.getSfzh().length() == 15) {
                        s.setXb(Integer.parseInt(s.getSfzh().substring(14)) % 2 == 0 ? "女" : "男");
                    } else {
                        s.setXb("女");
                    }
                    s.setSjh(mPhone.getText().toString());
                    s.setSjch(mImei.getText().toString());
                    s.setCsmc(mCsmc.getText().toString());
                    s.setLjsy(things.getText().toString());
                    s.setXxdz(xxdz.getText().toString());
                    if (aUserInfo != null) {
                        s.setTxmjxm(aUserInfo.getName());
                        s.setTxmjjh(aUserInfo.getSfzh());
                        s.setTxmjdh(aUserInfo.getPhone());
                    }

                    if (txrUserInfo != null) {
                        s.setTxr(txrUserInfo.getName());
                    }
                    if (MainActivity.aUserInfo != null) {
                        s.setMjxm(MainActivity.aUserInfo.getName());
                        s.setMjjh(MainActivity.aUserInfo.getSfzh());
                        s.setMjdh(MainActivity.aUserInfo.getPhone());
                        if (App.app().getUserInfo() != null)
                            s.setMjssbm((App.app().getUserInfo().getUserInfo().getDepcode()));

                    } else {
                        s.setMjxm(App.app().getUserInfo().getUserInfo().getName());
                        s.setMjjh(App.app().getUserInfo().getUserInfo().getCode());
                        s.setMjdh(App.app().getUserInfo().getUserInfo().getMobile());
                        s.setMjssbm(App.app().getUserInfo().getUserInfo().getDepcode());
                    }
                    String p1 = pathMap.get(0);
                    if (!"1".equals(p1))
                        s.setZpRy(p1);

                    String p2 = pathMap.get(1);
                    if (!"2".equals(p2)) {
                        s.setZpXdwp(p2);
                    }
                    String p3 = pathMap.get(2);
                    if (!"3".equals(p3)) {
                        s.setZpXd(p3);
                    }
                    String p4 = pathMap.get(3);
                    if (!"4".equals(p4)) {
                        s.setZpCl(p4);
                    }
                    String p5 = pathMap.get(4);
                    if (!"5".equals(p5)) {
                        s.setZpTxrsfz(p5);
                    }

                    String p6 = pathMap.get(5);
                    if (!"".equals(p6))
                        s.setZpDemp1(p6);
                    String p7 = pathMap.get(6);
                    if (!"".equals(p7))
                        s.setZpDemp2(p7);
                    String p8 = pathMap.get(7);
                    if (!"".equals(p8))
                        s.setZpDemp3(p8);
                    String p9 = pathMap.get(8);
                    if (!"".equals(p9))
                        s.setZpDemp4(p9);
                    String p10 = pathMap.get(9);
                    if (!"".equals(p10))
                        s.setZpDemp5(p10);


                    s.setZpDemp1(resultIdPicturePath);
                    Log.e("subscribe: ", gson.toJson(s));
                    SoapPrimitive soapPrimitive = SoapFactory.newCommit(gson.toJson(s));
                    RespData respData = gson.fromJson(soapPrimitive.toString(), RespData.class);
                    e.onNext(respData);
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::res2, this::err2, this::onComplete)
                    .isDisposed();
        } else {

            Observable.create(new ObservableOnSubscribe<RespData>() {
                @Override
                public void subscribe(ObservableEmitter<RespData> e) throws Exception {
                    StaticBufferData s = new StaticBufferData();
                    s.setBm(bm);
                    s.setSfjm("N");
                    s.setBz(mBz.getText().toString());
                    s.setXm(mName.getText().toString());
                    s.setSfzh(mSfzh.getText().toString());
                    s.setZpDemp1(resultIdPicturePath);
                    if (s.getSfzh().length() == 18)
                        s.setXb(Integer.parseInt(s.getSfzh().substring(16, 17)) % 2 == 0 ? "女" : "男");
                    else if (s.getSfzh().length() == 15) {
                        s.setXb(Integer.parseInt(s.getSfzh().substring(14)) % 2 == 0 ? "女" : "男");
                    } else {
                        s.setXb("女");
                    }
                    if (aUserInfo != null) {
                        s.setTxmjxm(aUserInfo.getName());
                        s.setTxmjjh(aUserInfo.getSfzh());
                        s.setTxmjdh(aUserInfo.getPhone());
                    }
                    if (MainActivity.aUserInfo != null) {
                        s.setMjxm(MainActivity.aUserInfo.getName());
                        s.setMjjh(MainActivity.aUserInfo.getSfzh());
                        s.setMjdh(MainActivity.aUserInfo.getPhone());
                        if (App.app().getUserInfo() != null)
                            s.setMjssbm((App.app().getUserInfo().getUserInfo().getDepcode()));

                    } else {
                        s.setMjxm(App.app().getUserInfo().getUserInfo().getName());
                        s.setMjjh(App.app().getUserInfo().getUserInfo().getCode());
                        s.setMjdh(App.app().getUserInfo().getUserInfo().getMobile());
                        s.setMjssbm(App.app().getUserInfo().getUserInfo().getDepcode());

//                    66.setMjssbm("4444");
                    }

                    if (txrUserInfo != null) {
                        s.setTxr(txrUserInfo.getName());
                    }
                    SoapPrimitive soapPrimitive = SoapFactory.newCommitNoMeet(gson.toJson(s));
                    RespData respData = gson.fromJson(soapPrimitive.toString(), RespData.class);
                    e.onNext(respData);
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::res2, this::err2, this::onComplete)
                    .isDisposed();

        }

    }

    private void err2(Throwable throwable) {
        if (commitDialog != null)
            commitDialog.dismiss();
        throwable.printStackTrace();
        Toast.makeText(this, "服务器异常,请重试", Toast.LENGTH_SHORT).show();
    }

    private void res2(RespData respData) {
        if (commitDialog != null)
            commitDialog.dismiss();
        if (respData.isSuccess()) {
            mName.setText("");
            mSfzh.setText("");
            mPhone.setText("");
            mImei.setText("");
            mCsmc.setText("");
            xxdz.setText("");
            mBz.setText("");
            pathMap.clear();
            adapter.clear();
            sxrName.setText("点击设置随行人信息");
            things.setText("请选择来吉事由");
            if (saveDateId != -1) {
                App.sdata.remove(saveDateId);
            }

            DialogUtils.showSuccess(mCommit, new DialogUtils.CallbackDialog() {
                @Override
                public void callback() {
//                finish();
                }
            });
        } else {
            Toast.makeText(this, "必选参数不可为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeState(boolean flag) {
        FrameLayout.LayoutParams _3_1Params = (FrameLayout.LayoutParams) _3_1.getLayoutParams();
        LinearLayout.LayoutParams _borderParams = (LinearLayout.LayoutParams) mBorder.getLayoutParams();

        int max = Math.max(yes, no);
        int min = Math.min(yes, no);

        ValueAnimator _borderAnimator;
        if (flag) {
            ValueAnimator _3_1Animator = ValueAnimator.ofInt(_3_1Params.leftMargin, 0);
            _3_1Animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    _3_1.setVisibility(View.VISIBLE);
                }
            });
            _3_1Animator.addUpdateListener(animation -> {
                _3_1Params.leftMargin = (int) animation.getAnimatedValue();
                _3_1.setLayoutParams(_3_1Params);
            });
            _3_1Animator.setDuration(500);
            _3_1Animator.start();
            ValueAnimator _3_2Animator = ValueAnimator.ofFloat(1, 0);
            _3_2Animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    _3_2.setVisibility(View.GONE);
                }
            });
            _3_2Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (float) animation.getAnimatedValue();
                    _3_2.setAlpha(alpha);
                }
            });
            _3_2Animator.setDuration(500);
            _3_2Animator.start();
            _borderAnimator = ValueAnimator.ofInt(min, max);


        } else {
            ValueAnimator _3_1Animator = ValueAnimator.ofInt(0, _3_1.getWidth());
            _3_1Animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    _3_1.setVisibility(View.GONE);
                }
            });
            _3_1Animator.addUpdateListener(animation -> {
                _3_1Params.leftMargin = (int) animation.getAnimatedValue();
                _3_1.setLayoutParams(_3_1Params);
            });
            _3_1Animator.setDuration(500);
            _3_1Animator.start();
            ValueAnimator _3_2Animator = ValueAnimator.ofFloat(0, 1);
            _3_2Animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    _3_2.setVisibility(View.VISIBLE);
                }
            });
            _3_2Animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float alpha = (float) animation.getAnimatedValue();
                    _3_2.setAlpha(alpha);
                }
            });
            _3_2Animator.setDuration(500);
            _3_2Animator.start();
            _borderAnimator = ValueAnimator.ofInt(max, min);
        }

        _borderAnimator.setDuration(500);
        _borderAnimator.addUpdateListener(animation -> {
            _borderParams.height = (int) animation.getAnimatedValue();
            mBorder.setLayoutParams(_borderParams);
        });
        _borderAnimator.start();
    }

    private void changeStateCompat(boolean flag) {
        if (BuildConfig.DEBUG) {
            changeStateDebug(flag);
        } else {
            changeState(flag);
        }
    }

    private void changeStateDebug(boolean flag) {
        _3_1.setVisibility(flag ? View.VISIBLE : View.GONE);
        _3_2.setVisibility(flag ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
        if (mTipDialog != null && !mTipDialog.isShowing()) {
            mTipDialog.show();
        }
    }

    private ProgressDialog dialog;
    private ProgressDialog commitDialog;

    private AlertDialog mTipDialog;
}
