package com.example.retrofitbasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.retrofitbasics.retrofit.ApiInterface
import com.example.retrofitbasics.retrofit.Model
import com.example.retrofitbasics.retrofit.UserAoth
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var textInputName: EditText
    private lateinit var textInputId: EditText
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        button = findViewById(R.id.button_second_activity)
        textView = findViewById(R.id.text_view_post)
        textInputName = findViewById(R.id.name_input)
        textInputId = findViewById(R.id.id_input)
        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://api.github.com/")
            .build()
            .create(ApiInterface::class.java)

        button.setOnClickListener {
            val model = Model(null,null,null,null,
                null,null,null,textInputId.text.toString().toInt(),
                textInputName.text.toString(),null,null,
                null,null,null,
                null,null,null,null)
            val retrofitData = retrofitBuilder.createUser(model = model)
            retrofitData.enqueue(object :Callback<UserAoth>{
                override fun onResponse(call: Call<UserAoth>, response: Response<UserAoth>) {
                    textView.text = response.body().toString()
                    Toast.makeText(this@SecondActivity,"OnResponse Called",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<UserAoth>, t: Throwable) {
                    Toast.makeText(this@SecondActivity,"OnFailure Called",Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}