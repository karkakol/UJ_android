package com.example.a5zad.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.a5zad.R
import com.example.a5zad.ui.main.basket.BasketFragment
import com.example.a5zad.ui.main.product.ProductFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    val productFragment = ProductFragment.newInstance()
    val basketFragment = BasketFragment.newInstance()

    override fun getItem(position: Int): Fragment {
        if(position == 0) return productFragment
        return basketFragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}