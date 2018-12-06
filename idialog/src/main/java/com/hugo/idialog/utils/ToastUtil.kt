package com.hugo.idialog.utils

import android.widget.Toast
import android.support.annotation.StringRes
import android.app.Application
import android.content.Context
import android.os.Handler


/**
 * @author  作者：hugo
 * @date    时间：2018/12/4.
 * 版本：v1.0
 * 描述：提示工具
 */
object ToastUtil {
    private var mToast: Toast? = null

    private var mainHandler: Handler? = null

    private var mApplication: Application? = null

    /**
     * 在自定义Application里   onCreate()里 初始化
     * mainHandler = new Handler(getMainLooper());
     * 设置Application下的Handler
     * @param mainHander
     */
    fun initToast(mainHander: Handler, application: Application) {
        mainHandler = mainHander
        mApplication = application
    }

    /**
     * 在自定义Application onTerminate()调用
     * 在App退出时 清除mainHander
     */
    fun clear() {
        mainHandler = null
        mApplication = null
    }

    fun showToast(msg: String) {
        if (mainHandler == null || mApplication == null) {
            return
        }
        mainHandler!!.post{
            if (mToast != null) {
                mToast!!.cancel()
            }
            mToast = Toast.makeText(mApplication!!.applicationContext, msg, Toast.LENGTH_SHORT)
            mToast!!.show()
        }
    }

    fun showToastLong(msg: String) {
        if (mainHandler == null || mApplication == null) {
            return
        }
        mainHandler!!.post {
            if (mToast != null) {
                mToast!!.cancel()
            }
            mToast = Toast.makeText(mApplication!!.applicationContext, msg, Toast.LENGTH_LONG)
            mToast!!.show()
        }
    }


    fun showToast(@StringRes resId: Int) {
        if (mainHandler == null || mApplication == null) {
            return
        }
        mainHandler!!.post{
            if (mToast != null) {
                mToast!!.cancel()
            }
            mToast = Toast.makeText(mApplication!!.applicationContext, resId, Toast.LENGTH_SHORT)
            mToast!!.show()
        }
    }

    fun showToastLong(@StringRes resId: Int) {
        if (mainHandler == null || mApplication == null) {
            return
        }
        mainHandler!!.post{
            if (mToast != null) {
                mToast!!.cancel()
            }
            mToast = Toast.makeText(mApplication!!.applicationContext, resId, Toast.LENGTH_LONG)
            mToast!!.show()
        }
    }

    fun showToast(context: Context?, msg: String) {
        if (mToast != null) {
            mToast!!.cancel()
        }
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        mToast!!.show()
    }

    fun showToastLong(context: Context?, msg: String) {
        mToast?.cancel()
        mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        mToast?.show()
    }


    fun showToast(context: Context?, @StringRes resId: Int) {
        if (mToast != null) {
            mToast!!.cancel()
        }
        mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        mToast!!.show()
    }

    fun showToastLong(context: Context?, @StringRes resId: Int) {
        mToast?.cancel()
        mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG)
        mToast?.show()
    }
}