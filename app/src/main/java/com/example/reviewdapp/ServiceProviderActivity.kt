package com.example.reviewdapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ServiceProvidersActivity : AppCompatActivity(), ProviderActionsListener {
    private lateinit var providersRecyclerView: RecyclerView
    private lateinit var providersAdapter: ProvidersAdapter
    private lateinit var categoryItems: MutableList<ProviderItem>
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_providers)

        databaseHelper = DatabaseHelper(this)

        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Unknown"
        supportActionBar?.title = categoryName

        providersRecyclerView = findViewById(R.id.providersRecyclerView)
        providersRecyclerView.layoutManager = LinearLayoutManager(this)

        categoryItems = getProvidersFromDb(categoryName)
        providersAdapter = ProvidersAdapter(categoryItems, this)
        providersRecyclerView.adapter = providersAdapter
    }

    private fun getProvidersFromDb(categoryName: String): MutableList<ProviderItem> {
        val providersList = mutableListOf<ProviderItem>()
        val cursor = databaseHelper.getAllProvidersByCategory(categoryName)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
            val imageResName = cursor.getString(cursor.getColumnIndexOrThrow("imageResId"))
            val imageResId = resources.getIdentifier(imageResName, "drawable", packageName)
            providersList.add(ProviderItem(id, name, rating, imageResId))
        }
        cursor.close()
        return providersList
    }

    // These methods should match exactly the methods declared in the ProviderActionsListener interface
    override fun onWriteReview(providerId: Int) {
        val intent = Intent(this, WriteReviewActivity::class.java)
        intent.putExtra("PROVIDER_ID", providerId)
        startActivity(intent)
    }

    override fun onViewReviews(providerId: Int) {
        val intent = Intent(this, ViewReviewsActivity::class.java)
        intent.putExtra("PROVIDER_ID", providerId)
        startActivity(intent)
    }
}

// Ensure this data class is visible to the ProvidersAdapter
data class ProviderItem(
    val providerId: Int,
    val name: String,
    val rating: Float,
    val imageResId: Int
)



