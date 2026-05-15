package com.mindmatrix.app1.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val area: String = "",
    val role: String = "", // "Reporter" or "Cleaner"
    val rewards: Long = 0L
)
