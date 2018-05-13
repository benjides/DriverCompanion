package com.exfume.drivercompanion.viewpager

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class PagerAdapter(private val tabs: Array<PagerElement>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = tabs[position].view
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, oView: Any): Boolean = view == oView as View

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence = ""
}

class PagerElement(val icon: Int, val title: String, val view: View)