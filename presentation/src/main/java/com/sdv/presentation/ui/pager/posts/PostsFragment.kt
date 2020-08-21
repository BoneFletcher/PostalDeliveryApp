package com.sdv.presentation.ui.pager.posts

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.sdv.domain.model.response.Post
import com.sdv.presentation.R
import com.sdv.presentation.adapters.PostsAdapter
import com.sdv.presentation.base.BaseFragment
import com.sdv.presentation.databinding.PostsFragmentBinding
import com.sdv.presentation.ui.pager.posts.detail.PostDetailDialogFragment
import com.sdv.presentation.ui.pager.user.UsersViewModel
import kotlinx.android.synthetic.main.bottom_list_selector_item.*
import kotlinx.android.synthetic.main.posts_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostsFragment : BaseFragment(), PostsAdapter.OnItemClickListener {

    private lateinit var adapter: PostsAdapter
    override val viewModel by sharedViewModel<UsersViewModel>()

    override fun layout(): Int = R.layout.posts_fragment

    override fun initialization(view: View, isFirstInit: Boolean) {
        setList()
        observeChanges()
        iv_arrow_left.setOnClickListener {
            viewModel.getPreviousPost()
        }
        iv_arrow_right.setOnClickListener {
            viewModel.getNextPost()
        }
    }

    private fun setList() {
        adapter = PostsAdapter(this)
        rv_posts.adapter = adapter
        rv_posts.addItemDecoration(DividerItemDecoration(rv_posts.context, DividerItemDecoration.VERTICAL))
    }

    override fun dataBind(view: View): ViewDataBinding {
        return PostsFragmentBinding.bind(view).apply {
            viewmodel = viewModel
        }
    }

    override fun observeChanges() {
        viewModel.postList.observe(viewLifecycleOwner){
            adapter.setItems(it as ArrayList<Post>)
        }
        viewModel.disablePreviousPost.observe(viewLifecycleOwner){
            if (it) {
                iv_arrow_left.isClickable = false
                iv_arrow_left.alpha = 0.3F
            } else {
                iv_arrow_left.isClickable = true
                iv_arrow_left.alpha = 1F
            }
        }
        viewModel.disableNextPost.observe(viewLifecycleOwner){
            if (it) {
                iv_arrow_right.isClickable = false
                iv_arrow_right.alpha = 0.3F
            } else {
                iv_arrow_right.isClickable = true
                iv_arrow_right.alpha = 1F
            }
        }

        viewModel.listProgressionPost.observe(viewLifecycleOwner){
            tv_items_count.text = "${it.first+1} to ${it.second+1} of ${it.third}"
        }

        viewModel.showProgress.observe(viewLifecycleOwner){
            progress_bar_posts.isVisible = it
        }

        viewModel.showError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(post: Post) {
        val fm = fragmentManager
        fm?.let { PostDetailDialogFragment.newInstance(post).show(it, "SubjectDialog") }
        viewModel.getComments(postId = post.id)
    }
}