package com.yuanye.gwes.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;

import com.yuanye.gwes.Constant.YC;
import com.yuanye.gwes.callback.VersionCheckCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Yhttp {

    public static void checkAppVersion(Context context, VersionCheckCallback callback) {
        try {
            URL url = new URL(YC.VERSION_FILE_REMOTE);
            InputStream is = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String s;
            while((s = reader.readLine()) != null){
                sb.append(s);
            }
            JSONObject jo = new JSONObject(sb.toString());
            int codeRemote = jo.getInt("code");
            callback.onSuccess(codeRemote);
//            int codeNative = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//            Log.e("yuanye", "codeRemote:"+codeRemote+" codeNative:"+codeNative);

//            if (codeRemote > codeNative){
//                callback.onSuccess(codeRemote);
//            }else if(codeRemote == codeNative){
//                String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
//                callback.onFail(""+versionName);
//            }
        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
            callback.onFail("malformed url error");
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
            callback.onFail("io error");
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
            callback.onFail("security error");
        } catch (JSONException e) {
            Log.e("SYNC getUpdate", "json解析问题", e);
            e.printStackTrace();
        }
    }

    public static void downloadFiles(Context context, String networkFile){

        try {
//            URL u = new URL("http://www.qwikisoft.com/demo/ashade/20001.kml");
            URL url = new URL(networkFile);
            InputStream is = url.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

//            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "code/version_code.txt"));
            FileOutputStream fos = new FileOutputStream(new File(context.getExternalFilesDir(null)+ "/" + "version_code.txt"));
            while ((length = dis.read(buffer))>0) {
                fos.write(buffer, 0, length);
            }
        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
    }

    public static void downloadApk(){

    }


}
