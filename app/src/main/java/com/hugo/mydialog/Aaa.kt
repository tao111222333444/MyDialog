package com.hugo.mydialog

/**
 * @author  作者：hugo
 * @date    时间：2018/11/1.
 * 版本：v1.0
 * 描述：
 */

class Aaa{
    lateinit var test: Bbb

    fun isTestInit():Boolean{

        return ::test.isInitialized
    }

    val  test1:ArrayList<String> by lazy{
        print("bbbbbbbbbbbbb\n")
        ArrayList<String>()
    }
}

class Bbb(var bbb:String)

fun  main(str:Array<String>){

    var aa = Aaa()

    Thread(){
        Thread.sleep(2)
        print("\n.........1.......\n")
        aa.test1
        aa.test1.add("aaa")
//        print(aa.test1.bbb)
    }.start()
    Thread(){
        Thread.sleep(1)
        print("\n.........2.......\n")
        aa.test1
        aa.test1.add("bbb")
    }.start()
    Thread(){
        print("\n.........3.......\n")
        aa.test1
        aa.test1.add("ccc")
    }.start()

    Thread.sleep(300)
    print("\n  test1列表个数:"+aa.test1.size)
//    while (true) {
//        if(aa.isTestInit()){
//            print(aa.test.bbb)
//            break
//        }else{
//            print("\n................\n")
//            aa.test = Bbb("aaaaaaa")
//        }
//    }
//    print("\n................\n")
//    print(aa.test1.bbb)
}