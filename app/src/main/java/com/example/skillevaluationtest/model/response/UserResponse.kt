package com.example.skillevaluationtest.model.response

import java.io.Serializable


data class UserResponse(
    val id: Long,
    val name: String?,
    val username: String?,
    val email: String?,
    val address: Address
) : Serializable