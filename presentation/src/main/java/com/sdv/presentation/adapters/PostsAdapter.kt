package com.sdv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sdv.domain.model.response.Post
import com.sdv.domain.model.response.User
import com.sdv.presentation.R
import kotlinx.android.synthetic.main.post_row_layout.view.*
import kotlinx.android.synthetic.main.user_row_layout.view.*

class PostsAdapter (private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private var list: ArrayList<Post> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            parent
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(listItems: ArrayList<Post>) {
        list = listItems
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    class PostViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        constructor(parent: ViewGroup) :
                this(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.post_row_layout,
                        parent,
                        false
                    )
                )

        fun bind(model: Post, onItemClickListener: OnItemClickListener) {
            itemView.tv_post_body.text = model.body
            itemView.post_row.setOnClickListener {
                onItemClickListener.onItemClick(model)
            }
        }
    }

}