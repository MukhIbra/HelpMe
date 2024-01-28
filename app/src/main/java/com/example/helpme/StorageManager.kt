package com.example.helpme

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Callback

data class Connection(
    var from: String?,
    var to: String?,
) {
    constructor() : this(null, null)
}
data class User(
    var username: String?,
    var phoneNumber: String?,
    var password: String?,
    var role: String?
) {
    constructor() : this(null, null, null, null)
}
class StorageManager {
    companion object {
        private val users = FirebaseDatabase.getInstance().reference.child("users")
        private val connection = FirebaseDatabase.getInstance().reference.child("connection")


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
            users.push().setValue(user)
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
        fun checkChild(user: String, callback: (String) -> Unit) {
            users.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        callback("Success")
                    } else {
                        callback("User not exists")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback("Server error")
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

        private fun getUserfromKey(key: String, callback: (String) -> Unit) {
            users.child(key).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val data = dataSnapshot.getValue(User::class.java)!!.username
                    if (data != null) {
                        callback(data)
                    } else callback("Error")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback("")
                }
            })
        }

        fun getChilds(user: String, callback: (List<String>) -> Unit) {
            connection.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val users = mutableListOf<String>()
                    val children = dataSnapshot.children
                    children.forEach {
                        val connection = it.getValue(Connection::class.java)
                        if (connection != null) {
                            if (connection.from == user) {
                                connection.to?.let { users.add(it) }
                            }
                        }
                    }
                    callback(users)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(emptyList())
                }
            })
        }

        fun findRole(phoneNumber: String, callback: (String)-> Unit) {
            users.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (data in dataSnapshot.children) {
                        val user = data.getValue(User::class.java)
                        if (user != null) {
                            if (user.phoneNumber == phoneNumber) {
                                user.role?.let { callback(it) }
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(databaseError.message)
                }
            })
        }
        fun addChild(context: Context, key: String) {
            getUserfromKey(key) {
                connection.push().setValue(Connection(getSavedUser(context), it))
            }
        }


        fun getUserr(key: String, callback: (String) -> Unit) {
            users.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (data in dataSnapshot.children) {
                        if (data.getValue(User::class.java)?.phoneNumber == key) data.key?.let {
                            callback(
                                it
                            )
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    callback(databaseError.toString())
                }
            })
        }





    }
}