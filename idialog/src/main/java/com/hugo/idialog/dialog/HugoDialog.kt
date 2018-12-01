package com.hugo.idialog.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.IdRes
import android.support.annotation.StyleRes
import android.support.v7.widget.DialogTitle
import android.view.View
import android.view.Window
import com.hugo.idialog.image.CommonImageLoader
import com.hugo.idialog.interfaces.HugoDialogClickListener
import com.hugo.idialog.interfaces.HugoDialogTextChangeListener

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
    private lateinit var dialogViewHelper:HugoDialogViewHelper

    /**
     * 获取dialog里的控件
     */
    fun <T : View> getView(@IdRes viewId :Int): T? = dialogViewHelper.getView(viewId)


    fun setText(@IdRes id:Int , charSequence:CharSequence){
        dialogViewHelper.setText(id,charSequence)
    }

    fun setImageBitmap(@IdRes viewId: Int,bitmap: Bitmap){
        dialogViewHelper.setImageBitmap(viewId,bitmap)
    }

    fun setImageDrawable(@IdRes viewId: Int,drawable:Drawable){
        dialogViewHelper.setImageDrawable(viewId,drawable)
    }

    fun setImagePath(@IdRes viewId: Int,commonImageLoader: CommonImageLoader){
        dialogViewHelper.setImagePath(viewId,commonImageLoader)
    }

    fun setVisibleOrGone(@IdRes viewId: Int,isVisible :Boolean){
        dialogViewHelper.setVisibleOrGone(viewId,isVisible)
    }

    fun setVisibleOrInvisible(@IdRes viewId: Int,isVisible: Boolean){
        dialogViewHelper.setVisibleOrInvisible(viewId,isVisible)
    }

    fun setOnClickListener(@IdRes id: Int,onClickListener :HugoDialogClickListener){
        dialogViewHelper.setOnDialogClickListener(id,onClickListener)
    }

    fun addTextChangedListener(@IdRes id: Int,hugoDialogTextChangeListener: HugoDialogTextChangeListener){
        dialogViewHelper.addTextChangedListener(id,hugoDialogTextChangeListener)
    }

    fun getContentById(@IdRes id: Int) = dialogViewHelper.getContentById(id)


    fun attach(baseBuilder:HugoBaseDialogBuilder<*>){
        if (baseBuilder.isContentViewInit()) {
            dialogViewHelper = HugoDialogViewHelper(baseBuilder.mContentView)
        }

        if (!::dialogViewHelper.isInitialized) {
            throw IllegalArgumentException("the xml layout of dialog should not be null")
        }
        setContentView(dialogViewHelper.getContentView())
        dialogViewHelper.mDialog = this
        setCanceledOnTouchOutside(baseBuilder.mCancelableOutside)
        setCancelable(baseBuilder.mCancelable)

        if (baseBuilder.isOnCancelListenerInit()){
            setOnCancelListener(baseBuilder.mOnCancelListener)
        }

        if (baseBuilder.isOnDismissListenerInit()){
            setOnDismissListener(baseBuilder.mOnDismissListener)
        }

        if (baseBuilder.isOnKeyListenerInit()){
            setOnKeyListener(baseBuilder.mOnKeyListener)
        }

        val window = window
        window?.setGravity(baseBuilder.mGravity)
        if (baseBuilder.mAnimation != 0){
            window?.setWindowAnimations(baseBuilder.mAnimation)
        }
        val lp = window?.attributes
        lp?.width = (baseBuilder.mContext.resources.displayMetrics.widthPixels * baseBuilder.mWidthOffset).toInt()
        lp?.height = baseBuilder.mHeight
        window?.attributes = lp
    }

}