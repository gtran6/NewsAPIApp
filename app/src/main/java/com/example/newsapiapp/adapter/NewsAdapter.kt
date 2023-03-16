package com.example.newsapiapp.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapiapp.databinding.ItemNewsBinding
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.view.DetailsActivity

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView).load(differ.currentList[position].urlToImage)
                .into(holder.binding.image)
            author.text = differ.currentList[position].author
            source.text = differ.currentList[position].source.name
            title.text = differ.currentList[position].title
            //Log.d("recycler", "${author.text.toString()}")

            newsLayout.setOnClickListener {
                val activity = holder.itemView.context as Activity
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("article", differ.currentList[position])
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}