package com.example.a8zad.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.a8zad.ui.main.product.ProductFragment
import com.example.a8zad.R
import com.example.a8zad.ui.main.basket.BasketFragment
import com.example.a8zad.ui.main.orders.OrdersFragment

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
    val basketFragment = BasketFragment.newInstance()
    val ordersFragment = OrdersFragment.newInstance()

    override fun getItem(position: Int): Fragment {
        if(position == 0) return productFragment
        if(position == 1) return basketFragment
        return ordersFragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}