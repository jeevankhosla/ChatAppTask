package com.example.chatapptask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapptask.databinding.ReceiveMsgBinding
import com.example.chatapptask.databinding.SendMsgBinding
import com.example.chatapptask.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val ctx: Context, val msgList: ArrayList<ChatMessage>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val MSG_RECEIVE = 2
    val MSg_SEND = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            return SentViewHolder(SendMsgBinding.inflate(LayoutInflater.from(ctx), parent, false))
        }else{

            return ReceiveViewHolder(ReceiveMsgBinding.inflate(LayoutInflater.from(ctx), parent, false))
        }

    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = msgList[position]
        if(holder.javaClass == SentViewHolder::class.java){
            // do the stuff for sent view holder
            val viewHolder = holder as SentViewHolder
            holder.sentBinding.apply {
                sendText.text = model.message
            }

        }else{
            // do stuff for receive view holder
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveBinding.apply {
                receiveMsg.text = model.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMsg = msgList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMsg.senderId)){
            return MSg_SEND
        }else{
            return MSG_RECEIVE
        }
    }


    class SentViewHolder(itemView: SendMsgBinding): RecyclerView.ViewHolder(itemView.root){
        val sentBinding = itemView
    }

    class ReceiveViewHolder(itemView: ReceiveMsgBinding): RecyclerView.ViewHolder(itemView.root){
        val receiveBinding = itemView
    }

}