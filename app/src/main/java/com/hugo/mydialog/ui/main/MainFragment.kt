package com.hugo.mydialog.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hugo.idialog.dialog.HugoDialog
import com.hugo.idialog.interfaces.HugoDialogClickListener
import com.hugo.idialog.utils.ToastUtil
import com.hugo.mydialog.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        dialog1?.setOnClickListener {
//            showDialog1()
            var ll = FrameLayout(context)

            fl_content1.addView(ll)
            ll.addView(ll)
        }
        dialog2?.setOnClickListener{
            showDialog2()
        }
        dialog3?.setOnClickListener{
            showDialog3()
        }
        dialog4?.setOnClickListener{
            showDialog4()
        }
    }
    var mDialog:HugoDialog? = null
    private fun showDialog4(){
         if (mDialog == null) {
             mDialog = context?.let {
                 HugoDialog.HugoCommonBuilder(it)
                         .setText(R.id.tv_dialog_common_content, "这是内容")
                         .setTitle("这是标题")
                         .setNegativeContent("取消")
                         .show()
             }
         }else{
             mDialog?.setText(R.id.tv_dialog_common_content, "这是内容111")
             mDialog?.show()
         }
    }
    private fun showDialog3(){
        var mDialog = context?.let {
            HugoDialog.MDBuilder(it).setTitle("这是标题")
                    .setNegativeContent("否")
                    .setPositiveContentAndListener("是", object : HugoDialogClickListener {
                        override fun onDialogClick(view: View): Boolean {
                            ToastUtil.showToast(context,"是的是的")
                            return true
                        }
                    })
                    .setMessage("你是最帅的！")
                    .show()
        }
    }

    private fun showDialog2(){
        var mDialog = context?.let {
            HugoDialog.CommonBuilder(it)
                    .setContentView(R.layout.dialog_common1)
                    .setFromBottom()
                    .setFullWidth()
                    .setText(R.id.dcu_tv_title,"这是标题")
                    .setText(R.id.dcu_tv_cancel,"取消")
                    .setOnCilckListener(R.id.dcu_tv_cancel, object : HugoDialogClickListener {
                        override fun onDialogClick(view: View): Boolean {
                            ToastUtil.showToast(context,"这是返回")
                            return true
                        }
                    })
                    .setText(R.id.dcu_tv_confirm,"hhhhhh")
                    .isShowSoftInput(true)
                    .setAnimation(R.style.dialog_from_bottom_anim)
                    .show()
        }
    }

    private fun showDialog1(){
        var mDialgo = context?.let {
            HugoDialog.CommonBuilder(it)
                .setContentView(R.layout.dialog_material)
                .setText(R.id.tv_dialog_material_cancel,"取消")
                .setOnCancelListener( DialogInterface.OnCancelListener {
                    ToastUtil.showToastLong(context,"这是返回了")
                })
                .setOnCilckListener(R.id.tv_dialog_material_cancel, object : HugoDialogClickListener {
                    override fun onDialogClick(view: View): Boolean {
                        ToastUtil.showToastLong(context,"返回事件")
                        return true
                    }
                })
                .setOnCilckListener(R.id.tv_dialog_material_confirm, object : HugoDialogClickListener {
                    override fun onDialogClick(view: View): Boolean {
                        ToastUtil.showToastLong(context,"点击事件")
                        return false
                    }
                })
                .setText(R.id.tv_dialog_material_confirm,"确认")
                .setTextColor(R.id.tv_dialog_material_title,R.color.colorPrimaryDark)
                .setText(R.id.tv_dialog_material_content,"这是内容提示这是内容提示这是内容提示这是内容提示这是内容提示这是内容提示这是内容提示这是内容提示")
                .setText(R.id.tv_dialog_material_title,R.string.pref_title_vibrate)
                .show()
        }

    }

}
