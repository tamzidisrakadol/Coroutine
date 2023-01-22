package com.example.androidcourutine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteDaoTest {

    //should  complete the test synchronously that's why init the Rule
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var quoteDatabase:QuoteDatabase
    lateinit var quotesDao: QuotesDao



    //this is not an actual Room Database.It is a Memory which store data just like Room and it will emit after completing the test
    @Before
    fun setup(){
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()

        quotesDao=quoteDatabase.quotesDao()
    }


    //insert the quote in database and the see the expected result
    @Test
    fun insertSingleQuote(){
        runBlocking {
            val quote = Quote(0,"This is a single Quote","Tamzid Israk")
            quotesDao.insertQuote(quote)

            val result = quotesDao.getQuotes().getOrAwaitValue()

            Assert.assertEquals(1,result.size)
            Assert.assertEquals("This is a single Quote",result[0].text)

        }
    }


    //insert the quote first and then delete it , then see the expected result
    @Test
    fun deleteQuote(){
        runBlocking {
            val quote = Quote(1,"This a delete quote","TAMZID")
            quotesDao.insertQuote(quote)
            quotesDao.delete()

            val expectedResult =  quotesDao.getQuotes().getOrAwaitValue()
            Assert.assertEquals(0,expectedResult.size)
        }
    }

    @After
    fun tearDown(){
        quoteDatabase.close()
    }
}