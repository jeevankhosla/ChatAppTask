package com.example.chatapptask.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapptask.databinding.ActivityLoginBinding
import com.example.chatapptask.viewModel.RegisterViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        clickListener()
        observers()

    }

    private fun observers() {
        viewModel.onSuccessLogin.observe(this) {
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                launchDashBoard.launch(intent)
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clickListener() {
        binding.loginBtn.setOnClickListener {
            if (binding.email.text.isNullOrEmpty()) {
                binding.email.error = "Please enter email"
            } else if (binding.password.text.isNullOrEmpty()) {
                binding.password.error = "Please enter password"
            } else {
                viewModel.loginUser(binding.email.text.toString(), binding.password.text.toString())
            }
        }
    }

    private val launchDashBoard =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {

            }
        }
}