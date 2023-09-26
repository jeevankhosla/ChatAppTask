package com.example.chatapptask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapptask.activity.ChatActivity
import com.example.chatapptask.databinding.UserItemViewBinding
import com.example.chatapptask.model.User

class UserAdapter(val context: Context, val list: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.viewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(UserItemViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       val model = list[position]
        holder.binding.apply {
         userName.text = model.name

            userItem.setOnClickListener {
                val intent = Intent(context,ChatActivity::class.java)

                intent.putExtra("uid",model.uid)
                intent.putExtra("userName",model.name)

                context.startActivity(intent)
            }
        }

    }

    class viewHolder(itemView : UserItemViewBinding): RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }
}