package com.example.chatapptask.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptask.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterViewModel :ViewModel() {
    private var auth: FirebaseAuth? = null
    var onSuccessRegister = MutableLiveData<Boolean>()
    var onSuccessLogin = MutableLiveData<Boolean>()
    private lateinit var dbrefe: DatabaseReference

    init {
        auth = FirebaseAuth.getInstance()
        dbrefe = FirebaseDatabase.getInstance().reference

    }

    fun signUpUser(name:String, email: String, pass : String) {

        auth?.createUserWithEmailAndPassword( email, pass)?.addOnCompleteListener {
            if (it.isSuccessful) {

                auth?.currentUser?.apply {
                    val profileUpdates : UserProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    updateProfile(profileUpdates).addOnCompleteListener(OnCompleteListener {
                        when(it.isSuccessful) {
                            true -> apply {
                                onSuccessRegister.postValue(true)
                            }
                            false -> onSuccessRegister.postValue(false)
                        }
                    })
                }
            } else {
                onSuccessRegister.postValue(false)
            }
        }

    }
    fun addUserToDatabase(name:String, email:String){
        // use real
        dbrefe.child("user").child(auth?.currentUser?.uid!!).setValue(User( name, email,auth?.currentUser?.uid!!)) // add user to database
        Log.d("mDbRef", "addUserToDatabase:  Data saved ${auth?.currentUser?.uid!!}")
    }

    fun loginUser(email: String, pass: String){
        auth?.signInWithEmailAndPassword(email, pass)?.addOnCompleteListener{
            if (it.isSuccessful) {
                onSuccessLogin.postValue(true)
            } else
                onSuccessLogin.postValue(false)
        }
    }


}