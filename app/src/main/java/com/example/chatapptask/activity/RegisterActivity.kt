package com.example.chatapptask.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptask.databinding.ActivityRegisterBinding
import com.example.chatapptask.viewModel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    lateinit var binding  : ActivityRegisterBinding
    lateinit var viewModel  : RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        clickListener()
        observer()

    }

    private fun observer() {
        viewModel.onSuccessRegister.observe(this){
            if(it){

                viewModel.addUserToDatabase(binding.firstName.text.toString(), binding.email.text.toString())
                val intent = Intent(this, LoginActivity::class.java)
                launchLogin.launch(intent)
            }
            else{
                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clickListener() {
        binding.registerBtn.setOnClickListener {
            if (binding.firstName.text.isNullOrEmpty()) {
                binding.firstName.error = "Please enter first name"
            } else if (binding.lastName.text.isNullOrEmpty()) {
                binding.lastName.error = "Please enter last name"
            } else if (binding.email.text.isNullOrEmpty()) {
                binding.email.error = "Please enter email"
            } else if (binding.passwordTv.text.isNullOrEmpty()) {
                binding.passwordTv.error = "Please enter password"
            }
            else if (binding.passwordTv.text.length < 6  ){
                binding.passwordTv.error = "password must be greater then 6"
            }
            else {
                val fname = binding.firstName.text.toString()
                val lname = binding.lastName.text.toString()
                val email = binding.email.text.toString()
                val pass = binding.passwordTv.text.toString()

                viewModel.signUpUser(fname, email,  pass)
            }
        }

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            launchLogin.launch(intent)
        }
    }

    private val launchLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){

        }
    }
}