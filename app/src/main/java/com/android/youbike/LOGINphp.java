package com.android.youbike;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class LOGINphp {
    public static String DBstring(String i, String j, String Wcookie, String url){
        String result="抓不到";
        try{
            HttpClient HC = new DefaultHttpClient();
            HttpPost HP = new HttpPost(url);
            HP.addHeader("Cookie", Wcookie+";expires=Fri,1-Jan-38 07:55:55 GMT; path=/");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ACC", i));
            params.add(new BasicNameValuePair("PW", j));
            HP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse HR = HC.execute(HP);
            result = EntityUtils.toString(HR.getEntity(), HTTP.UTF_8);
        }catch (Exception e){
            result = e.toString();
        }
        return result;
    }
}
