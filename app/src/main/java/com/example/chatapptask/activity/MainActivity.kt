package com.example.chatapptask.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptask.adapter.UserAdapter
import com.example.chatapptask.databinding.ActivityMainBinding
import com.example.chatapptask.model.User
import com.example.chatapptask.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference
        viewModel.getAllUsers()

        observers()
    }

    private fun observers() {
        viewModel.userList.observe(this){
            it.forEach {
                Log.d("users", "observers: useNmae ${it.name}")
            }
            binding.rvUsers.adapter = UserAdapter(this, it)
        }
    }


}