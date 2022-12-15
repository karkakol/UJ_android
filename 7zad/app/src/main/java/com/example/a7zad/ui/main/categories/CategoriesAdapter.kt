package com.example.a5zad.ui.main.product
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7zad.databinding.CategoryListTileBinding
import com.example.a7zad.model.category.Category
import com.example.a7zad.model.product.Product
import com.example.a7zad.ui.main.PageViewModel

class CategoriesAdapter(private var data: List<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CategoryListTileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category1: Category) {
            binding.apply {
                category = category1
            }

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = CategoryListTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currItem = data[position]

        viewHolder.bind(currItem)
    }

    override fun getItemCount()=data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newData: List<Category>) {
        data = newData
        notifyDataSetChanged()
    }

}