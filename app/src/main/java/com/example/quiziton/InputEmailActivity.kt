package com.example.quiziton


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quiziton.viewmodel.AuthViewModel
import com.example.quiziton.databinding.ActivityInputEmailBinding
import com.google.firebase.auth.FirebaseAuth

class InputEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputEmailBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Set the OnClickListener for the Next button
        binding.nextButton.setOnClickListener {
            val email = binding.emailEditTxt.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailEditTxt.error = "Email is required"
                return@setOnClickListener
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditTxt.error = "Enter a valid email"
                return@setOnClickListener
            }

            // Start the password recovery process
            sendVerificationCode(email)
        }
    }

    private fun sendVerificationCode(email: String) {
        // Show loading animation
        showLoading()

        // Call the ViewModel function to send the verification email
        viewModel.sendPasswordResetEmail(email).observe(this) { message ->
            hideLoading()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            // Check if the message indicates success
            if (message == "Verification code sent to your email.") {
                // Navigate to the next screen (Verification Code Input)
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("email", email) // Pass the email to the next activity
                startActivity(intent)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.nextButton.isEnabled = false // Disable the button while loading
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.nextButton.isEnabled = true // Re-enable the button
    }
}
