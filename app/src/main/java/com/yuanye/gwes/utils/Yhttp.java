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
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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

    public static void register(String username, String password, String phonenumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                // 封装CollegeStudent
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("", 1);
                    jsonObject.put("name", "WangXiaoNao");
                    jsonObject.put("age", 20);

                    String s = String.valueOf(jsonObject);

//                    Log.d(TAG, "run: ------>" + s);
                    URL url = new URL("http://XXX:8080/ReceiveJson");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("User-Agent", "Fiddler");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Charset", "UTF-8");
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(s.getBytes());
                    outputStream.close();
                    if (200 == connection.getResponseCode()) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

                        String line = null;
                        String responseData = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            responseData += line;

                        }

//                        Toast.makeText(MainActivity.this, "后台返回的数据:" + responseData, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

    /**
     * 使用GET访问网络
     *
     * @param username
     * @param password
     * @return 服务器返回的结果
     */
    public static String loginOfGet(String username, String password) {

        HttpURLConnection sConnection = null;

        String data = "username=" + username + "&password=" + password;

        try {
            URL url = new URL("http://XXX:8080/Login?" + data);
            sConnection = (HttpURLConnection) url.openConnection();
            sConnection.setRequestMethod("GET");
            sConnection.setConnectTimeout(10000);
            sConnection.setReadTimeout(10000);
            sConnection.connect();

            int code = sConnection.getResponseCode();
            if (code == 200) {

                InputStream is = sConnection.getInputStream();
                String state = getStringFromInputStream(is);
                return state;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (sConnection != null) {
                sConnection.disconnect();
            }

        }


        return null;

    }

    /**
     * 使用POST访问网络
     *
     * @param username
     * @param password
     * @return 服务器返回的结果
     */
    public static String LoginOfPost(String username, String password) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://XXX:8080/Login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);


            /**
             *
             *  public void setDoOutput(boolean dooutput)
             *          将此 URLConnection 的 doOutput 字段的值设置为指定的值.
             *          URL 连接可用于输入和/或输出。如果打算使用 URL 连接进行输出，
             *          则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。
             *          简单一句话：get请求的话默认就行了，post请求需要setDoOutput(true)，这个默认是false的。
             */
            connection.setDoOutput(true);

            String data = "username=" + username + "&password=" + password;
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            connection.connect();

            if (200 == connection.getResponseCode()) {
                InputStream is = connection.getInputStream();
                String state = getStringFromInputStream(is);
                return state;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;

    }
    /**
     * 使用POST访问网络
     *
     * @param username
     * @param password
     * @return 服务器返回的结果
     */
    public static String RegisterUser(String username, String password, String phonenumber) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://XXX:8080/Login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            /**
             *
             *  public void setDoOutput(boolean dooutput)
             *          将此 URLConnection 的 doOutput 字段的值设置为指定的值.
             *          URL 连接可用于输入和/或输出。如果打算使用 URL 连接进行输出，
             *          则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。
             *          简单一句话：get请求的话默认就行了，post请求需要setDoOutput(true)，这个默认是false的。
             */
            connection.setDoOutput(true);

            String data = "username=" + username + "&password=" + password + "&phonenumber" + phonenumber;
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            connection.connect();

            if (200 == connection.getResponseCode()) {
                InputStream is = connection.getInputStream();
                String state = getStringFromInputStream(is);
                return state;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    private static String getStringFromInputStream(InputStream is) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        is.close();
        String html = baos.toString();
        baos.close();

        return html;
    }


}
