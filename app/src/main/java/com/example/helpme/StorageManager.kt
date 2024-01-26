package com.example.helpme

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class User(
    var username: String?,
    var phoneNumber: String?,
    var password: String?,
    var role: String?,
) {
    constructor() : this(null, null, null, null)
}

class StorageManager {
    companion object {
        private val users = FirebaseDatabase.getInstance().reference.child("users")

        fun saveUser(context: Context, phoneNumber: String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("user", phoneNumber)
            editor.apply()
        }

        fun getSavedUser(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences("db", Context.MODE_PRIVATE)
            return sharedPreferences.getString("user", "") ?: ""
        }

        fun createUser(user: User) {
            users.child(user.username!!).setValue(user)
        }

        fun checkUser(phoneNumber: String, callback: (Boolean) -> Unit) {
            users.child(phoneNumber).addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    Log.d("TAG", "onCancelled: message")
                    if (dataSnapshot.exists()) {
                        callback(false)
                    } else {
                        callback(true)
                    }
                }


                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("TAG", "onCancelled: ${databaseError.message}")
                    callback(false)
                }
            })
        }

        fun getUser(
            username: TextFieldValue, password: TextFieldValue, callback: (String) -> Unit
        ) {
            users.child(username.text).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.getValue(User::class.java)!!.password == password.text) {
                        callback("Successful Login")
                    } else {
                        callback("Incorrect Username or Password")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback("Database Error")
                }
            })
        }
    }
}