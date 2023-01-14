package com.example.androidcourutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {

    val baseUrl="https://jsonplaceholder.typicode.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val fetchBtn:Button = findViewById(R.id.fetchBtn)


        fetchBtn.setOnClickListener {
            fetchData()
        }


    }

    private fun fetchData(){
        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            val responses = api.getComments().awaitResponse()
            if (responses.isSuccessful){
                for (comments in responses.body()!!){
                    Log.d("tag",comments.id.toString())
                }
            }
        }
    }
}