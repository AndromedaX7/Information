package com.zhhl.qingbao.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by miao on 2018/12/19.
 */
public class UserInfo implements Parcelable {
    private String name;
    private String phone;
    private String sfzh;

    public UserInfo(String name, String phone, String sfzh) {

        this.name = name;
        this.phone = phone;
        this.sfzh = sfzh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.sfzh);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.sfzh = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
