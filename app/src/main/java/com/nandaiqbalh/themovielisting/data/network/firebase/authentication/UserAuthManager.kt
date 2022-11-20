package com.nandaiqbalh.themovielisting.data.network.firebase.authentication

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.nandaiqbalh.themovielisting.util.Utils

import com.nandaiqbalh.themovielisting.data.network.firebase.model.User
import com.nandaiqbalh.themovielisting.presentation.ui.movie.home.HomeFragment
import com.nandaiqbalh.themovielisting.presentation.ui.user.login.LoginFragment
import com.nandaiqbalh.themovielisting.presentation.ui.user.profile.ProfileFragment
import com.nandaiqbalh.themovielisting.presentation.ui.user.profile.UpdateProfileFragment

class UserAuthManager(val context: Context) {

    // instantiate firebase auth feature
    val auth = FirebaseAuth.getInstance()
    val firestore =  FirebaseFirestore.getInstance()

    private var isLoginSuccess = false

    fun createUserFirebase(username: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)

                // handling if user is successful register with email
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result.user!!
                    val user = User(
                        firebaseUser.uid,
                        username = username,
                        email = email,
                    )
                    registerUser(user)

                    Utils.showFailureToast(context, "Register success", false)
                }
            }

            // handling if user is unsuccessful register with email
            .addOnFailureListener {
                Utils.showFailureToast(context, it.message.toString(), true)
            }
    }

    suspend fun signInFirebase(email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                isLoginSuccess = it.isSuccessful
                Utils.showFailureToast(context, "Login success", false)
            }

            .addOnFailureListener {
                isLoginSuccess = false
                Utils.showFailureToast(context, it.message.toString(), true)
            }
    }

    @JvmName("isLoginSuccess1")
    fun isLoginSuccess(): Boolean {
        return isLoginSuccess
    }

    // get id
    private fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""

        currentUser?.let {
            currentUserId = it.uid
        }

        return currentUserId
    }

    private fun registerUser(user: User) {
        firestore.collection(USER)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                auth.signOut()
            }
            .addOnFailureListener {
            }
    }

    fun getUserDetail(fragment: Fragment) {
        firestore.collection(USER)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!

                when (fragment) {
                    is LoginFragment -> {
                        fragment.navigateToHome(user)
                    }
                    is ProfileFragment -> {
                        fragment.bindDataToView(user)
                    }
                    is HomeFragment -> {
                        fragment.getInitialUser(user)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(fragment.javaClass.simpleName, "Error while getting user detail")
            }
    }

    fun updateProfile(fragment: Fragment, userHashMap: HashMap<String, Any>) {
        firestore.collection(USER)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when (fragment) {
                    is UpdateProfileFragment -> {
                        fragment.activity?.finish()
                    }
                }
            }

            .addOnFailureListener {
                when (fragment) {
                    is UpdateProfileFragment -> {
                        fragment.activity?.finish()
                    }
                }
                Log.e(fragment.javaClass.simpleName, "Error!")
            }
    }

    companion object {
        private const val USER = "user"
    }
}
