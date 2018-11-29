package com.hugo.idialog.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.v7.widget.DialogTitle
import android.view.View

/**
 * @author  作者：hugo
 * @date    时间：2018/11/1.
 * 版本：v1.0
 * 描述： dialog 主类
 */

class HugoDialog(context: Context, @StyleRes themeResIdRes: Int) : Dialog(context,themeResIdRes) {
    /**
     * dialog铺助工具类
     */
    private var dialogViewHelper:HugoDialogViewHelper ?= null

    /**
     * 获取dialog里的控件
     */
    fun <T : View> getView(@IdRes viewId :Int): T? = dialogViewHelper?.getView(viewId)


    fun setText(@IdRes id:Int , charSequence:CharSequence){
        dialogViewHelper?.setText(id,charSequence)
    }

    fun setImageBitmap(@IdRes viewId: Int,bitmap: Bitmap){
//        dialogViewHelper
    }
}