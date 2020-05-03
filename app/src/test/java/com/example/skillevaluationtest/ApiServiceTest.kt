package com.example.skillevaluationtest

import com.example.skillevaluationtest.activity.MainActivity
import com.example.skillevaluationtest.api.ApiService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ApiServiceTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var mainActivity: MainActivity

    @Before
    @Throws(Exception ::class)
    fun setUp() {

        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun getPosts() {
        val result = mainActivity.getPosts()
    }
}