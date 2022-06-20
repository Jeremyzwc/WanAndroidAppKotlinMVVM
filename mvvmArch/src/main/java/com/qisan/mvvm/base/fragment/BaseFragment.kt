package com.qisan.mvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.qisan.baselib.ext.saveAs
import com.qisan.baselib.ext.saveAsUnChecked
import com.qisan.mvvm.base.dialog.LoadingDialog

import java.lang.reflect.ParameterizedType

/**
 * Created by qisan 2022/6/20
 * com.qisan.wanandroid.base
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(){

    protected var viewBinding: VB? = null
        get() = _viewBinding

    private val _viewBinding: VB by lazy {
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(this, layoutInflater)!!.saveAsUnChecked()
    }

    protected val loadingDialog by lazy { activity?.let { LoadingDialog(it) } }

    protected var isShowLoadingLayout = false
    protected var isShowErrorLayout = false
    protected lateinit var errorMsg : String
    /**
     * 是否初始化过布局
     */
    protected var isPrepared: Boolean = false
    /**
     * 是否加载过数据
     */
    protected var isDataInitiated: Boolean = false


    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isPrepared = true
        initMenu()
        return viewBinding?.root
    }

    open fun initMenu(){}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //通过在onResume中实现懒加载
    override fun onResume() {
        super.onResume()
        if(!isDataInitiated && isPrepared){
            initData()
            initListener()
            isDataInitiated = true
        }
    }

    protected abstract fun initData()

    open fun initListener(){}

    override fun onDestroyView() {
        super.onDestroyView()

        viewBinding = null
        isDataInitiated = false
        isPrepared = false
    }

    fun showDialogloading(desc: String) {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        loadingDialog?.showLoading(desc)
    }

    fun showDialogloading() {

        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        loadingDialog?.showLoading()
    }

    open fun scrollToTop(){

    }
}