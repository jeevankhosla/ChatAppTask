package com.example.chatapptask.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptask.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatViewModel :ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var dbrefe: DatabaseReference = FirebaseDatabase.getInstance().reference

    val chatMessage = MutableLiveData<ArrayList<ChatMessage>>()


    fun getChathistory(id: String){
        dbrefe.child("chats").child(id).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val msgList = ArrayList<ChatMessage>()
                    msgList.clear()

                    for(postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(ChatMessage::class.java)
                        msgList.add(message!!)
                    }
                    if(!msgList.isNullOrEmpty()){
                        chatMessage.postValue(msgList)
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    fun sendMsg(sendId :String?, receiceId:String?, message: String, senderUid: String){
        val messageObject = ChatMessage(message, senderUid)

        dbrefe.child("chats").child(sendId!!).child("messages").push()
            .setValue(messageObject).addOnSuccessListener {
                dbrefe.child("chats").child(receiceId!!).child("messages").push()
                    .setValue(messageObject)
            }
        //messageBox.setText("")
    }
}