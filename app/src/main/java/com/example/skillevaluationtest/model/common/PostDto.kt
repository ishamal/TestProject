package com.example.skillevaluationtest.model.common

import com.example.skillevaluationtest.model.db.CommentsEntity
import com.example.skillevaluationtest.model.db.PostEntity
import com.example.skillevaluationtest.model.db.UserEntity
import java.io.Serializable

data class PostDto(
    val postEntity: List<PostEntity>,
    val userEntity: List<UserEntity>,
    val commentsEntity: List<CommentsEntity>,
    val userImage : String?
) :Serializable