# MyDialog
这是一个用kotlin封装的builder模式的dialog

这个项目主要是根据[https://github.com/AndroidFriendsGroup/FRDialog](https://github.com/AndroidFriendsGroup/FRDialog)

来写的kotlin版本
在这个项目里面  封装了统一的风格HugoCommonBuilder 和 通用自定义CommonBuilder   MD风格的MDBuilder
如果想自己定义builder 直接继承HugoBaseDialogBuilder就行了

使用案例

这是同一风格

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
    
这是MD风格

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


这是自定义风格

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

