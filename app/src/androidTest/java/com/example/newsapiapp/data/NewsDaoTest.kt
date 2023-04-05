package com.example.newsapiapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.newsapiapp.model.Article
import com.example.newsapiapp.model.Source
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class NewsDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var dao: NewsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.newsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNews() = runBlockingTest {
        val newsItem = Article("author",
            "content",
            "description",
            "published at",
            source = Source("id", "name"),
            "title",
            "url",
            "url to image")
        dao.insertNews(newsItem)
    }
}