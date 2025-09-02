package com.example.reviewdapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val itemClickListener: CategoryClickListener // Ensure this interface is correctly defined elsewhere
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var items: List<CategoryItem> = listOf()

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewCategory)
        private val textView: TextView = itemView.findViewById(R.id.textViewCategory)

        fun bind(categoryItem: CategoryItem, clickListener: CategoryClickListener) {
            imageView.setImageResource(categoryItem.icon)
            textView.text = categoryItem.name
            itemView.setOnClickListener { clickListener.onCategoryItemClick(categoryItem.name) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = items[position]
        holder.bind(categoryItem, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<CategoryItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}

interface CategoryClickListener {
    fun onCategoryItemClick(categoryName: String)
}







