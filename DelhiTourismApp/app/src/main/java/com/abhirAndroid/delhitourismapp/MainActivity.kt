package com.abhirAndroid.delhitourismapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var currImageId = 0
        lateinit var image:ImageView
        val delhiPlaces = arrayOf("Humayun's Tomb","India Gate","Red Fort","Humayun's Tomb Evening","Qutub Minar")


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var nextBtn = findViewById<ImageView>(R.id.forImg)
        var prevBtn = findViewById<ImageView>(R.id.prevImg)
        val placeText = findViewById<TextView>(R.id.descTV)

        nextBtn.setOnClickListener {

            // Creating the name of the id
            var currImageString = "mainImg$currImageId"
            // Find the identifier of type "id" of the resource from our page using "this" and "resources"
            var currImageIdentifier = this.resources.getIdentifier(currImageString,"id",packageName)
            image = findViewById(currImageIdentifier)

            // Alpha is used to hide or unhide the image
            image.alpha = 0f

            currImageId = (currImageId+1) % 5

            var imageToShowString = "mainImg$currImageId"
            var imageToShowIdentifier = this.resources.getIdentifier(imageToShowString,"id",packageName)
            image = findViewById(imageToShowIdentifier)
            image.alpha = 1f
            placeText.text= delhiPlaces[currImageId]


        }

        prevBtn.setOnClickListener {

            var currImageString = "mainImg$currImageId"
            var currImageIdentifier = this.resources.getIdentifier(currImageString,"id",packageName)
            image = findViewById<ImageView>(currImageIdentifier)
            image.alpha = 0f

            currImageId = (5 + currImageId - 1) % 5

            var imageToShowString = "mainImg$currImageId"
            var imageToShowIdentifier = this.resources.getIdentifier(imageToShowString,"id",packageName)
            image = findViewById<ImageView>(imageToShowIdentifier)
            image.alpha = 1f
            placeText.text= delhiPlaces[currImageId]
        }


    }
}