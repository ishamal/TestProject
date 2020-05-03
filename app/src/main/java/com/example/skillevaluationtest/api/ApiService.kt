package com.example.skillevaluationtest.api

import com.example.skillevaluationtest.model.request.PostCreateRequest
import com.example.skillevaluationtest.model.response.CommentResponse
import com.example.skillevaluationtest.model.response.PostCreateResponse
import com.example.skillevaluationtest.model.response.PostResponse
import com.example.skillevaluationtest.model.response.UserResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/posts")
    fun getPosts(): Observable<List<PostResponse>>

    @GET("/users")
    fun getUsers(): Observable<List<UserResponse>>

    @GET("/comments")
    fun getComments() : Observable<List<CommentResponse>>

    @POST("/posts")
    fun createPost(@Body data: PostCreateRequest) : Observable<PostCreateResponse>
}