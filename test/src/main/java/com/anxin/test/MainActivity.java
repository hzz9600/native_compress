package com.anxin.test;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import me.hzz.lib.ImageCompress;
import me.hzz.utillib.CameraUtils;
import me.hzz.utillib.FileUtils;
import me.hzz.utillib.ToastUtils;
import me.hzz.utillib.Utils;

public class MainActivity extends AppCompatActivity {

    private TextView mTvPath;
    private TextView mTvInfo;
    private ImageView mIvShow;
    private CheckBox mCheckBox;

    private String srcPath;
    private String zipPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvPath = (TextView)  findViewById(R.id.tv_src);
        mCheckBox= (CheckBox) findViewById(R.id.checkbox);
        mIvShow= (ImageView) findViewById(R.id.iv_show);

        Utils.init(this);

        checkReadPermission();
    }


    public void choosePic(View view){
        Intent intent = CameraUtils.getImagePickerIntent();
        startActivityForResult(intent,1);
    }


    public void compress(View view){
        if(TextUtils.isEmpty(srcPath)){
            ToastUtils.showShort("请先选择图片!");
            return;
        }
        if(checkReadPermission()){
            testCompress();
        }
    }
    public void testCompress(){
        final ProgressDialog progressDialog = ProgressDialog.show(this, "压缩中..", null);
        progressDialog.show();
        final boolean hfm=mCheckBox.isChecked();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(srcPath);
                    zipPath="/sdcard/hfresult.jpg";
                    final int code = ImageCompress.nativeCompressBitmap(bitmap, 20,zipPath, hfm);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if(code==1){
                                ToastUtils.showShort("压缩成功");
                                Bitmap bitmap1 = BitmapFactory.decodeFile(zipPath);
                                mIvShow.setImageBitmap(bitmap1);
                                appendZipFileInfo(zipPath);
                            }else{
                                ToastUtils.showShort("压缩失败");
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private boolean checkReadPermission(){
        if(checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                testCompress();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
             srcPath = CameraUtils.getChoosedImagePath(this, data);
            mTvPath.setText(srcPath);
            appendClear();
            appendSrcFileInfo(srcPath);
        }
    }

    private void appendClear(){
        mTvInfo.setText("图片信息:\n");

    }
    private void  appendSrcFileInfo(String src){
        String fileSize = FileUtils.getFileSize(src);
        mTvInfo.append("源图片大小:"+fileSize+"\n");
    }

    private void  appendZipFileInfo(String src){
        String fileSize = FileUtils.getFileSize(src);
        mTvInfo.append("压缩图片大小:"+fileSize+"\n");
    }
}
