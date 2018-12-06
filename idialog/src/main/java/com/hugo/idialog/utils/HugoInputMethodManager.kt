package com.hugo.idialog.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author  作者：hugo
 * @date    时间：2018/12/3.
 * 版本：v1.0
 * 描述：
 */
class HugoInputMethodManager {
    //使用伴生对象来实现java的静态方法   可以省略伴生对象名称
    companion object HugoInputMethodManagerCompanion{


        /**
         * 获取输入方法管理服务
         */
        fun getInputMethodManager(context: Context?): InputMethodManager {
            return context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }

        /**
         * 显示软键盘
         */
        fun showSoftInput(context: Context?, view: View?) {
            var inputMethodManager = getInputMethodManager(context)
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

        fun showSoftInput(view: View?) {
            showSoftInput(view?.context, view)
        }

        fun showSoftInput(activity: Activity?) {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        /**
         * 隐藏软键盘
         */
        fun hideSoftInput(context: Context, view: View?) {
            //如果windowToken 为null 则退出该方法（return）
            val token: IBinder? = view?.windowToken ?: return

            val inputMethodManager = getInputMethodManager(context)
            inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        fun hideSoftInput(view: View) {
            hideSoftInput(view.context, view)
        }

        fun hideSoftInput(activity: Activity) {
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//        hideSoftInput(activity,activity.currentFocus)
        }

        /**
         * 软键盘状态转换  如果已经显示  就隐藏 否则就 显示
         */
        fun toggleSoftInput(context: Context) {
            val inputMethodManager = getInputMethodManager(context)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        /**
         * 判断软键盘是否显示
         */
        fun isSoftInputShowing(activity: Activity): Boolean {
            val screenHeight = activity.window.decorView.height
            val rect = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(rect)
            return screenHeight - rect.bottom != 0
        }


        private fun isAutoHideSoftInput(view: View, event: MotionEvent): Boolean {
            if (event.action != MotionEvent.ACTION_DOWN) {
                return false
            }

            if (view !is EditText) {
                return false
            }

            val x = event.x
            val y = event.y
            var location: IntArray = intArrayOf(0, 0)
            view.getLocationInWindow(location)
            val left = location[0]
            val top = location[1]
            val bottom = top + view.height
            val right = left + view.width
            if (left <= x && x < right && top <= y && y < bottom) {
                //点击事件在EditText的区域里
                return false
            }
            return true
        }

        /**
         * 自动判断是否隐藏
         */
        fun autoHideSoftInput(activity: Activity, event: MotionEvent) {
            //elvis 操作符
            val focusView = activity.currentFocus ?: return
            if (isAutoHideSoftInput(focusView, event)) {
                hideSoftInput(activity, focusView)
            }
        }

        fun autoHideSoftInput(view: View?, motionEvent: MotionEvent) {

            if (isAutoHideSoftInput(view?:return, motionEvent)) {
                hideSoftInput(view)
            }
        }
    }
}