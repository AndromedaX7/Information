package com.zhhl.qingbao.utils;


import com.zhhl.qingbao.data.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by miao on 2018/9/27.
 */
public interface Login {
    @FormUrlEncoded
    @POST(Api.__BASED__.path)
//    @POST("/test")
    Observable<LoginBean> login(@Field("strBill") String token);

}
