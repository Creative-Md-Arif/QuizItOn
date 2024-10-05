package com.example.quiziton

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quiziton.databinding.ActivityRegistrationBinding
import com.example.quiziton.viewmodel.AuthViewModel

class RegistrationActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Set up Register button click event
        binding.registerBtn.setOnClickListener {
            val name = binding.nameEditTxt.text.toString().trim()
            val email = binding.emailEditTxt.text.toString().trim()
            val password = binding.passEditTxt.text.toString().trim()
            val conPass = binding.conPassEditTxt.text.toString().trim()
            val checkBox = binding.termsCheckBox.isChecked

            // Validate inputs
            if (name.isEmpty()) {
                binding.nameEditTxt.error = "Name is required"
            } else if (email.isEmpty()) {
                binding.emailEditTxt.error = "Email is required"
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditTxt.error = "Enter a valid email"
            } else if (password.isEmpty()) {
                binding.passEditTxt.error = "Password is required"
            } else if (password.length < 6) {
                binding.passEditTxt.error = "Password must be at least 6 characters"
            } else if (conPass.isEmpty()) {
                binding.conPassEditTxt.error = "Confirm password is required"
            } else if (conPass != password) {
                binding.conPassEditTxt.error = "Passwords do not match"
            } else if (!checkBox) {
                Toast.makeText(this, "You must accept the terms and conditions", Toast.LENGTH_SHORT).show()
            } else {
                // Show loading animation
                showLoading()

                // Register user via ViewModel
                viewModel.signUp(email, password).observe(this) { message ->
                    hideLoading()

                    if (message == "Registration successful. Please check your email to verify your account.") {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                        // Navigate to LoginActivity after a delay
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                        }, 3000)
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Set up Sign-in text click listener to navigate to LoginActivity
        binding.signInText.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }
    }

    // Show loading progress bar
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.registerBtn.isEnabled = false // Disable the button while loading
    }

    // Hide loading progress bar
    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.registerBtn.isEnabled = true 
    }
}
