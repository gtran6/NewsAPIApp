package com.example.newsapiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.newsapiapp.adapter.NewsAdapter
import com.example.newsapiapp.databinding.ActivitySaveBinding
import com.example.newsapiapp.extra.Events
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveActivity : AppCompatActivity() {
    lateinit var binding: ActivitySaveBinding
    val mainViewModel: MainViewModel by viewModels()
    private val newsAdapter by lazy { NewsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

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
                            newsAdapter.differ.submitList(it2)
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

        val article = intent.getParcelableExtra<Article>("article")
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (article != null) {
                    val position = viewHolder.adapterPosition
                    val news_item = newsAdapter.differ.currentList[position]
                    mainViewModel.deleteNews(context = applicationContext, news_item)

                    Snackbar.make(binding.saveLayout, "News Deleted", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo") {
                            mainViewModel.insertNews(context, news_item)
                        }
                        show()
                    }
                    //Log.d("delete-news", "${article.title.toString()}")
//                        mainViewModel.data?.value?.data = mainViewModel.data.value?.data?.toMutableList()
//                            ?.apply {
//                                removeAt(viewHolder.adapterPosition)
//                            }?.toList()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvSave)
    }

    private fun setupRecyclerView() = binding.rcvSave.apply {
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(this@SaveActivity)
    }
}