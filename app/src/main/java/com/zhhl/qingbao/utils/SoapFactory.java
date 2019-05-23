package com.zhhl.qingbao.utils;

import android.text.TextUtils;
import android.util.Log;

import com.zhhl.qingbao.app.App;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by miao on 2018/10/29.
 */
public class SoapFactory {
    private static final String target = "http://jws.jeecgframework.org/";
    private static final String url = "http://192.168.20.228:7103/infoplatform/cxf/InformationWebService?wsdl";
//    private static final String url  = "http://hb8twa.natappfree.cc/RecordPersonControl/cxf/InformationWebService?wsdl";
    public static SoapPrimitive newImage(String arg) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(target, "saveInformationImage");
        if (!TextUtils.isEmpty(arg)) {
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setType(String.class);
            propertyInfo.setName("arg0");
            propertyInfo.setValue(arg);
            request.addProperty(propertyInfo);
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.call(null, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        Log.e("newInstanceCall: ", response.toString());
//        App.app().getLogReport().log("{}");
         return response;
    }

    public static SoapPrimitive newCommit(String arg) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(target, "saveInformationData");
        if (!TextUtils.isEmpty(arg)) {
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setType(String.class);
            propertyInfo.setName("arg0");
            propertyInfo.setValue(arg);
            request.addProperty(propertyInfo);
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.call(null, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        Log.e("newInstanceCall: ", response.toString());
//        App.app().getLogReport().log("{}");
        return response;
    }

    public static SoapPrimitive historyList(String arg) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(target, "findInformationData");
        if (!TextUtils.isEmpty(arg)) {
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setType(String.class);
            propertyInfo.setName("arg0");
            propertyInfo.setValue(arg);
            request.addProperty(propertyInfo);
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.call(null, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        Log.e("historyList: ", response.toString());
//        App.app().getLogReport().log("{}");
        return response;
    }

    public static SoapPrimitive newCommitNoMeet(String val) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(target, "saveInformationDataNoMeet");
        if (!TextUtils.isEmpty(val)) {
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setType(String.class);
            propertyInfo.setName("arg0");
            propertyInfo.setValue(val);
            request.addProperty(propertyInfo);
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.call(null, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        Log.e("newInstanceCall: ", response.toString());
//        App.app().getLogReport().log("{}");
        return response;

    }

    public static SoapPrimitive getDepartment() throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(target, "findDepartmentInfoData");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.call(null, envelope);
        SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        Log.e("newInstanceCall: ", response.toString());
//        App.app().getLogReport().log("{}");
        return response;

    }
}
