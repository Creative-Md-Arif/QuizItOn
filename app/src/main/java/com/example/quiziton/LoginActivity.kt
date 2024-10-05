package com.example.quiziton

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quiziton.databinding.ActivityLoginBinding
import com.example.quiziton.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater) // Inflate the binding
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEditTxt.text.toString().trim()
            val password = binding.passEditTxt.text.toString().trim()

            // Validate inputs
            if (email.isEmpty()) {
                binding.emailEditTxt.error = "Email is required"
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditTxt.error = "Enter a valid email"
            } else if (password.isEmpty()) {
                binding.passEditTxt.error = "Password is required"
            } else {
                // Show loading animation
                showLoading()

                // Call the login method in ViewModel
                viewModel.signIn(email, password).observe(this) { result ->
                    // Hide loading animation
                    hideLoading()

                    // Handle the result
                    if (result == "Login successful") {
                        // Check if email is verified
                        if (viewModel.isEmailVerified()) {
                            // Navigate to HomeActivity
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish() // Optional: Close the LoginActivity
                        } else {
                            Toast.makeText(this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show()
                        }
                    } else if (result.contains("wrong password", ignoreCase = true)) {
                        Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show()
                    } else if (result.contains("no user record", ignoreCase = true)) {
                        Toast.makeText(this, "No user found with this email. Please register.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Show generic error message
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.signInText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }

        binding.forgotPasswordText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, InputEmailActivity::class.java))
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE // Show the ProgressBar
        binding.loginBtn.isEnabled = false // Disable the login button while loading
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE // Hide the ProgressBar
        binding.loginBtn.isEnabled = true // Re-enable the login button
    }

}
