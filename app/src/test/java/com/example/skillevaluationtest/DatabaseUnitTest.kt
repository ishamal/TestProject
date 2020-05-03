package com.example.skillevaluationtest

import com.example.skillevaluationtest.model.db.*
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.File

class DatabaseUnitTest {

    private val DIRECTORY : File = File("objectbox-example/test-db")
    var store : BoxStore? = null


    @Before
    @Throws(Exception ::class)
    fun setUp()  {
        BoxStore.deleteAllFiles(DIRECTORY)
        store = MyObjectBox.builder().directory(DIRECTORY).debugFlags(DebugFlags.LOG_QUERIES).build()
    }

    @After
    @Throws(Exception ::class)
    fun tearDown() {
        if (store != null) {
            store!!.close()
            store = null

        }
        BoxStore.deleteAllFiles(DIRECTORY)
    }

    @Test
    fun testPostSave() {
        val postBox :Box<PostEntity> = store!!.boxFor(PostEntity::class.java)
        val postEntity = PostEntity(
            title = "test Title",
            body = "test Body",
            userId = 1L,
            postId = 2L
        )

        val postId : Long = postBox.put(postEntity)
        assertTrue(postId > 0)

        val postFromBox : PostEntity = postBox.get(postId)
        assertEquals("test Title", postFromBox.title)
        assertEquals("test Body", postFromBox.body)
    }


    @Test
    fun testUserSave() {
        val userBox :Box<UserEntity> = store!!.boxFor(UserEntity::class.java)
        val userEntity : UserEntity = mock(UserEntity::class.java)

        `when`(userEntity.name).thenReturn("test user")

        val userId : Long = userBox.put(userEntity)
        assertTrue(userId > 0)

        val userFromBox : UserEntity = userBox.get(userId)
        assertEquals("test user", userFromBox.name)
    }

    @Test
    fun testCommentSave() {
        val commentBox : Box<CommentsEntity>  = store!!.boxFor(CommentsEntity::class.java)
        val commentsEntity : CommentsEntity = mock(CommentsEntity::class.java)

        `when`(commentsEntity.email).thenReturn("test@123.com")
        `when`(commentsEntity.name).thenReturn("test comment")

        val commentID : Long = commentBox.put(commentsEntity)
        assertTrue(commentID > 0)

        val commentFromBox : CommentsEntity = commentBox.get(commentID)
        assertEquals("test@123.com", commentFromBox.email)
        assertEquals("test comment", commentFromBox.name)
    }



    @Test
    fun testCreatePost() {
        val createPostBox : Box<CreatePostEntity>  = store!!.boxFor(CreatePostEntity::class.java)
        val createPostEntity : CreatePostEntity = mock(CreatePostEntity::class.java)

        `when`(createPostEntity.title).thenReturn("test title")
        `when`(createPostEntity.body).thenReturn("test body")
        `when`(createPostEntity.userId).thenReturn(1)

        val postId : Long = createPostBox.put(createPostEntity)
        assertTrue(postId > 0)

        val postFromBox : CreatePostEntity = createPostBox.get(postId)
        assertEquals("test title", postFromBox.title)
        assertEquals("test body", postFromBox.body)
        assertEquals(1, postFromBox.userId)
    }


}