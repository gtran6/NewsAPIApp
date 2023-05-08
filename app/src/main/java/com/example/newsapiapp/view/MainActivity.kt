package com.example.newsapiapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapiapp.R
import com.example.newsapiapp.adapter.NewsAdapter
import com.example.newsapiapp.adapter.SliderAdapter
import com.example.newsapiapp.databinding.ActivityMainBinding
import com.example.newsapiapp.extra.Events
import com.example.newsapiapp.extra.Utils.API_KEY
import com.example.newsapiapp.model.ArticleX
import com.example.newsapiapp.viewmodel.MainViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels()
    var search: String = ""
    var sortBy: String = ""
    private val newsAdapter by lazy { NewsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerAdapter()

        mainViewModel.getData("android", sortBy, API_KEY)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search = query!!
                mainViewModel.getData(search, sortBy, API_KEY)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.popularButton -> sortBy = "popularity"
                R.id.relevantButton -> sortBy = "relevancy"
                R.id.recentButton -> sortBy = "publishedAt"
            }
            mainViewModel.getData(search, sortBy, API_KEY)
        }

        mainViewModel.data.observe(this, Observer {
            when (it) {
                is Events.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Events.Success -> {
                    it.let {
                        it.data?.let { it2 ->
                            newsAdapter.differ.submitList(it2)
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                }
                is Events.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        binding.cardView2.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.getDataTopHeadlines("flower", API_KEY)

        mainViewModel.headlinesResponse.observe(this, Observer {
            when (it) {
                is Events.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.e("headline", "loading")
                }
                is Events.Success -> {
                    it.let {
                        it.data?.let { it1 ->
                            setAdapter(it1)
                        }
                    }
                    binding.progressBar.visibility = View.GONE
                    Log.e("headline", "success")
                }
                is Events.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("headline", "error")
                }
            }
        })

        binding.save.setOnClickListener {
            val intent = Intent(this, SaveActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRecyclerAdapter() = binding.rcv.apply {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = newsAdapter
    }

    private fun setAdapter(list: List<ArticleX>) = binding.imageSlider.apply {
        setSliderAdapter(SliderAdapter(list))
        setIndicatorAnimation(IndicatorAnimationType.SWAP)
        setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        startAutoCycle()
        scrollTimeInSec = 3
        setOnClickListener {
            val intent = Intent(this@MainActivity, DetailsActivity::class.java)
            startActivity(intent)
        }
    }
}