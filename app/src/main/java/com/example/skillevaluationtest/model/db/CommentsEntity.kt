package com.example.skillevaluationtest.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

@Entity
data class CommentsEntity(
    @Id var commentId: Long = 0L,
    val postId : Long,
    val id : Long,
    val name : String?,
    val email : String?,
    val body : String?
) : Serializable