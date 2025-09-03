package com.olivia.localcultureandlanguagehelper.data


data class User(
    val fullname: String = "",
    val email: String = "",
    val password: String = "",
    val uid: String = "",
    val role: String = "user"   // <-- added role field
)
