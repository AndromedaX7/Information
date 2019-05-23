package com.zhhl.qingbao.utils;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ICheckPermission {
    @POST("http://192.168.20.228:7098/jeesite/jinghao/yanzheng")
    @FormUrlEncoded
    Observable<Boolean> checkPermission(@Field("jinghao") String jinghao, @Field("dizhi") String dizhi);
}
