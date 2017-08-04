package me.hzz.lib;

import android.graphics.Bitmap;

/**
 * Created by Huang、 on 2017/8/4.
 */

public class ImageCompress {
    static {
        System.loadLibrary("compress");
    }

    /**
     * 使用native方法进行图片压缩
     * Bitmap的格式必须是ARGB_8888 {@link Bitmap.Config}。
     *
     * @param bitmap 图片数据
     * @param quality 压缩质量
     * @param dstFile 压缩位置
     * @param optimize 是否开启哈夫曼算法
     * @return 0，压缩成功，其它 压缩失败
     */
    public static native int nativeCompressBitmap(Bitmap bitmap,int quality,String dstFile,boolean optimize);
}
