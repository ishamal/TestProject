package com.example.skillevaluationtest.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

@Entity
data class UserEntity(
    @Id var uId: Long = 0L,
    val userId: Long,
    val name: String?,
    val username: String?,
    val email: String?,
    val city : String?,
    val zipCode : String?,
    val lat: String?,
    val lng: String?
) : Serializable
