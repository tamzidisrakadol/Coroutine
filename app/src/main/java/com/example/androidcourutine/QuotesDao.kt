package com.example.androidcourutine

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface QuotesDao {

    @Insert
    suspend fun insertQuote(quote:Quote)

    @Update
    suspend fun updateQuote(quote: Quote)

    @Query("DELETE FROM QUOTE")
    suspend fun delete()

    @Query("SELECT * FROM quote")
    fun getQuotes():LiveData<List<Quote>>

    @Query("SELECT * FROM QUOTE WHERE id = :quoteID")
    suspend fun getQuoteByID(quoteID:Int):Quote
}