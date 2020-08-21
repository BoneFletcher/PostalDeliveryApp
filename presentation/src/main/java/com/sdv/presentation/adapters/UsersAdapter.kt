package com.sdv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sdv.domain.model.response.User
import com.sdv.presentation.R
import kotlinx.android.synthetic.main.user_row_layout.view.*

class UsersAdapter (private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val limit: Int = 6
    private var list: ArrayList<User> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            parent
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        if(list.size > limit){
            return limit;
        }
        else {
            return list.size
        }
    }

    fun setItems(listItems: ArrayList<User>) {
        list = listItems
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    class UserViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        constructor(parent: ViewGroup) :
                this(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.user_row_layout,
                        parent,
                        false
                    )
                )

        fun bind(model: User, onItemClickListener: OnItemClickListener) {
            itemView.tv_name.text = model.name
            itemView.tv_company_name.text = model.company.name
            itemView.tv_email.text = model.email
            itemView.container_user.setOnClickListener {
                onItemClickListener.onItemClick(model)
            }
        }
    }

}