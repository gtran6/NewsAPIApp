package com.example.newsapiapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapiapp.databinding.ItemNewsBinding
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.view.DetailsActivity

class NewsAdapter(val list: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(list[position].urlToImage)
                .into(holder.binding.image)
            author.text = list[position].author
            source.text = list[position].source.name
            title.text = list[position].title

            newsLayout.setOnClickListener {
                val activity = holder.itemView.context as Activity
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("article", list[position])
                holder.itemView.context.startActivity(intent)
            }

        }
    }
}