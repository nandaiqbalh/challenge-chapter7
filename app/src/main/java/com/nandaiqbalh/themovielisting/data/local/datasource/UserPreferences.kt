package com.nandaiqbalh.themovielisting.data.local.datasource

data class UserPreferences(
    var id: Int? = 0,
    val username: String,
    val email: String,
    val password: String? = "",
    val fullName: String? = "",
    val dateOfBirth: String? = "",
    val address: String? = "",
    val profileImage: String? = "",
    val userLoggedIn: Boolean = false
)