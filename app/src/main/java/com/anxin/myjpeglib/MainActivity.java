package com.anxin.myjpeglib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import me.hzz.utillib.CameraUtils;
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
    }

}
