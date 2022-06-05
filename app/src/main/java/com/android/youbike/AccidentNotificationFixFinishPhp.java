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

public class AccidentNotificationFixFinishPhp {
    private static HttpClient HC;
    private static HttpPost HP;

    public static void accidentNotificationFixFinishPhp(String id, String account, String Wcook, String url){
        try{
            HC =new DefaultHttpClient();
            HP=new HttpPost(url+"repairFinish.php");
            System.out.println("是否取得cookie="+Wcook);
            HP.addHeader("Cookie", Wcook+";expires=Fri,01-Jan-38 07:55:55 GMT; path=/");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", id));
            params.add(new BasicNameValuePair("PH", account));
            HP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse HR = HC.execute(HP);
            HR.getEntity();

        }catch (Exception e){
            System.out.print(e.toString());
        }
    }

}
