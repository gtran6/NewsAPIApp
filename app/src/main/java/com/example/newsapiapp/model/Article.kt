package com.example.newsapiapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        Gson().fromJson(parcel.readString(),Source::class.java),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(content)
        dest.writeString(description)
        dest.writeString(publishedAt)
        dest.writeString( Gson().toJson(source))
        dest.writeString(title)
        dest.writeString(url)
        dest.writeString(urlToImage)
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}