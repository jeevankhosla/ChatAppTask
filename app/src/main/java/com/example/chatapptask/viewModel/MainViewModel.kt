package com.example.chatapptask.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptask.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class MainViewModel :ViewModel() {

    //var rootRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("user")
    private var auth: FirebaseAuth? = null
    var rootRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    //var userList = ArrayList<User>()
    val userList = MutableLiveData<ArrayList<User>>()


    init {
        auth = FirebaseAuth.getInstance()
    }

    fun getAllUsers(){
        try {
            rootRef.child("user").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var list: ArrayList<User> = ArrayList()
                    for (userSnapshot in snapshot.children) {
                        val currentUser = userSnapshot.getValue(User::class.java)
                        // add user to list
                        if(auth?.currentUser?.uid != currentUser?.uid){
                            list.add(currentUser!!)
                        }
                    }
                    userList.postValue(list)
                    Log.d("users", "onDataChange: ${list.size} ")
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("users", "failed:  ")
                }
            })
        }
        catch (ex:Exception){
            Log.d("Ex", "getAllUsers: ex ${ex.message} ")
        }
    }
}