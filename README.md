# native_compress
Using c language development of library based on JPEG images compression

## 简介
本压缩库使用的是JPEG的图片处理，因为历史原因，起初的Android版本内存小，Google采用的是Skia作为Android系统的图片压缩算法，这种算法压缩出来的图片在同等大小下跟IOS系统比清晰度差很多，为了达到好的压缩效果，参考资料修改了一个压缩库。


* 使用
1. gradle文件添加
> compile 'com.hzz:native_compress:1.0.0'


2. 使用
```
    /**
     * 
     * @param srcPatch 源图片路径
     * @param zipPath 压缩路径
     * @param enlableHfm 是否开启哈夫曼算法（开启压缩效果会更好，但压缩时间会增加，建议开启）
     */
    public void comress(String srcPatch,String zipPath,boolean enlableHfm){
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath);
        int code = ImageCompress.nativeCompressBitmap(bitmap, 20,zipPath, enlableHfm);

        if(code==1){
            ToastUtils.showShort("压缩成功");
        }else{
            ToastUtils.showShort("压缩失败");
        }
    }

```


* 其它
  * Bitmap 的格式必须是ARGB_8888
  >  c只做ARGB_8888的处理（默认也是这个格式），如要更改,可拷贝项目，修改app\src\cpp\compress.c 中JNI部分处理代码

  * 为了尽量使库更轻量，只生成armeabi-v7a的so文件，如要其它架构的修改 app\build.gradle ndk配置

