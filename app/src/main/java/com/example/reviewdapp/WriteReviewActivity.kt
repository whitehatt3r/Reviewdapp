package com.example.reviewdapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WriteReviewActivity : AppCompatActivity() {

    private lateinit var submitButton: Button
    private lateinit var reviewEditText: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var databaseHelper: DatabaseHelper
    private var providerId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        providerId = intent.getIntExtra("PROVIDER_ID", -1)
        if (providerId == -1) {
            Toast.makeText(this, "No provider ID provided", Toast.LENGTH_SHORT).show()
            finish()  // Finish activity if no provider ID was passed
        }

        databaseHelper = DatabaseHelper(this)
        reviewEditText = findViewById(R.id.editTextReview)
        ratingBar = findViewById(R.id.ratingBar)
        submitButton = findViewById(R.id.buttonSubmit)

        submitButton.setOnClickListener {
            val reviewText = reviewEditText.text.toString()
            val reviewRating = ratingBar.rating
            if (reviewText.isNotEmpty()) {
                databaseHelper.addReview(providerId, reviewRating, reviewText)
                Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after submitting the review
            } else {
                Toast.makeText(this, "Please enter some text for the review.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

