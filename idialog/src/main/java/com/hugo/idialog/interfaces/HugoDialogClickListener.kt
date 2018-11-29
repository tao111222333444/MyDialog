package com.hugo.idialog.interfaces

import android.view.View

/**
 * @author  作者：hugo
 * @date    时间：2018/11/28.
 * 版本：v1.0
 * 描述：点击事件接口
 */
interface HugoDialogClickListener {

    /**
     * @param view
     * @return 返回true时会dismiss 当前dialog
     */
    fun onDialogClick(view :View): Boolean
}