package com.example.skillevaluationtest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.skillevaluationtest.R
import com.example.skillevaluationtest.model.common.PostDto
import com.example.skillevaluationtest.model.db.UserEntity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var postData: PostDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setTitle(R.string.title_post_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        postData = intent.getSerializableExtra("Post_Data") as PostDto?
        setData()
        userNameDataTv.setOnClickListener {
            navigateToNextUi(postData?.userEntity!![0])
        }
    }

    private fun setData() {
        titleTv.text = postData?.postEntity!![0].title
        bodyTv.text = postData?.postEntity!![0].body
        userNameDataTv.text = postData?.userEntity!![0].username
        commentCountTv.text = postData?.commentsEntity!!.size.toString()

        Glide.with(this).load(postData?.userImage).placeholder(R.mipmap.ic_launcher)
            .into(profileImageView)

    }


    private fun navigateToNextUi(user: UserEntity) {

        val intent = Intent(this@DetailActivity, UserDetailActivity::class.java)
        intent.putExtra("user_Data", user)
        startActivity(intent)

    }

}
