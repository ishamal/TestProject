package com.example.skillevaluationtest.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

@Entity
data class CreatePostEntity(
    @Id var id: Long = 0L,
    var title : String?,
    var body : String?,
    var userId : Int

) : Serializable