package com.example.newsapiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.adapter.NewsAdapter
import com.example.newsapiapp.databinding.ActivitySaveBinding
import com.example.newsapiapp.extra.Events
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveActivity : AppCompatActivity() {
    lateinit var binding: ActivitySaveBinding
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getNewsFromDatabase(context = applicationContext)
        mainViewModel.data.observe(this, Observer {
            when (it) {
                is Events.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("save", "loading")
                }
                is Events.Success -> {
                    it.let {
                        it.data?.let { it2 ->
                            setupRecyclerView(it2)
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                    Log.d("save", "${it.data.toString()}")
                }
                is Events.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("save", "error")
                }
            }
        })


        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView(list: List<Article>) = binding.rcvSave.apply {
        adapter = NewsAdapter(list)
        layoutManager = LinearLayoutManager(this@SaveActivity)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteArticle: Article = list[viewHolder.adapterPosition]
                mainViewModel.deleteNews(context, deleteArticle)
                Log.d("delete-news", "${deleteArticle.title.toString()}")
            }
        })
    }
}