package com.abhir.musicapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val retroFit = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retroFit.getData("eminem")

        retrofitData.enqueue(object : Callback<com.abhir.musicapp.Response?> {
            override fun onResponse(
                call: Call<com.abhir.musicapp.Response?>,
                response: retrofit2.Response<com.abhir.musicapp.Response?>
            ) {
                val textView = findViewById<TextView>(R.id.tv)
                textView.text = "" + (response.body()?.total )
                Log.d("SUCCESS", response.body().toString())
            }

            override fun onFailure(call: Call<com.abhir.musicapp.Response?>, t: Throwable) {
                Log.d("FAILURE : ",t.message.toString())
            }
        })
    }
}


