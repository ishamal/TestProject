package com.example.skillevaluationtest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skillevaluationtest.R
import com.example.skillevaluationtest.model.response.PostResponse
import kotlinx.android.synthetic.main.list_post.view.*

class PostAdapter(val posts: List<PostResponse>, val context: Context) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    var mListener: PostAdapterListener? = null

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val postTitle = view.postTitleTv!!
        val postView = view.postTitleView

    }

    interface PostAdapterListener {

        fun onPostClick(posts: PostResponse)

    }

    fun setListener(listener: PostAdapterListener) {

        this.mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        return PostViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_post, parent, false)
        )

    }

    override fun getItemCount(): Int {

        return posts.size

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val postResponse = posts[position]
        holder.postTitle.text = postResponse.title
        holder.postView.setOnClickListener {
            mListener?.onPostClick(postResponse)
        }

    }

}