package com.exfume.drivercompanion

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.exfume.drivercompanion.ui.orbs.OrbsContract
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.Fragment
import com.exfume.drivercompanion.ui.chart.ChartView
import com.exfume.drivercompanion.ui.orbs.OrbsView


class MainActivity : AppCompatActivity() , OrbsContract.View {


    override fun setPresenter(presenter: OrbsContract.Presenter) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        consume { OrbsView.newInstance() }
        bottomNavigationBar.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_orbs -> consume { OrbsView.newInstance() }
                R.id.nav_chart -> consume { ChartView.newInstance() }
                else -> {true}
            }
        }
    }

    private fun consume(f: () -> Fragment) : Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, f())
        transaction.addToBackStack(null)
        transaction.commit()
        return true
    }

}
