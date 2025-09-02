package com.example.reviewdapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class ViewReviewsActivity : AppCompatActivity() {

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private var providerId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reviews)

        providerId = intent.getIntExtra("PROVIDER_ID", -1)
        if (providerId == -1) finish() // Exit if provider ID is not passed

        databaseHelper = DatabaseHelper(this)
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        loadReviews()
    }

    private fun loadReviews() {
        val reviewsList = mutableListOf<Review>()
        val cursor = databaseHelper.getReviews(providerId)

        while (cursor.moveToNext()) {
            val comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
            reviewsList.add(Review(comment, rating))
        }
        cursor.close() // It's important to close the cursor when you're done with it

        reviewsAdapter =
            ReviewsAdapter(reviewsList) // Now reviewsAdapter is being provided with the correct data type
        reviewsRecyclerView.adapter = reviewsAdapter
    }
}


