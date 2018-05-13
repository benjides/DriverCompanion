package com.exfume.drivercompanion

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.exfume.drivercompanion.data.Element
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()  {

    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    val elements = Element.getElements()
    val anim : AnimatedVectorDrawable by lazy { findViewById<FloatingActionButton>(R.id.resetButton).drawable as AnimatedVectorDrawable }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomSheet()
        resetButton.setOnClickListener {
            elements.map { it.reset() }
            //anim.start()
        }

    }

    private fun setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(elementSheet)
        elementSheet.setTabSelectedListener({
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        },{
            val state: Int = if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                BottomSheetBehavior.STATE_EXPANDED
            } else {
                BottomSheetBehavior.STATE_COLLAPSED
            }
            bottomSheetBehavior.state = state
        })
    }



}
