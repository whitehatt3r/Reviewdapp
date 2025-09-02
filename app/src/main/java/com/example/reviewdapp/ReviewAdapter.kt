package com.example.reviewdapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Define the data class for a review
data class Review(
    val comment: String,
    val rating: Float
)

class ReviewsAdapter(private val reviews: MutableList<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int = reviews.size

    // Method to update the reviews list
    fun updateReviews(newReviews: List<Review>) {
        reviews.clear()
        reviews.addAll(newReviews)
        notifyDataSetChanged()  // Notify any registered observers that the data set has changed.
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentTextView: TextView = itemView.findViewById(R.id.review_comment)
        private val ratingTextView: TextView = itemView.findViewById(R.id.review_rating)

        fun bind(review: Review) {
            commentTextView.text = review.comment
            ratingTextView.text = itemView.context.getString(R.string.review_rating_format, review.rating)
        }
    }
}
