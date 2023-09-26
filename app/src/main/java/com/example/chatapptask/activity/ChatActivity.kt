package com.example.chatapptask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptask.R
import com.example.chatapptask.adapter.MessageAdapter
import com.example.chatapptask.databinding.ActivityChatBinding
import com.example.chatapptask.viewModel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth

class ChatActivity : AppCompatActivity() {
    private lateinit var sendId: String
    private lateinit var receiverId: String
    private var senderUid: String? = null
    lateinit var binding : ActivityChatBinding
   lateinit var viewModel : ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        val receiverUid =intent.getStringExtra("uid")
        val userName =intent.getStringExtra("userName")

        binding.userName.text = userName

        senderUid = FirebaseAuth.getInstance().currentUser?.uid
        sendId = receiverUid + senderUid
        receiverId = senderUid + receiverUid

        viewModel.getChathistory(sendId)
        observers()
        clickListener()


    }

    private fun observers() {
        viewModel.chatMessage.observe(this){
            binding.chatRv.adapter = MessageAdapter(this, it)

        }
    }

    private fun clickListener() {
        binding.sentBtn.setOnClickListener {
            if(!binding.messageBox.text.isNullOrEmpty()){
                viewModel.sendMsg(sendId, receiverId, binding.messageBox.text.toString(), senderUid!!)
                binding.messageBox.setText("")
            }

        }
    }
}