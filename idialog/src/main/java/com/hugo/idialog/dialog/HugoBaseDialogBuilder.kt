package com.hugo.idialog.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.hugo.idialog.R
import com.hugo.idialog.image.CommonImageLoader
import com.hugo.idialog.interfaces.HugoDialogClickListener

/**
 * @author  作者：hugo
 * @date    时间：2018/11/30.
 * 版本：v1.0
 * 描述：
 */
open class HugoBaseDialogBuilder<BUILDER : HugoBaseDialogBuilder<BUILDER>>(

        var mContext: Context,
        /**
         * dialog主题
         */
        var mThemeResId: Int){

    lateinit var mContentView : View

    lateinit var mDialog :HugoDialog

    lateinit var mDialogViewHelper:HugoDialogViewHelper

    /**
     * 点击返回键时候dismiss
     */
    var mCancelable = true
    /**
     * 设置点击dialog以外的区域是否消失dialog
     */
    var mCancelableOutside = true
    /**
     * 是否弹出软键盘
     */
    var isShowSoftInput = false
    /**
     * dialog监听事件
     */
    lateinit var mOnDismissListener :DialogInterface.OnDismissListener
    lateinit var mOnCancelListener:DialogInterface.OnCancelListener
    lateinit var mOnKeyListener:DialogInterface.OnKeyListener



    /**
     * dialog布局上的文案
     */
    val mTextArray:SparseArray<CharSequence> by lazy { SparseArray<CharSequence>() }
    /**
     * dialog布局上的文案颜色
     */
    val mTextColorArray : SparseIntArray by lazy { SparseIntArray() }
    /**
     * dialog布局上的文案颜色
     */
    val mTextColorStateListArray :SparseArray<ColorStateList> by lazy { SparseArray<ColorStateList>() }
    /**
     * dialog上控件的点击事件
     */
    val mClickListenerArray :SparseArray<HugoDialogClickListener> by lazy { SparseArray<HugoDialogClickListener>() }
    /**
     * 图片列表
     */
    val mImageBitmapArray :SparseArray<Bitmap> by lazy { SparseArray<Bitmap>() }
    val mImageDrawableArray :SparseArray<Drawable> by lazy { SparseArray<Drawable>() }
    val mImageCommonImageLoaderArray :SparseArray<CommonImageLoader> by lazy { SparseArray<CommonImageLoader>() }

    /**
     * dialog宽度占屏幕宽度的比例
     */
    var mWidthOffset = 0.9
    /**
     * dialog最高高度占屏幕的比例  默认三分之二
     */
    var mHeightOffset = 2f/3
    /**
     * dialog 弹窗
     */
    var mHeight  = ViewGroup.LayoutParams.WRAP_CONTENT
    /**
     * dialog位置
     */
    var mGravity = Gravity.CENTER
    /**
     * dialog动画
     */
    var mAnimation = R.style.default_dialog_anim

    /**
     * 判断mOnDismissListener是否初始化
     */
    fun isOnDismissListenerInit():Boolean{
        return ::mOnDismissListener.isInitialized
    }
    /**
     * 判断mOnCancelListener是否初始化
     */
    fun isOnCancelListenerInit():Boolean{
        return ::mOnCancelListener.isInitialized
    }
    /**
     * 判断mOnKeyListener是否初始化
     */
    fun isOnKeyListenerInit():Boolean{
        return ::mOnKeyListener.isInitialized
    }
    /**
     * 判断ContentVIew是否初始化
     */
    fun isContentViewInit():Boolean{
        return ::mContentView.isInitialized
    }

    /**
     * 设置dialog宽度全屏
     */
    fun  setFullWidth() :BUILDER{
        mWidthOffset = 1.0
        return builder()
    }

    /**
     * 设置dialog宽度比例
     */
    fun setWidthOffset(widthOffset:Double) : BUILDER{
        mWidthOffset = widthOffset
        return builder()
    }

    /**
     * 设置dialog最高高度占屏幕的比例
     */
    fun setHeightOffset(heightOffset:Float):BUILDER{
        mHeightOffset = heightOffset
        return builder()
    }

    /**
     * 设置dialog高度
     */
    fun setHeight(height : Int):BUILDER {
        mHeight = height
        return builder()
    }

    /**
     * 设置dialog从底部弹出
     */
    fun setFromBottom():BUILDER{
        mAnimation = R.style.dialog_from_bottom_anim;
        mGravity = Gravity.BOTTOM
        return builder()
    }

    /**
     * 设置其他dialog动画
     */
    fun setAnimation(@StyleRes animation : Int):BUILDER{
        mAnimation = animation
        return builder()
    }

    fun isShowSoftInput(isShow:Boolean):BUILDER{
        isShowSoftInput = isShow
        return builder()
    }

    /**
     * 设置OnCancelListener 监听
     */
    fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener):BUILDER{
        mOnCancelListener = onCancelListener
        return builder()
    }

    /**
     * 设置OnDismissListener 监听
     */
    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener):BUILDER{
        mOnDismissListener = onDismissListener
        return builder()
    }

    /**
     * 设置OnkeyListener 监听
     */
    fun setOnKeyListener(onKeyListener: DialogInterface.OnKeyListener):BUILDER{
        mOnKeyListener = onKeyListener
        return builder()
    }

    /**
     * 设置点击返回键是否小时Dialog
     */
    fun setCancelable(isCancelable :Boolean):BUILDER{
        mCancelable = isCancelable
        return builder()
    }

    /**
     * 设置点击dialog以外的区域是否消失dialog
     */
    fun setCancelableOutside(isCancelableOutside:Boolean):BUILDER{
        mCancelableOutside = isCancelableOutside
        return builder()
    }

    fun setText(@IdRes id : Int,@StringRes stringId:Int):BUILDER{
            setText(id, mContext.getString(stringId))
        return builder()
    }

    fun setText(@IdRes id : Int,charSequence: CharSequence):BUILDER{
        mTextArray.put(id,charSequence)
        return builder()
    }

    fun setTextColor(@IdRes id: Int,@ColorRes color:Int):BUILDER{
        mTextColorArray.put(id,color)
        return builder()
    }

    fun setTextColor(@IdRes id: Int,color:ColorStateList):BUILDER{
        mTextColorStateListArray.put(id,color)
        return builder()
    }

    fun setImage(@IdRes id: Int,bitmap: Bitmap):BUILDER{
        mImageBitmapArray.put(id,bitmap)
        return builder()
    }

    fun setImage(@IdRes id: Int,drawable: Drawable):BUILDER{
        mImageDrawableArray.put(id,drawable)
        return builder()
    }

    fun setImage(@IdRes id: Int,commonImageLoader: CommonImageLoader):BUILDER{
        mImageCommonImageLoaderArray.put(id,commonImageLoader)
        return builder()
    }

    /**
     * 设置点击事件
     */
    fun setOnCilckListener(@IdRes id: Int,hugoDialogClickListener: HugoDialogClickListener):BUILDER{
        mClickListenerArray.put(id,hugoDialogClickListener)
        return builder()
    }

    private fun builder():BUILDER  {
        return this as BUILDER
    }



    /**
     * 获取对应id View
     */
    fun <VIEW : View> getView(@IdRes viewId:Int): VIEW? {
        if (::mDialogViewHelper.isInitialized){
            return mDialogViewHelper.getView(viewId)
        }
        return null
    }

    /**
     * 创建dialog
     */
    fun create():HugoDialog {
        if (!::mDialog.isInitialized){
            mDialog = HugoDialog(mContext,mThemeResId)
            if (isShowSoftInput) {
                mDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            mDialog.attach(this)
            mDialogViewHelper = mDialog.getDialogViewHelper()
            attachView()
        }
        return mDialog
    }

    /**
     * 显示弹窗
     */
    fun show():HugoDialog{
        if(::mDialog.isInitialized){
            mDialog.show()
        }else{
            create().show()
        }
        return mDialog
    }

    fun attachView():Boolean{
        /**
         * 设置文案 和 颜色
         * */
        for (i in 0 until  mTextArray.size()){
            mDialogViewHelper.setText(mTextArray.keyAt(i),mTextArray.valueAt(i))
        }

        repeat(mTextColorArray.size()){
            mDialogViewHelper.setTextColor(mTextColorArray.keyAt(it),mTextColorArray.valueAt(it))
        }

        repeat(mTextColorStateListArray.size()){
            mDialogViewHelper.setTextColor(mTextColorStateListArray.keyAt(it),mTextColorStateListArray.valueAt(it))
        }

        /**
         * 点击事件
         * */
        repeat(mClickListenerArray.size()){
            mDialogViewHelper.setOnDialogClickListener(mClickListenerArray.keyAt(it),mClickListenerArray.valueAt(it))
        }

        /**
         * 图片
         */
        repeat(mImageBitmapArray.size()){
            mDialogViewHelper.setImageBitmap(mImageBitmapArray.keyAt(it),mImageBitmapArray.valueAt(it))
        }

        repeat(mImageDrawableArray.size()){
            mDialogViewHelper.setImageDrawable(mImageDrawableArray.keyAt(it),mImageDrawableArray.valueAt(it))
        }

        for (i in 0 until mImageCommonImageLoaderArray.size()){
            mDialogViewHelper.setImagePath(mImageCommonImageLoaderArray.keyAt(i),mImageCommonImageLoaderArray.valueAt(i))
        }

        return true
    }
}