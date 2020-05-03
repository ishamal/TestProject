package com.example.skillevaluationtest.model.request

import java.io.Serializable

data class PostCreateRequest(
    var title : String?,
    var body : String?,
    var userId : Int
) : Serializable