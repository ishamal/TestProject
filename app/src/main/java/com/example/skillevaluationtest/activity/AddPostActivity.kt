package com.example.skillevaluationtest.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skillevaluationtest.R
import com.example.skillevaluationtest.api.Service.createService
import com.example.skillevaluationtest.db.ObjectBox
import com.example.skillevaluationtest.model.db.CreatePostEntity
import com.example.skillevaluationtest.model.request.PostCreateRequest
import com.example.skillevaluationtest.model.response.PostCreateResponse
import com.example.skillevaluationtest.utils.AppUtils
import io.objectbox.Box
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_post.*

class AddPostActivity : AppCompatActivity() {

    private lateinit var createPostBox: Box<CreatePostEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.title_add_post)

        progressBar.visibility = View.GONE

        createPostBox = ObjectBox.boxStore.boxFor(CreatePostEntity::class.java)

        createPost.setOnClickListener {
            if (!isEmpty()) {
                if (AppUtils.hasNetworkAvailable(this)) {
                    createPosts(getCreatePostRequest())
                } else {
                    savePosts(getCreatePostRequest())
                }
            }
        }

    }


    private fun isEmpty() : Boolean {
        var empty : Boolean = false
        when {
            titleEt.text.toString().isEmpty() -> {
                empty = true
                titleEt.error = this.getString(R.string.error_title)
            }
            bodyEt.text.toString().isEmpty() -> {
                empty = true
                bodyEt.error = this.getString(R.string.error_body)
            }
            else -> {
                empty = false
            }
        }

        return empty
    }

    private fun getCreatePostRequest() : PostCreateRequest{
        return PostCreateRequest(
            title = titleEt.text.toString(),
            body = bodyEt.text.toString(),
            userId = 1
        )
    }

    private fun savePosts(data : PostCreateRequest) {
        progressBar.visibility = View.VISIBLE
        Thread(Runnable {
            val data =
                CreatePostEntity(
                    id = 0L,
                    body = data.body,
                    userId = data.userId,
                    title = data.title
                )
            createPostBox.put(data)
            progressBar.visibility = View.GONE
            runOnUiThread {
                showToast(this.getString(R.string.toast_post_save))
                resetFields()
            }
        }).start()

    }


    private fun resetFields() {
        titleEt.setText(R.string.empty)
        bodyEt.setText(R.string.empty)
    }

    /**
     * Post data
     *
     * @param data PostCreateRequest
     * @return Post response list
     */
    private fun createPosts(data : PostCreateRequest) {
        progressBar.visibility = View.VISIBLE
        createService().createPost(data).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { posts: PostCreateResponse? ->
                    resetFields()
                    showToast(this.getString(R.string.toast_post_create))
                    progressBar.visibility = View.GONE
                },
                { error ->
                    progressBar.visibility = View.GONE
                    showError(error.message) }
            )

    }

    private fun showToast(msg : String) {
        val toast =   Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.LEFT,200,200)
        toast.show()
    }

    private fun showError(error: String?) {

        println(error)

    }

}
