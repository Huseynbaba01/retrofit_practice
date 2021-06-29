package com.example.retrofitbasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.retrofitbasics.retrofit.ApiInterface
import com.example.retrofitbasics.retrofit.Model
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .build()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val textView: TextView = findViewById(R.id.textView)
        val retrofitData = retrofitBuilder.getUser(1)
        retrofitData.enqueue(object :Callback<Model>{
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                textView.text = "SUCCESS "+ response.body()
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                textView.setText("Failure")
            }

        })
        val secondRetrofitData = retrofitBuilder.getUsers()
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        secondRetrofitData.enqueue(object :Callback<List<Model>>{
            override fun onResponse(call: Call<List<Model>>, response: Response<List<Model>>) {
                Log.i("MyTestHere","second onResponse Called")
                Toast.makeText(this@MainActivity,"second onResponse Called",Toast.LENGTH_SHORT).show()
                textView.setText(response.body().toString())
                progressBar.visibility = ProgressBar.VISIBLE
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"second onFailure Called",Toast.LENGTH_SHORT).show()
                Log.i("MyTestHere","second onFailure Called")
                progressBar.visibility = ProgressBar.INVISIBLE
            }

        })
    }
}