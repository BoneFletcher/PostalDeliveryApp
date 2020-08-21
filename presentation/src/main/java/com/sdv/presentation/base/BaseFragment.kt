package com.sdv.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


abstract class BaseFragment : Fragment() {
    protected var rootView: View? = null
    var isVisible: (fragment: Fragment) -> Boolean = { true }
  //  protected val clicksFilter = ClicksFilter()

    @Suppress("LeakingThis")
    abstract val viewModel: ViewModel

    @LayoutRes
    protected abstract fun layout(): Int
    protected abstract fun initialization(view: View, isFirstInit: Boolean)

    protected abstract fun dataBind(view: View): ViewDataBinding
    protected abstract fun observeChanges()

    lateinit var viewDataBinding: ViewDataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialization(view, rootView == null)
        rootView = view
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = if (layout() != 0)
            trueView(inflater, container)
        else
            super.onCreateView(inflater, container, savedInstanceState)
        return if (rootView == null) view else rootView
    }

    private fun trueView(inflater: LayoutInflater, container: ViewGroup?): View? {
        val view = inflater.inflate(layout(), container, false)
        viewDataBinding = dataBind(view)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }


//
//    fun View.onClick(durationMillis: Long, action: (view: View) -> Unit) {
//        setOnClickListener {
//            clicksFilter.debounce(durationMillis, it, action)
//        }
//    }
}