package com.example.newsapiapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.newsapiapp.databinding.SliderItemBinding
import com.example.newsapiapp.model.ArticleX
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(val list: List<ArticleX>) : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(var binding: SliderItemBinding) : SliderViewAdapter.ViewHolder(binding.root)

    override fun getCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        return SliderViewHolder(SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.binding.apply {
            Glide.with(viewHolder.itemView)
                .load(list[position].urlToImage).fitCenter()
                .into(viewHolder.binding.imageSlider)
            //Log.d("image","${list[position].urlToImage.toString()}")
        }
    }
}