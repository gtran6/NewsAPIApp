package com.example.newsapiapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.newsapiapp.databinding.ActivityDetailsBinding
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    val mainViewModel: MainViewModel by viewModels()

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

                // set up implicit intent
                moreButton.setOnClickListener {
                    val query: Uri = Uri.parse("${article.url}")
                    val intent = Intent(Intent.ACTION_VIEW, query)
                    startActivity(intent)
                }

                saveButton.setOnClickListener {
                    mainViewModel.insertNews(this@DetailsActivity, article)
                    Toast.makeText(applicationContext, " News Saved", Toast.LENGTH_SHORT).show()
                    Log.d("save-news","${article.title.toString()}")
                    Log.d("save-news", "${article.toString()}")
                }
            }
        }
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}