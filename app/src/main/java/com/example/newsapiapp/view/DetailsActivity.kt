package com.example.newsapiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.newsapiapp.databinding.ActivityDetailsBinding
import com.example.newsapiapp.model.Article

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getParcelableExtra<Article>("article")
        if (article != null) {
            binding.apply {
                title.text = article.title
                publishedAt.text = article.publishedAt
                source.text = article.source.name
                content.text = article.content
                Glide.with(this@DetailsActivity)
                    .load(article.urlToImage)
                    .into(binding.image)
            }
        }
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}