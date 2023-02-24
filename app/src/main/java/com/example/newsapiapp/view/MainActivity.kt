package com.example.newsapiapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.newsapiapp.model.Article
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapiapp.R
import com.example.newsapiapp.adapter.NewsAdapter
import com.example.newsapiapp.adapter.SliderAdapter
import com.example.newsapiapp.adapter.TopAdapter
import com.example.newsapiapp.databinding.ActivityMainBinding
import com.example.newsapiapp.extra.Events
import com.example.newsapiapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val mainViewModel : MainViewModel by viewModels()
    var search : String = ""
    var sortBy : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getData("world", sortBy, "9850bc5968804dac9ba65c377b4220a2")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search = query!!
                mainViewModel.getData(search, sortBy, "9850bc5968804dac9ba65c377b4220a2")
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

            mainViewModel.getData(search, sortBy, "9850bc5968804dac9ba65c377b4220a2")
        }

        mainViewModel.data.observe(this, Observer {
            when (it) {
                is Events.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Events.Success -> {
                    it.let {
                        it.data?.let {
                            it2 -> setRecyclerAdapter(it2)
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
    }

    private fun setRecyclerAdapter(list: List<Article>) = binding.rcv.apply {
        adapter = NewsAdapter(list)
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

//    private fun setAdapter(list: List<Article>) {
//        sliderAdapter = SliderAdapter(list)
//        binding.imageSlider.apply {
//            setIndicatorAnimation(IndicatorAnimationType.WORM)
//            setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
//            startAutoCycle()
//        }
//    }
}