package com.yuanye.gwes;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.yuanye.gwes.Constant.YC;
import com.yuanye.gwes.R;
import com.yuanye.gwes.callback.VersionCheckCallback;
import com.yuanye.gwes.utils.Yhttp;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class CheckActivity extends AppCompatActivity {

    private Handler handler = new MyHandler();
    private Context context;
    private DownloadManager downloadManager;
    private long downloadId;
    private BroadcastReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterMyReceiver();
    }

    public void check(View v){
        checkAppVersion();
    }

    private void checkAppVersion() {
        final VersionCheckCallback callback = new VersionCheckCallback() {
            @Override
            public void onSuccess(int codeRemote) {
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = codeRemote;
                handler.sendMessage(msg);
            }

            @Override
            public void onFail(String info) {
                Message msg = Message.obtain();
                msg.what = -1;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                Yhttp.checkAppVersion(context, callback);
            }
        }).start();
    }

    private void showUpdateDialog(int state, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        switch (state){
            case 0:
                builder.setMessage("检测到有更新，新版本号："+msg)
                .setPositiveButton("下载更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (downloadApk() != -1){
                            registerMyReceiver();
                        }
                    }
                })
                .setNegativeButton("下次再说", null);
                break;
            case 1:
                builder.setMessage("当前已经是最新版本");
                break;
            case -1:
                builder.setMessage(msg);
                break;
        }
        builder.setTitle("版本检测").create().show();

    }

    private long downloadApk() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
        Cursor cursor = downloadManager.query(query);
        if (!cursor.moveToNext()){
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(YC.APK_FILE_REMOTE));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle("GWES.apk下载");
            request.setDestinationInExternalFilesDir(this, "apk", "GWES.apk");
            downloadId = downloadManager.enqueue(request);
            return downloadId;
        }
        cursor.close();
        return -1;
    }

    private void installApk(String fileName) {
        File file = new File(Uri.parse(fileName).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            data = FileProvider.getUriForFile(context, "com.yuanye.gwes.fileProvider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void registerMyReceiver(){
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(myReceiver, intentFilter);
    }

    private void unregisterMyReceiver(){
        if (myReceiver != null){
            unregisterReceiver(myReceiver);
        }
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                int codeRemote = (int) msg.obj;
                int codeNative = 0;
                try {
                    codeNative = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (codeRemote > codeNative){
                    showUpdateDialog(0, ""+codeRemote);
                }else if (codeNative == codeRemote){
                    showUpdateDialog(1, ""+codeRemote);
                }else{
                    showUpdateDialog(-1, "未知错误");
                }

            }else{
                showUpdateDialog(-1, "检测出错："+msg.obj);
            }
        }
    }

    class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
            long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (ID == downloadId){
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()){
                    String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    Log.d("MyReceiver", fileName);
                    if (fileName != null){
                        installApk(fileName);
                    }
                }
                cursor.close();
            }
        }
    }


}
