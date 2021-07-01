package com.example.retrofitbasics

import android.Manifest
import android.graphics.Picture
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.retrofitbasics.retrofit.ApiInterface
import com.example.retrofitbasics.retrofit.Model
import com.example.retrofitbasics.retrofit.UserAuth
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class SecondActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var textInputName: EditText
    private lateinit var textInputId: EditText
    private lateinit var textView: TextView
    private lateinit var client: OkHttpClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        button = findViewById(R.id.button_second_activity)
        textView = findViewById(R.id.text_view_post)
        textInputName = findViewById(R.id.name_input)
        textInputId = findViewById(R.id.id_input)

        /*var client_second = OkHttpClient()

        var request: Request = Request.Builder()
            .url("https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/search/TrendingNewsAPI?pageNumber=1&pageSize=10&withThumbnails=false&location=us")
            .get()
            .addHeader("x-rapidapi-key", "b9d2a10510mshee2d210c90170a0p1024b8jsn23fe0949483b")
            .addHeader("x-rapidapi-host", "contextualwebsearch-websearch-v1.p.rapidapi.com")
            .build()

        var response: okhttp3.Response = client_second.newCall(request).execute()*/



        val interceptor = HttpLoggingInterceptor()
        client = OkHttpClient.Builder()
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
            val permissionsArray:Array<String> = arrayOf( Manifest.permission.READ_EXTERNAL_STORAGE )
            ActivityCompat.requestPermissions(this, permissionsArray, 1)
            val model = Model(null,null,null,null,
                null,null,null,textInputId.text.toString().toInt(),
                textInputName.text.toString(),null,null,
                null,null,null,
                null,null,null,null)
//            val retrofitData = retrofitBuilder.createUser(model = model)
            val retrofitData = retrofitBuilder.createUser(textInputId.text.toString().toInt(),textInputName.text.toString())
            val file = File("/storage/emulated/0/DCIM/Screenshots/Screenshot_2021-06-30-23-14-26-687_com.whatsapp.jpg")

            val secondRetrofitData = retrofitBuilder.sendFile(file.asRequestBody("image/*".toMediaTypeOrNull()))
            retrofitData.enqueue(object :Callback<UserAuth>{
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {
                    textView.text = response.body().toString()
//                    Toast.makeText(this@SecondActivity,"OnResponse Called",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(this@SecondActivity,"OnFailure Called",Toast.LENGTH_SHORT).show()
                }

            })
            secondRetrofitData.enqueue(object :Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Toast.makeText(this@SecondActivity,response.body().toString(),Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@SecondActivity,"OnFailureSecond is called",Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
//    fun uploadFile(){
//        val file = File("C:\\Users\\Admin\\Pictures\\th.jpg")
//        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val part =  MultipartBody.Part.createFormData("my_new_image", file.name,requestBody)
//        val data= RequestBody.create("text/plain".toMediaTypeOrNull(),"This is a new image")
//        val retrofit = NetworkClient
//        val apiInterface: ApiInterface = retrofit.create(...)
//        val call = Call
//
//        /*val requestBody = RequestBody.create(MultipartBody.FORM,"MyContentHere")
//        val filePart = RequestBody.create(MediaType.parse(contentResolver.getType(fileUrl)))*/
//    }


    /*  val retrofitData = retrofitBuilder.createUser(textInputId.text.toString().toInt(),textInputName.text.toString())
            val file = File("C:\\Users\\Admin\\Pictures\\th.jpg")

            val partFile = RequestBody.create("JPG".toMediaTypeOrNull(),file)
            val data = MultipartBody.Part.createFormData("file_name","file_actual_name",partFile)
            val secondRetrofitData = retrofitBuilder.sendFile(
                MultipartBody.)
            retrofitData.enqueue(object :Callback<UserAuth>{
                override fun onResponse(call: Call<UserAuth>, response: Response<UserAuth>) {
                    textView.text = response.body().toString()
//                    Toast.makeText(this@SecondActivity,"OnResponse Called",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<UserAuth>, t: Throwable) {
                    Toast.makeText(this@SecondActivity,"OnFailure Called",Toast.LENGTH_SHORT).show()
                }

            })*/


}