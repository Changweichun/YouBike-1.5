package com.android.youbike;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;

import java.util.ArrayList;

public class AccidentNotificationPhp {
    private static HttpClient HC;
    private static HttpPost HP;
    public static String AccidentNotification(String[] i, String Wcook, String url){
        String result="錯誤";
        try{
            HC=new DefaultHttpClient();
            HP=new HttpPost(url+"accident_notification.php");
            System.out.println("是否取得cookie="+Wcook);
            if (Wcook != null){
                HP.addHeader("Cookie", Wcook+";expires=Fri, 01-Jan-38 07:55:55 GMT; path=/");

                ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("NAME", i[0]));
                params.add(new BasicNameValuePair("CAT", i[1]));
                params.add(new BasicNameValuePair("ADD", i[2]));
                params.add(new BasicNameValuePair("DATE", i[3]));
                params.add(new BasicNameValuePair("PH", i[4]));
                params.add(new BasicNameValuePair("CD", i[5]));
                params.add(new BasicNameValuePair("DET", i[6]));
                params.add(new BasicNameValuePair("TIME", i[7]));

                HP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse HR = HC.execute(HP);
                HR.getEntity();
                result = "成功";}
        }catch (Exception e){
            System.out.print(e.toString());
        }finally {
            HC.getConnectionManager().shutdown();
            return result;
        }
    }
}
