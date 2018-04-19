package com.exfume.drivercompanion.ui.chart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exfume.drivercompanion.R


class ChartView : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.chart_view_layout, container!!, false)
        Log.d("ChartView", "Instance id $this")

        return view
    }

    companion object {
        fun newInstance() : ChartView = ChartView()
    }
}