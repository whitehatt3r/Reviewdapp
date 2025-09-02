package com.example.reviewdapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reviewdapp.R.layout.activity_main

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var categoryAdapter: CategoryAdapter
    private var categoryItems: List<CategoryItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        // Check if the user is logged in
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        if (sharedPref.all.isEmpty()) {
            startActivity(Intent(this, LoginSignupActivity::class.java))
            finish()
            return
        }

        // Set up the RecyclerView for Categories
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        categoryItems = createDummyData()
        categoryAdapter = CategoryAdapter(object : CategoryClickListener {

            override fun onCategoryItemClick(categoryName: String) {
                val intent = Intent(this@MainActivity, ServiceProvidersActivity::class.java)
                intent.putExtra("CATEGORY_NAME", categoryName)
                startActivity(intent)
            }
        })
        categoryAdapter.setData(categoryItems)
        recyclerView.adapter = categoryAdapter

        // Set up the SearchView
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = categoryItems.filter { it.name.contains(newText ?: "", ignoreCase = true) }
                categoryAdapter.setData(filteredList)
                return false
            }
        })
    }

    private fun createDummyData(): List<CategoryItem> {
        return listOf(
            CategoryItem(R.drawable.ic_plumbing, "Plumbing"),
            CategoryItem(R.drawable.ic_electrical, "Electrical"),
            CategoryItem(R.drawable.ic_construction, "Construction"),
            CategoryItem(R.drawable.ic_painting, "Painting"),
            CategoryItem(R.drawable.ic_landscaping, "Landscaping"),
            CategoryItem(R.drawable.ic_carpentry, "Carpentry")
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> true
            R.id.nav_categories -> true
            R.id.nav_account -> {
                startActivity(Intent(this, AccountActivity::class.java))
                true
            }
            R.id.nav_login_signup -> {
                startActivity(Intent(this, LoginSignupActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


