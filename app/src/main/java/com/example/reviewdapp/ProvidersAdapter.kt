package com.example.reviewdapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Define the listener interface
interface ProviderActionsListener {
    fun onWriteReview(providerId: Int)
    fun onViewReviews(providerId: Int)
}

class ProvidersAdapter(
    private val providers: MutableList<ProviderItem>,
    private val listener: ProviderActionsListener  // Listener to handle the actions
) : RecyclerView.Adapter<ProvidersAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_provider, parent, false)
        return ProviderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider = providers[position]
        holder.bind(provider, listener)
    }

    override fun getItemCount(): Int = providers.size

    class ProviderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewProviderName)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarProvider)
        private val providerImageView: ImageView = itemView.findViewById(R.id.imageViewProvider)
        private val viewReviewsButton: ImageView = itemView.findViewById(R.id.imageViewViewReviews)
        private val writeReviewButton: ImageView = itemView.findViewById(R.id.imageViewWriteReview)

        fun bind(provider: ProviderItem, listener: ProviderActionsListener) {
            nameTextView.text = provider.name
            ratingBar.rating = provider.rating
            providerImageView.setImageResource(provider.imageResId)

            viewReviewsButton.setOnClickListener {
                listener.onViewReviews(provider.providerId)
            }

            writeReviewButton.setOnClickListener {
                listener.onWriteReview(provider.providerId)
            }
        }
    }
}



