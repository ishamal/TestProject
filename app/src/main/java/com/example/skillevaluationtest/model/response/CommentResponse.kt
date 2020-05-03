package com.example.skillevaluationtest.model.response

import java.io.Serializable

data class CommentResponse(
    val postId : Long,
    val id : Long,
    val name : String?,
    val email : String?,
    val body : String?
) : Serializable