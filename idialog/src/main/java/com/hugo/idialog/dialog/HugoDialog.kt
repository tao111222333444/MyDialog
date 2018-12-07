package com.hugo.idialog.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hugo.idialog.R
import com.hugo.idialog.image.CommonImageLoader
import com.hugo.idialog.interfaces.HugoDialogClickListener
import com.hugo.idialog.interfaces.HugoDialogTextChangeListener
import com.hugo.idialog.utils.HugoInputMethodManager

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

    fun setText(@IdRes id : Int,@StringRes stringId:Int):HugoDialog{
        return setText(id,context.getString(stringId))
    }
    fun setText(@IdRes id:Int , charSequence:CharSequence):HugoDialog{
        dialogViewHelper.setText(id,charSequence)
        return this
    }

    fun setImageBitmap(@IdRes viewId: Int,bitmap: Bitmap):HugoDialog{
        dialogViewHelper.setImageBitmap(viewId,bitmap)
        return this
    }

    fun setImageDrawable(@IdRes viewId: Int,drawable:Drawable):HugoDialog{
        dialogViewHelper.setImageDrawable(viewId,drawable)
        return this
    }

    fun setImagePath(@IdRes viewId: Int,commonImageLoader: CommonImageLoader):HugoDialog{
        dialogViewHelper.setImagePath(viewId,commonImageLoader)
        return this
    }

    fun setVisibleOrGone(@IdRes viewId: Int,isVisible :Boolean):HugoDialog{
        dialogViewHelper.setVisibleOrGone(viewId,isVisible)
        return this
    }

    fun setVisibleOrInvisible(@IdRes viewId: Int,isVisible: Boolean):HugoDialog{
        dialogViewHelper.setVisibleOrInvisible(viewId,isVisible)
        return this
    }

    fun setOnClickListener(@IdRes id: Int,onClickListener :HugoDialogClickListener):HugoDialog{
        dialogViewHelper.setOnDialogClickListener(id,onClickListener)
        return this
    }

    fun addTextChangedListener(@IdRes id: Int,hugoDialogTextChangeListener: HugoDialogTextChangeListener):HugoDialog{
        dialogViewHelper.addTextChangedListener(id,hugoDialogTextChangeListener)
        return this
    }

    fun getContentById(@IdRes id: Int) = dialogViewHelper.getContentById(id)


    /**
     * 初始化
     */
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
        val maxHeight = (baseBuilder.mContext.resources.displayMetrics.heightPixels * baseBuilder.mHeightOffset).toInt()
        //如果超过了比例高度则就按比例高度来设置
        lp?.height = if(baseBuilder.mHeight > maxHeight) maxHeight else baseBuilder.mHeight
        window?.attributes = lp
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        HugoInputMethodManager.autoHideSoftInput(this.currentFocus,ev)
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 获取dialog帮助类
     */
    fun getDialogViewHelper() = dialogViewHelper

    /**
     * 通用dialog构造类
     */
    class CommonBuilder(context: Context,themeResIdRes: Int) : HugoBaseDialogBuilder<CommonBuilder>(context,themeResIdRes){
        constructor(context: Context) : this(context, R.style.dialog)

        fun setContentView(view:View):CommonBuilder{
            mContentView = view
            return this
        }

        fun setContentView(@LayoutRes layoutRes: Int):CommonBuilder{
            if (layoutRes != 0){
                setContentView(LayoutInflater.from(mContext).inflate(layoutRes,null))
            }
            return this
        }
    }

    /**
     * MD风格dialog构造类
     */
    class MDBuilder(context: Context,themeResIdRes: Int):HugoBaseDialogBuilder<MDBuilder>(context,themeResIdRes){
        constructor(context: Context):this(context,R.style.dialog)

        init {
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_material,null)
        }


        /**
         * 设置dialog标题
         */
        fun setTitle(charSequence: CharSequence):MDBuilder{
            mTextArray.put(R.id.tv_dialog_material_title,charSequence)
            return this
        }

        /**
         * 设置MD效果内容
         */
        fun setMessage(charSequence: CharSequence):MDBuilder{
            mTextArray.put(R.id.tv_dialog_material_content,charSequence)
            return this
        }

        /**
         * 设置取消事件和取消按钮文案
         */
        fun setNegativeContentAndListener(charSequence: CharSequence,onClickListener: HugoDialogClickListener):MDBuilder{
            mTextArray.put(R.id.tv_dialog_material_cancel,charSequence)
            mClickListenerArray.put(R.id.tv_dialog_material_cancel,onClickListener)
            return this
        }

        /**
         * 设置取消按钮文案  默认事件为关闭dialog
         */
        fun setNegativeContent(charSequence: CharSequence):MDBuilder{
            setNegativeContentAndListener(charSequence, object : HugoDialogClickListener {
                override fun onDialogClick(view: View): Boolean {
                    return true
                }
            })
            return this
        }

        /**
         * 设置确认按钮文案和点击事件
         */
        fun setPositiveContentAndListener(charSequence: CharSequence,onClickListener: HugoDialogClickListener):MDBuilder{
            mTextArray.put(R.id.tv_dialog_material_confirm,charSequence)
            mClickListenerArray.put(R.id.tv_dialog_material_confirm,onClickListener)
            return this
        }

        /**
         * 设置取消按钮字体颜色
         */
        fun setNegativeTextColor(@ColorInt color: Int):MDBuilder{
            mTextColorArray.put(R.id.tv_dialog_material_cancel,color)
            return this
        }

        /**
         * 设置取消按钮字体颜色
         */
        fun setNegativeTextColor(color:ColorStateList):MDBuilder{
            mTextColorStateListArray.put(R.id.tv_dialog_material_cancel,color)
            return this
        }

        /**
         * 设置确认键文案颜色
         */
        fun setPositiveTextColor(@ColorInt color: Int):MDBuilder{
            mTextColorArray.put(R.id.tv_dialog_material_confirm,color)
            return this
        }

        /**
         * 设置确认按钮颜色
         */
        fun setPositiveTextColor(color:ColorStateList):MDBuilder{
            mTextColorStateListArray.put(R.id.tv_dialog_material_confirm,color)
            return this
        }
    }


    /**
     * 项目统一风格dialog构造类
     */
    class HugoCommonBuilder(context: Context,themeResIdRes: Int):HugoBaseDialogBuilder<HugoCommonBuilder>(context,themeResIdRes){
        constructor(context: Context):this(context,R.style.dialog)

        init {
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_common,null)
        }

        /**
         * 内容块的布局
         */
        private lateinit var view:View
        /**
         * 是否设置了 Merge 标签布局
         */
        private var isMergeLayout = false

        /**
         * 判断是否设置 取消按钮
         */
        private var isSetCancel = false
        /**
         * 判断是否设置 确认按钮
         */
        private var isSetConfirm=false

        /**
         *  设置内容块的布局
         */
        fun setConentLayout(view: View):HugoCommonBuilder{
            this.view = view
            return this
        }
        /**
         *  设置内容块的布局的  @LayoutRes
         */
        fun setConentLayout(@LayoutRes resId:Int):HugoCommonBuilder{
            if (resId != 0 ){
                setConentLayout(LayoutInflater.from(mContext).inflate(resId,null))
            }
            return this
        }

        fun setMergeConentLayout(@LayoutRes resId:Int):HugoCommonBuilder {
            if (resId != 0 ){
                LayoutInflater.from(mContext).inflate(resId,getView<FrameLayout>(R.id.fl_content),true)
                isMergeLayout = true
            }
            return this
        }

        /**
         * 设置dialog标题
         */
        fun setTitle(charSequence: CharSequence):HugoCommonBuilder{
            mTextArray.put(R.id.tv_dialog_common_title,charSequence)
            return this
        }


        /**
         * 设置取消事件和取消按钮文案
         */
        fun setNegativeContentAndListener(charSequence: CharSequence,onClickListener: HugoDialogClickListener):HugoCommonBuilder{
            mTextArray.put(R.id.tv_dialog_common_cancel,charSequence)
            mClickListenerArray.put(R.id.tv_dialog_common_cancel,onClickListener)
            isSetCancel = true
            return this
        }

        /**
         * 设置取消按钮文案  默认事件为关闭dialog
         */
        fun setNegativeContent(charSequence: CharSequence):HugoCommonBuilder{
            setNegativeContentAndListener(charSequence, object : HugoDialogClickListener {
                override fun onDialogClick(view: View): Boolean {
                    return true
                }
            })
            return this
        }


        /**
         * 设置确认按钮文案和点击事件
         */
        fun setPositiveContentAndListener(charSequence: CharSequence,onClickListener: HugoDialogClickListener):HugoCommonBuilder{
            mTextArray.put(R.id.tv_dialog_common_confirm,charSequence)
            mClickListenerArray.put(R.id.tv_dialog_common_confirm,onClickListener)
            isSetConfirm = true
            return this
        }

        /**
         * 设置取消按钮字体颜色
         */
        fun setNegativeTextColor(@ColorInt color: Int):HugoCommonBuilder{
            mTextColorArray.put(R.id.tv_dialog_common_cancel,color)
            return this
        }

        /**
         * 设置取消按钮字体颜色
         */
        fun setNegativeTextColor(color:ColorStateList):HugoCommonBuilder{
            mTextColorStateListArray.put(R.id.tv_dialog_common_cancel,color)
            return this
        }

        /**
         * 设置确认键文案颜色
         */
        fun setPositiveTextColor(@ColorInt color: Int):HugoCommonBuilder{
            mTextColorArray.put(R.id.tv_dialog_common_confirm,color)
            return this
        }

        /**
         * 设置确认按钮颜色
         */
        fun setPositiveTextColor(color:ColorStateList):HugoCommonBuilder{
            mTextColorStateListArray.put(R.id.tv_dialog_common_confirm,color)
            return this
        }


        /**
         * 初始控件
         */
        override fun attachView():Boolean{
            //如果有赋值就添加进  容器
            if (::view.isInitialized ){
                getView<FrameLayout>(R.id.fl_content)?.addView(view)
            }else if (!isMergeLayout){
                //如果没有赋值也没有设置  merge布局就设置默认布局
                setMergeConentLayout(R.layout.dialog_commom_default_content)
            }

            if (!isSetCancel && !isSetConfirm){
                //如果都没有设置  默认一个取消按钮
                setPositiveContentAndListener(mContext.getString(R.string.dialog_default_confirm), object : HugoDialogClickListener {
                    override fun onDialogClick(view: View): Boolean {
                        return true
                    }
                })
                getView<View>(R.id.tv_dialog_common_confirm)?.setBackgroundResource(R.drawable.dialog_center_btn_bg)
                setVisibleOrGone(R.id.tv_dialog_common_cancel,false)
                setVisibleOrGone(R.id.dialog_line_3,false)
            }else if (isSetCancel&& !isSetConfirm){
                //如果只设置了取消按钮  就把确定按钮隐藏
                getView<View>(R.id.tv_dialog_common_cancel)?.setBackgroundResource(R.drawable.dialog_center_btn_bg)
                setVisibleOrGone(R.id.tv_dialog_common_confirm,false)
                setVisibleOrGone(R.id.dialog_line_3,false)
            }else if (isSetConfirm && !isSetCancel){
                //如果只设置了确定按钮  就把取消按钮隐藏
                getView<View>(R.id.tv_dialog_common_confirm)?.setBackgroundResource(R.drawable.dialog_center_btn_bg)
                setVisibleOrGone(R.id.tv_dialog_common_cancel,false)
                setVisibleOrGone(R.id.dialog_line_3,false)
            }


            return super.attachView()
        }

    }
}