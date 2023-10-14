package com.example.astronomypictureoftheday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var astronomyImageUrl=""
    var astronomyTitle=""
    var astronomyDate=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button=findViewById<Button>(R.id.Get_random_image)
        val imageView= findViewById<ImageView>(R.id.astronomy_image)
        val title=findViewById<TextView>(R.id.image_title)
        val date=findViewById<TextView>(R.id.image_date)
        button.setOnClickListener{
            //make a network call to NASA API
            getAstronomyPhoto()
            Glide.with(this)
                . load(astronomyImageUrl)
                .fitCenter()
                .into(imageView)

            date.setText(astronomyDate)
            title.setText(astronomyTitle)

        }
    }
    private fun getAstronomyPhoto(){
        val client= AsyncHttpClient()

        client["https://api.nasa.gov/planetary/apod?api_key=JLq0M2hcg5K32iAj3GpVKqD0C2e1hgQSISRJDnqo", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Astronomy", "response successful $json")
                astronomyImageUrl= json.jsonObject.getString("url")
                Log.d("Astronomy", "$astronomyImageUrl image URL set")
                astronomyTitle=json.jsonObject.getString("title")
                Log.d("Astronomy", "$astronomyTitle title set")
                astronomyDate= json.jsonObject.getString("date")
                Log.d("Astronomy", "$astronomyDate date set")


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d ("Astronomy Error", errorResponse)
            }
        }]
    }


}