package com.example.quiziton.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

//    sign up function

    fun signUp(email: String, password: String): LiveData<String> {
        val result = MutableLiveData<String>()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                        if (emailTask.isSuccessful) {
                            result.value = "Registration successful. Please check your email to verify your account."
                        } else {
                            result.value = emailTask.exception?.message
                        }
                    }
                } else {
                    result.value = task.exception?.message
                }
            }

        return result
    }

//  EmailVerified





//    sign in function

    fun signIn(email: String, password: String): LiveData<String> {
        val result = MutableLiveData<String>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.value = "Login successful"
                } else {
                    result.value = task.exception?.message // Pass the error message
                }
            }
        return result
    }

    fun isEmailVerified(): Boolean {
        val user = auth.currentUser
        return user?.isEmailVerified == true
    }


    fun signOut() {
        auth.signOut()

    }

}