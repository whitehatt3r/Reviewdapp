package com.example.reviewdapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IndividualProviderActivity : AppCompatActivity() {

    private lateinit var providerImageView: ImageView
    private lateinit var providerNameTextView: TextView
    private lateinit var providerRatingBar: RatingBar
    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var reviewAdapter: ReviewsAdapter
    private lateinit var writeReviewButton: Button
    private var providerId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_provider)

        databaseHelper = DatabaseHelper(this)
        providerId = intent.getIntExtra("PROVIDER_ID", -1)
        if (providerId == -1) {
            finish() // Finish the activity if no provider ID was passed
            return
        }

        // Set up the views
        providerImageView = findViewById(R.id.providerImageView)
        providerNameTextView = findViewById(R.id.providerNameTextView)
        providerRatingBar = findViewById(R.id.providerRatingBar)
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)
        writeReviewButton = findViewById(R.id.button_write_review)

        // Set up RecyclerView for reviews
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        reviewAdapter = ReviewsAdapter(mutableListOf())
        reviewsRecyclerView.adapter = reviewAdapter

        // Load provider details and reviews
        loadProviderDetails(providerId)
        refreshReviews()

        // Set up the button to write a review
        writeReviewButton.setOnClickListener {
            showReviewDialog()
        }
    }

    private fun loadProviderDetails(providerId: Int) {
        // Fetch provider details from the database and populate the Views
        val cursor = databaseHelper.getProvider(providerId)
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
            val imageResName = cursor.getString(cursor.getColumnIndexOrThrow("imageResId"))
            val imageResId = resources.getIdentifier(imageResName, "drawable", packageName)

            providerNameTextView.text = name
            providerRatingBar.rating = rating
            providerImageView.setImageResource(imageResId)
        }
        cursor.close()
    }

    private fun refreshReviews() {
        // Fetch reviews from the database and update the RecyclerView
        val reviews = mutableListOf<Review>()
        databaseHelper.getReviews(providerId).use { cursor ->
            while (cursor.moveToNext()) {
                val comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"))
                val rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
                reviews.add(Review(comment, rating))
            }
        }
    }

    private fun showReviewDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val parentViewGroup = findViewById<ViewGroup>(android.R.id.content)
        val reviewDialogView = layoutInflater.inflate(R.layout.dialog_review, parentViewGroup, false)

        val reviewEditText = reviewDialogView.findViewById<EditText>(R.id.editTextReview)
        val reviewRatingBar = reviewDialogView.findViewById<RatingBar>(R.id.ratingBarDialog)

        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.write_review))
            setView(reviewDialogView)
            setPositiveButton(getString(R.string.submit)) { dialog, _ ->
                val reviewText = reviewEditText.text.toString()
                val reviewRating = reviewRatingBar.rating
                submitReview(reviewText, reviewRating)
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            create()
        }.show()
    }


    private fun submitReview(reviewText: String, reviewRating: Float) {
        if (reviewText.isNotEmpty()) {
            databaseHelper.addReview(providerId, reviewRating, reviewText)
            onReviewAdded()
        } else {
            Toast.makeText(this, getString(R.string.review_error_empty), Toast.LENGTH_LONG).show()
        }
    }

    private fun onReviewAdded() {
        refreshReviews() // Refresh the reviews list to include the new review
    }

    data class Review(
        val comment: String,
        val rating: Float
    )
}
