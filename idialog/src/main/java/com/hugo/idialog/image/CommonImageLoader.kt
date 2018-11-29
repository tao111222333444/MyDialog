package com.hugo.idialog.image

import android.widget.ImageView

/**
 * @author  作者：hugo
 * @date    时间：2018/11/28.
 * 版本：v1.0
 * 描述：这个是用来传入图片路径设置图片   这样可以将第三方图片加载框架 与之分离
 */
abstract class CommonImageLoader (private var mImagePath: String){

    /**
     * 通过链接加载图片
     */
    abstract fun loadImageView(imageView :ImageView,imagePath :String)

    fun getImagePath() = mImagePath
}