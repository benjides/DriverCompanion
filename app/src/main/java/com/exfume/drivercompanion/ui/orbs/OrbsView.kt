package com.exfume.drivercompanion.ui.orbs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exfume.drivercompanion.R
import com.exfume.drivercompanion.widget.Orb
import kotlinx.android.synthetic.main.orb_view_layout.*

class OrbsView : Fragment() , OrbsContract.View {

    lateinit var orbs : Array<Orb>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.orb_view_layout, container!!, false)
        Log.d("OrbsView", "Instance id $this")
        orbs = arrayOf(R.id.fire, R.id.water,
                R.id.earth, R.id.wind,
                R.id.ice, R.id.electric,
                R.id.light, R.id.dark).map { view.findViewById<Orb>(it) }.toTypedArray()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetButton.setOnClickListener{ orbs.map { it.reset() }
        }
    }

    override fun setPresenter(presenter: OrbsContract.Presenter) {}


    companion object{
        fun newInstance() : OrbsView = OrbsView()
    }

}