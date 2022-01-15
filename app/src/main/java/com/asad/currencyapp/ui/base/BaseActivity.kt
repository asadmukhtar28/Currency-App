package com.asad.currencyapp.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.asad.currencyapp.R


/**
 * Abstract Activity which binds [ViewModel] [VM] and [ViewDataBinding] [VB]
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : AppCompatActivity() {
    protected abstract val viewModel: VM
    protected lateinit var viewBinding: VB
    protected lateinit var context: Context

    protected abstract fun getBindingVariable(): Int

    open fun initUi() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        viewBinding = DataBindingUtil.setContentView(this, layoutResId)
        viewBinding.setVariable(getBindingVariable(), viewModel)
        viewBinding.lifecycleOwner = this
        initUi()
    }


}
