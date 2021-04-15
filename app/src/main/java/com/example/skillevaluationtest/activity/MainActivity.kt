package com.example.skillevaluationtest.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skillevaluationtest.R
import com.example.skillevaluationtest.adapters.PostAdapter
import com.example.skillevaluationtest.api.Service.createService
import com.example.skillevaluationtest.db.ObjectBox
import com.example.skillevaluationtest.model.common.PostDto
import com.example.skillevaluationtest.model.db.*
import com.example.skillevaluationtest.model.request.PostCreateRequest
import com.example.skillevaluationtest.model.response.CommentResponse
import com.example.skillevaluationtest.model.response.PostCreateResponse
import com.example.skillevaluationtest.model.response.PostResponse
import com.example.skillevaluationtest.model.response.UserResponse
import com.example.skillevaluationtest.utils.AppUtils.hasNetworkAvailable
import io.objectbox.Box
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PostAdapter.PostAdapterListener {
    private lateinit var viewAdapter: PostAdapter
    private lateinit var postBox: Box<PostEntity>
    private lateinit var userBox: Box<UserEntity>
    private lateinit var commentBox: Box<CommentsEntity>
    private lateinit var createPostBox: Box<CreatePostEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.title_main)

        progressBar.visibility = GONE
        postBox = ObjectBox.boxStore.boxFor(PostEntity::class.java)
        userBox = ObjectBox.boxStore.boxFor(UserEntity::class.java)
        commentBox = ObjectBox.boxStore.boxFor(CommentsEntity::class.java)
        createPostBox = ObjectBox.boxStore.boxFor(CreatePostEntity::class.java)


        defineLayout()

        getData()

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, AddPostActivity::class.java)
            startActivity(intent)
        }

        syncBtn.setOnClickListener {
            if (hasNetworkAvailable(this)) {
                syncData()
            } else {
                showToast(this.getString(R.string.error_network))
            }

        }
    }

    private fun syncData() {
        progressBar.visibility = VISIBLE
        val data = getSyncData()
        if (data.isNotEmpty()) {
            createPosts(
                PostCreateRequest(
                    title = data[0].title,
                    body = data[0].body,
                    userId = data[0].userId
                ), data[0].id
            )
        } else {
            progressBar.visibility = GONE
            showToast(this.getString(R.string.toast_post_sync))
        }
    }

    /**
     * Post data
     *
     * @param data PostCreateRequest
     * @return Post response list
     */
    private fun createPosts(data: PostCreateRequest, id: Long) {
        progressBar.visibility = VISIBLE
        createService().createPost(data).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { posts: PostCreateResponse? ->
                    createPostBox.remove(id)
                    syncData()
                },
                { error ->
                    showError(error.message)
                }
            )

    }


    private fun showToast(msg: String) {
        val toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.LEFT, 200, 200)
        toast.show()
    }

    private fun getSyncData(): List<CreatePostEntity> {
        return createPostBox.all
    }

    /**
     * Define layout
     */
    private fun defineLayout() {

        posTable.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    /**
     * Get data
     */
    private fun getData() {
        progressBar.visibility = VISIBLE
        if (hasNetworkAvailable(this)) {
            getPosts()
        } else {
            loadPostFromDb()
        }

    }

    /**
     * Get post from api
     *
     * @return Post response list
     */
     fun getPosts() {

        createService().getPosts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { posts: List<PostResponse>? ->
                    saveAndCreateList(posts)
                    progressBar.visibility = GONE
                },
                { error ->
                    showError(error.message)
                    progressBar.visibility = GONE
                }
            ).also {
                progressBar.visibility = VISIBLE
                getUsers()
            }

    }

    private fun getUsers() {

        createService().getUsers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users: List<UserResponse>? ->
                    saveUsers(users)
                    progressBar.visibility = GONE
                },
                { error ->
                    showError(error.message)
                    progressBar.visibility = GONE
                }
            ).also {
                progressBar.visibility = VISIBLE
                getComments()
            }
    }

    private fun getComments() {

        createService().getComments().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { comments: List<CommentResponse>? ->
                    saveComments(comments)
                    progressBar.visibility = GONE
                },
                { error ->
                    progressBar.visibility = GONE
                    showError(error.message)
                }
            )
    }


    private fun showError(error: String?) {

        println(error)

    }

    private fun savePosts(posts: List<PostResponse>?) {

        Thread(Runnable {
            postBox.removeAll()
            for (post in posts!!) {
                val data =
                    PostEntity(
                        id = 0L,
                        postId = post.id,
                        userId = post.userId,
                        title = post.title,
                        body = post.body
                    )
                postBox.put(data)
            }
        }).start()

    }

    private fun saveComments(comments: List<CommentResponse>?) {

        Thread(Runnable {
            commentBox.removeAll()
            for (comment in comments!!) {
                val data =
                    CommentsEntity(
                        commentId = 0L,
                        name = comment.name,
                        email = comment.email,
                        postId = comment.postId,
                        body = comment.body,
                        id = comment.id
                    )
                commentBox.put(data)
            }
        }).start()

    }

    private fun saveUsers(users: List<UserResponse>?) {

        Thread(Runnable {
            userBox.removeAll()
            for (user in users!!) {
                val data =
                    UserEntity(
                        uId = 0L,
                        userId = user.id,
                        username = user.username,
                        name = user.name,
                        email = user.email,
                        zipCode = user.address.zipCode,
                        lat = user.address.geo.lat,
                        lng = user.address.geo.lng,
                        city = user.address.city
                    )
                userBox.put(data)
            }
        }).start()

    }

    private fun dbObjectMapper(): List<PostResponse> {
        val list = ArrayList<PostResponse>()

        for (postItem in postBox.all) {
            val data =
                PostResponse(
                    id = postItem.postId,
                    title = postItem.title,
                    body = postItem.body,
                    userId = postItem.userId
                )
            list.add(data)
        }
        return list
    }

    private fun loadPostFromDb() {

        progressBar.visibility = GONE
        val posts: List<PostResponse>? = dbObjectMapper()
        addPostList(posts)
    }

    private fun saveAndCreateList(posts: List<PostResponse>?) {

        savePosts(posts)
        addPostList(posts)

    }

    /**
     * Add post to List view
     *
     * @param posts PostResponse
     */
    private fun addPostList(posts: List<PostResponse>?) {

        viewAdapter = PostAdapter(posts!!, this)
        viewAdapter.setListener(this)
        posTable.adapter = viewAdapter

        println("Commit print added")


    }

    override fun onPostClick(posts: PostResponse) {

        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("Post_Data", createBundle(posts))
        startActivity(intent)

    }



    private fun createBundle(posts: PostResponse): PostDto {
        val postEntity = postBox.query().equal(PostEntity_.postId, posts.id).build().find()
        val userEntity = userBox.query().equal(UserEntity_.userId, posts.userId).build().find()
        val comments = commentBox.query().equal(CommentsEntity_.postId, posts.id).build().find()
        var url = "https://api.adorable.io/avatars/285/abott@adorable.png"
        if (userEntity.isNotEmpty()) {
            url = "https://api.adorable.io/avatars/285/${userEntity[0].email}.png"
        }

        return PostDto(
            postEntity = postEntity,
            userEntity = userEntity,
            commentsEntity = comments,
            userImage = url
        )
    }


}

