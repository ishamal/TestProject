package com.example.skillevaluationtest.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

@Entity
data class PostEntity(
    val userId : Long,
    val postId: Long,
    @Id var id: Long = 0,
    val title : String?,
    val body : String?

) : Serializable