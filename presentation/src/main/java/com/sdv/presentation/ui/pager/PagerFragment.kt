package com.sdv.presentation.ui.pager

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.observe
import com.sdv.presentation.R
import com.sdv.presentation.adapters.PostPagerAdapter
import com.sdv.presentation.base.BaseFragment
import com.sdv.presentation.databinding.PagerFragmentBinding
import com.sdv.presentation.ui.pager.user.UsersViewModel
import kotlinx.android.synthetic.main.pager_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagerFragment : BaseFragment() {
    override val viewModel by sharedViewModel<UsersViewModel>()

    override fun layout(): Int = R.layout.pager_fragment

    override fun initialization(view: View, isFirstInit: Boolean) {
        val fragmentAdapter =
            PostPagerAdapter(
                requireFragmentManager()
            )

        fragmentAdapter.setTitleName(getString(R.string.users_tab), getString(R.string.posts_tab))
        viewpager_main.adapter = fragmentAdapter
        viewpager_main.setPagingEnabled(false)
        tabs_main.setupWithViewPager(viewpager_main)
        observeChanges()
    }

    override fun dataBind(view: View): ViewDataBinding {
        return PagerFragmentBinding.bind(view).apply {
            viewmodel = viewModel
        }
    }

    override fun observeChanges() {
        viewModel.changePage.observe(viewLifecycleOwner){
            viewpager_main.currentItem = 1
        }
    }

}