package com.example.a7zad.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.a5zad.ui.main.basket.CategoriesFragment
import com.example.a7zad.R
import com.example.a7zad.ui.main.create_product.CreateProductFragment
import com.example.a7zad.ui.main.products.ProductFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    val productFragment = ProductFragment.newInstance()
    val categoriesFragment = CategoriesFragment.newInstance()
    val createProductFragment =CreateProductFragment.newInstance()

    override fun getItem(position: Int): Fragment {
        if(position == 0) return productFragment
        if(position == 1) return categoriesFragment
        return createProductFragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}