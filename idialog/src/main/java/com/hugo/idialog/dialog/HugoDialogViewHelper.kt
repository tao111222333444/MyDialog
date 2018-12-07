package com.hugo.idialog.dialog

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.util.SparseArray
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.hugo.idialog.image.CommonImageLoader
import com.hugo.idialog.interfaces.HugoDialogClickListener
import com.hugo.idialog.interfaces.HugoDialogTextChangeListener
import java.lang.ref.WeakReference

/**
 * @author  作者：hugo
 * @date    时间：2018/11/1.
 * 版本：v1.0
 * 描述：
 */
class HugoDialogViewHelper(view : View) {

    private var mContentView = view
    /**
     * dialog 布局控件的缓存列表
     */
    private var mViews:SparseArray<WeakReference<View>> = SparseArray()

    var mDialog:HugoDialog ?= null

    /**
     * 返回view
     */
    fun getContentView():View = mContentView

    /**
     * 获取对应id的控件
     */
    fun < T:View > getView( @IdRes idRes:Int): T {
        val viewWeakReference = mViews.get(idRes)
        var view: View ? = null
        if(null != viewWeakReference){
            view = viewWeakReference.get()
        }

        if(null == view){
            view = mContentView.findViewById(idRes)
            if(null != view){
                mViews.put(idRes, WeakReference(view))
            }
        }

        return view as T
    }

    /**
     * 返回控件的文案内容
     */
    fun getContentById(@IdRes id: Int) :String {
        var view = getView<View>(id)
        if(view is TextView){
            return view.text.toString()
        }else{
            return ""
        }
    }

    fun setText(@IdRes id:Int,charSequence: CharSequence){
        val view :View = getView(id)
        if (view is TextView){
            view.visibility = View.VISIBLE
            view.text = charSequence
        }
    }

    /**
     * 设置点击事件
     */
    fun setOnClickListener(@IdRes id: Int, onClickListener: View.OnClickListener){
        val view = getView<View>(id)
        view.setOnClickListener(onClickListener)
    }

    /**
     * 设置点击事件  如果用户点击就关闭当前弹窗
     */
    fun setOnDialogClickListener(@IdRes id: Int,dialogClickListener:HugoDialogClickListener){
        val view = getView<View>(id)
        view.setOnClickListener {
            val dismiss = dialogClickListener.onDialogClick(view)
            if(dismiss) {
                mDialog?.dismiss()
            }
        }
    }

    /**
     * 设置 字体颜色
     */
    fun setTextColor(@IdRes id: Int,@ColorInt color:Int){
        val view = getView<View>(id)
        if (view is TextView){
            view.setTextColor(color)
        }
    }
    /**
     * 设置 字体颜色
     */
    fun setTextColor(@IdRes id: Int,color:ColorStateList){
        val view  = getView<View>(id)
        if (view is TextView){
            view.setTextColor(color)
        }
    }

    /**
     * 设置输入框监听
     */
    fun addTextChangedListener(@IdRes id:Int , hugoDialogTextChangeListener: HugoDialogTextChangeListener){
        val view = getView<View>(id)
        if (view is EditText){
            view.addTextChangedListener(hugoDialogTextChangeListener)
        }
    }

    /**
     * 设置控件是否显示
     */
    fun setVisibleOrGone(@IdRes id: Int,isVisible:Boolean){
        val view  = getView<View>(id)
        view.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
    /**
     * 设置控件是否显示  不可见占位
     */
    fun setVisibleOrInvisible (@IdRes id: Int,isVisible: Boolean){
        val view = getView<View>(id)
        view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * 给ImageView 设置图片
     */
    fun setImageBitmap(@IdRes id: Int,bitmap: Bitmap){
        val view = getView<View>(id)
        if (view is ImageView){
            view.setImageBitmap(bitmap)
        }
    }

    /**
     * 给ImageView 设置图片
     */
    fun setImageDrawable(@IdRes id: Int,drawable: Drawable){
        val view = getView<View>(id)
        if (view is ImageView){
            view.setImageDrawable(drawable)
        }
    }

    /**
     * 传入已经实现图片框架的图片加载类
     */
    fun setImagePath(@IdRes id: Int,commonImageLoader: CommonImageLoader){
        //将第三方加载图片框架与之分离（解耦）——————这里主要参考红橙Darren的博客
        //https://www.jianshu.com/p/2c5a99984919
        val view = getView<View>(id)
        if (view is ImageView){
            commonImageLoader.loadImageView(view,commonImageLoader.getImagePath())
        }
    }
}