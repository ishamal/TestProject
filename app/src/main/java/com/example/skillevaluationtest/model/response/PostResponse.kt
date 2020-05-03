package com.example.skillevaluationtest.model.response

import java.io.Serializable

data class PostResponse(
    val userId : Long,
    var id: Long,
    val title : String?,
    val body : String?

):Serializable