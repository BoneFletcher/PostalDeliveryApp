package com.sdv.presentation.ui.pager.user

import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.sdv.domain.model.response.User
import com.sdv.presentation.R
import com.sdv.presentation.adapters.UsersAdapter
import com.sdv.presentation.base.BaseFragment
import com.sdv.presentation.databinding.UsersFragmentBinding
import kotlinx.android.synthetic.main.bottom_list_selector_item.*
import kotlinx.android.synthetic.main.users_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UsersFragment : BaseFragment(), UsersAdapter.OnItemClickListener {
    private lateinit var adapter: UsersAdapter
    override val viewModel by sharedViewModel<UsersViewModel>()


    override fun layout(): Int = R.layout.users_fragment

    override fun initialization(view: View, isFirstInit: Boolean) {
        setList()
        observeChanges()
        iv_arrow_left.setOnClickListener {
            viewModel.getPrevious()
        }
        iv_arrow_right.setOnClickListener {
            viewModel.getNext()
        }
    }

    private fun setList() {
        adapter = UsersAdapter(this)
        rv_users.adapter = adapter
        rv_users.addItemDecoration(DividerItemDecoration(rv_users.context, DividerItemDecoration.VERTICAL))
    }

    override fun dataBind(view: View): ViewDataBinding {
        return UsersFragmentBinding.bind(view).apply {
            viewmodel = viewModel
        }
    }

    override fun observeChanges() {
        viewModel.userList.observe(viewLifecycleOwner){
            adapter.setItems(it as ArrayList<User>)
        }
        viewModel.disablePrevious.observe(viewLifecycleOwner){
            if (it) {
                iv_arrow_left.isClickable = false
                iv_arrow_left.alpha = 0.3F
            } else {
                iv_arrow_left.isClickable = true
                iv_arrow_left.alpha = 1F
            }
        }
        viewModel.disableNext.observe(viewLifecycleOwner){
            if (it) {
                iv_arrow_right.isClickable = false
                iv_arrow_right.alpha = 0.3F
            } else {
                iv_arrow_right.isClickable = true
                iv_arrow_right.alpha = 1F
            }
        }
        viewModel.listProgression.observe(viewLifecycleOwner){
            tv_items_count.text = "${it.first+1} to ${it.second+1} of ${it.third}"
        }

        viewModel.showError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(user: User) {
        viewModel.openPosts(user)
    }
}