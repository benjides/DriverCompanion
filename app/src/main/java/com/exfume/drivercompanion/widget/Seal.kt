package com.exfume.drivercompanion.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.exfume.drivercompanion.R
import com.exfume.drivercompanion.config.Constants
import com.exfume.drivercompanion.data.Element
import kotlinx.android.synthetic.main.seal_element.view.*

class Seal : LinearLayout {


    private val element: Element

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        inflate(context, R.layout.seal_element, this)

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.Orb, defStyleAttr, 0)
        element = Element.getElement(a.getString(R.styleable.Orb_element))
        a.recycle()
        sealText.text = resources.getString(R.string.seal, resources.getString(element.seal))
        sealIcon.setImageResource(element.icon)
        element.onSwitchListener {
            if (isAttachedToWindow)
                set()
        }
        set()
    }

    fun set() {
        if (!element.check) {
            sealText.setTextColor(Constants.COLOR_HIGHLIGHT)
            sealIcon.setColorFilter(Constants.COLOR_ALPHA_WHITE)
        } else {
            sealText.setTextColor(element.color)
            sealIcon.setColorFilter(element.color)
        }

    }
}