package com.example.reviewdapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=ON")

        // Create table for providers
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS providers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                rating FLOAT,
                category TEXT,
                imageResId TEXT
            )
        """.trimIndent())

        // Create table for reviews
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS reviews (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                providerId INTEGER,
                rating FLOAT,
                comment TEXT,
                FOREIGN KEY(providerId) REFERENCES providers(id)
            )
        """.trimIndent())

        // Insert initial provider data
        insertInitialProviderData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS providers")
        db.execSQL("DROP TABLE IF EXISTS reviews")
        onCreate(db)
    }

    private fun insertInitialProviderData(db: SQLiteDatabase) {
        // Insert providers into the providers table
        val providers = arrayOf(
            // Plumbing providers
            arrayOf("Ace's Plumbing", 4.5f, "Plumbing", "plumbing1"),
            arrayOf("Sally's Plumbing", 4.8f, "Plumbing", "plumbing2"),
            arrayOf("Jerry's Plumbing", 4.0f, "Plumbing", "plumbing3"),
            arrayOf("Tim's Plumbing", 3.5f, "Plumbing", "plumbing4"),
            arrayOf("Mario's Plumbing", 5.0f, "Plumbing", "plumbing5"),
            // Electrical providers
            arrayOf("R and R Electrical", 4.3f, "Electrical", "elect1"),
            arrayOf("Handsome Rob's Electrical Surplus", 4.1f, "Electrical", "elect2"),
            arrayOf("John's Electronics", 4.6f, "Electrical", "elect3"),
            arrayOf("Simpson Family Electric", 4.8f, "Electrical", "elect4"),
            arrayOf("Radio Shack", 4.0f, "Electrical", "elect5"),
            // Construction providers
            arrayOf("Build Right Construction", 4.2f, "Construction", "const1"),
            arrayOf("Mighty Frames Builders", 4.7f, "Construction", "const2"),
            arrayOf("Skyline Erectors", 3.9f, "Construction", "const3"),
            arrayOf("Ground Up Constructions", 4.5f, "Construction", "const4"),
            arrayOf("Hammer Down Builders", 4.8f, "Construction", "const5"),
            // Painting providers
            arrayOf("Splash of Color Painting", 4.3f, "Painting", "paint1"),
            arrayOf("Brush Masters", 4.5f, "Painting", "paint2"),
            arrayOf("Roller Ready Paint Co.", 3.8f, "Painting", "paint3"),
            arrayOf("Wall Wizards", 4.7f, "Painting", "paint4"),
            arrayOf("Palette Pros Painting", 4.9f, "Painting", "paint5"),
            // Landscaping providers
            arrayOf("Green Thumb Landscapers", 4.6f, "Landscaping", "land1"),
            arrayOf("Lawn Legends", 4.2f, "Landscaping", "land2"),
            arrayOf("Plant Pioneers", 4.0f, "Landscaping", "land3"),
            arrayOf("Earth Movers Landscaping", 4.8f, "Landscaping", "land4"),
            arrayOf("Nature Nurturers", 4.5f, "Landscaping", "land5"),
            // Carpentry providers
            arrayOf("Craftsman Creations", 4.1f, "Carpentry", "carpen1"),
            arrayOf("Wood Works Specialists", 4.3f, "Carpentry", "carpen2"),
            arrayOf("Precision Joiners", 4.5f, "Carpentry", "carpen3"),
            arrayOf("Timber Tailors", 4.7f, "Carpentry", "carpen4"),
            arrayOf("Cut Above Carpentry", 4.9f, "Carpentry", "carpen5")
        )

        providers.forEach { provider ->
            val contentValues = ContentValues().apply {
                put("name", provider[0] as String)
                put("rating", provider[1] as Float)
                put("category", provider[2] as String)
                put("imageResId", provider[3] as String)
            }
            db.insert("providers", null, contentValues)
        }
    }

    fun getAllProvidersByCategory(category: String): Cursor {
        val db = this.readableDatabase
        return db.query(
            "providers", // Table name
            arrayOf("id", "name", "rating", "imageResId"), // Columns to return
            "category = ?", // Selection criteria
            arrayOf(category), // Selection arguments
            null, // Group by
            null, // Having
            null  // Order by
        )
    }

    fun getProvider(providerId: Int): Cursor {
        val db = this.readableDatabase
        return db.query(
            "providers",
            arrayOf("id", "name", "rating", "imageResId"),
            "id = ?",
            arrayOf(providerId.toString()),
            null, null, null
        )
    }

    // Method to insert a review into the database
    fun addReview(providerId: Int, rating: Float, comment: String) {
        val values = ContentValues().apply {
            put("providerId", providerId)
            put("rating", rating)
            put("comment", comment)
        }
        writableDatabase.insert("reviews", null, values)
    }

    // Method to retrieve reviews for a specific provider
    fun getReviews(providerId: Int): Cursor {
        return readableDatabase.rawQuery("SELECT * FROM reviews WHERE providerId = ?", arrayOf(providerId.toString()))
    }

    companion object {
        const val DATABASE_NAME = "serviceProviders.db"
        const val DATABASE_VERSION = 1
    }
}

