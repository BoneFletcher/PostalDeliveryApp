package com.sdv.presentation.ui.pager.posts.detail

import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.sdv.domain.model.response.Post
import com.sdv.presentation.R
import com.sdv.presentation.databinding.PostDetailDialogFragmentLayoutBinding
import com.sdv.presentation.ui.pager.posts.PostsViewModel
import com.sdv.presentation.ui.pager.user.UsersViewModel
import kotlinx.android.synthetic.main.post_detail_dialog_fragment_layout.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailDialogFragment : DialogFragment() {
    private lateinit var binding: PostDetailDialogFragmentLayoutBinding
    val viewModel by sharedViewModel<UsersViewModel>()

    companion object {
        private const val POST = "POST"

        fun newInstance(post: Post): PostDetailDialogFragment {
            val args = Bundle()
            args.putParcelable(POST, post)
            val fragment = PostDetailDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: PostDetailDialogFragmentLayoutBinding = DataBindingUtil.inflate(
            inflater, R.layout.post_detail_dialog_fragment_layout, container, false
        )
        binding.viewModel = viewModel
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners(view)
        arguments?.getParcelable<Post>(POST)?.let { viewModel.setPostDetail(it) }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.commentList.observe(viewLifecycleOwner){
            binding.tvNumberOfComments.text = setBoldText(getString(R.string.number_comments)+" "+it.second.size.toString(), getString(R.string.number_comments).length-1)
            binding.tvUserName.text = setBoldText(getString(R.string.user_name)+" "+it.first, getString(R.string.user_name).length-1)
        }
        viewModel.postDetail.observe(viewLifecycleOwner){
            binding.tvPostBody.text = setBoldText(getString(R.string.post_body)+" "+it.body, getString(R.string.post_body).length-1)
            binding.tvPostTitle.text = setBoldText(getString(R.string.post_title)+" "+it.title, getString(R.string.post_title).length-1)
        }

        viewModel.showError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
        }
    }

    private fun setBoldText(text: String, countBold: Int): Spannable{
        val spannable = SpannableString(text)
        spannable.setSpan(StyleSpan(BOLD), 0, countBold, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners(view: View) {

        view.btn_ok.setOnClickListener {
            dialog?.dismiss()
        }
    }

}