package com.olivia.localcultureandlanguagehelper.data


import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_HOME
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_LOGIN
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_REGISTER

class AuthViewModel(var navController: NavHostController, var context: Context) {

    var mAuth: FirebaseAuth

    init {
        mAuth = FirebaseAuth.getInstance()
    }

    fun signup(fullname: String, email: String, pass: String, confirmpass: String, isAdmin: Boolean = false) {
        if (email.isBlank() || pass.isBlank() || confirmpass.isBlank()) {
            Toast.makeText(
                context, "Please email and password cannot be blank",
                Toast.LENGTH_LONG
            ).show()
            return
        } else if (pass != confirmpass) {
            Toast.makeText(
                context, "Passwords do not match",
                Toast.LENGTH_LONG
            ).show()
            return
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val role = if (isAdmin) "admin" else "user"   // ✅ store role
                        val userdata = User(fullname, email, pass, mAuth.currentUser!!.uid, role)
                        val regRef = FirebaseDatabase.getInstance().getReference()
                            .child("Users/" + mAuth.currentUser!!.uid)
                        regRef.setValue(userdata).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Registered Successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(ROUTE_LOGIN)
                            } else {
                                Toast.makeText(
                                    context,
                                    "${task.exception!!.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(ROUTE_LOGIN)
                            }
                        }
                    } else {
                        navController.navigate(ROUTE_REGISTER)
                    }
                }
        }
    }

    fun login(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                // After login, fetch user role
                val userId = mAuth.currentUser?.uid ?: return@addOnCompleteListener
                FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val role = snapshot.child("role").value?.toString() ?: "user"
                        if (role == "admin") {
                            Toast.makeText(context, "Welcome Admin!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate(ROUTE_HOME)
                    }
            } else {
                Toast.makeText(context, "Error logging in", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0)
        }
    }

    fun isLoggedIn(): Boolean = mAuth.currentUser != null
}
