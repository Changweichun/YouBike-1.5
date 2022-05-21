package com.android.youbike;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class AccidentNotificationCheckPhp {
    public static String AccidentNotificationCheckPhp(String i, String Wcookie, String url){
        String result="抓不到";
        try{
            HttpClient HC = new DefaultHttpClient();
            HttpPost HP = new HttpPost(url+"accidentNotificationCardCheck.php");
            HP.addHeader("Cookie", Wcookie+";expires=Fri,1-Jan-38 07:55:55 GMT; path=/");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("CARD", i));
            HP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse HR = HC.execute(HP);
            result = EntityUtils.toString(HR.getEntity(), HTTP.UTF_8);
        }catch (Exception e){
            result = e.toString();
        }
        return result;
    }
}
