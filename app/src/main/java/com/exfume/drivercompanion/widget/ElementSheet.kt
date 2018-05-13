package com.exfume.drivercompanion.widget

import android.content.Context
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.widget.LinearLayout
import com.exfume.drivercompanion.R
import com.exfume.drivercompanion.viewpager.PagerAdapter
import com.exfume.drivercompanion.viewpager.PagerElement
import com.exfume.drivercompanion.views.OrbsView
import com.exfume.drivercompanion.views.SealView
import kotlinx.android.synthetic.main.element_sheet_layout.view.*

class ElementSheet : LinearLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.element_sheet_layout,this)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        viewPager.adapter = PagerAdapter(getTabs())
        tabLayout.setupWithViewPager(viewPager)
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.setIcon(getTabs()[i].icon)
        }
    }


    fun setTabSelectedListener(onTabSelected : (TabLayout.Tab) -> Unit, onTabReselected: (TabLayout.Tab) -> Unit) {
        tabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                onTabSelected(tab!!)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabReselected(tab!!)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
        })
    }


    private fun getTabs() : Array<PagerElement> {
        return arrayOf(
                PagerElement(R.drawable.nav_orbs, "Orbs", OrbsView(context)),
                PagerElement(R.drawable.nav_seal,"Seal Effect", SealView(context))
        )
    }

}