package com.android.youbike;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ServiceCenterPhp {
    private static HttpClient HC;
    private static HttpPost HP;

    public static String ServiceCenterPhp (String i, String Wcook, String url){
        String result="錯誤";
        try {

            HC =new DefaultHttpClient();
            HP=new HttpPost(url+"service_center.php");
            HP.addHeader("Cookie", Wcook+";expires=Fri,1-Jan-38 07:55:55 GMT; path=/");

            ArrayList<NameValuePair> params= new ArrayList<NameValuePair>();
            if (i!=null && !i.toString().equals("")) {
                params.add(new BasicNameValuePair("S1", i));
                HP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }

            HttpResponse HR = HC.execute(HP);
            HttpEntity HE= HR.getEntity();
            InputStream inputStream = HE.getContent();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8), 8);
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }
            inputStream.close();
            result=stringBuilder.toString();

        }catch (Exception e){
            result=e.toString();
        }finally {
            HC.getConnectionManager().shutdown();
        }
        return result;
    }
}
