package com.exfume.drivercompanion.widget

import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import com.exfume.drivercompanion.R
import com.exfume.drivercompanion.data.Element

class Orb : FrameLayout, View.OnTouchListener {

    private val TAG = "OrbView"
    private val element : Element

    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)

    private val backgroundView : View by lazy { View(context) }
    private val imageView : ImageView by lazy { ImageView(context) }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.Orb, defStyleAttr, 0)
        element = Element.getElement(a.getString(R.styleable.Orb_element))
        a.recycle()

        setOnTouchListener(this)

        background = resources.getDrawable(R.drawable.circle_ripple,null)

        setupView(backgroundView) {
            backgroundView.visibility = View.GONE
            backgroundView.background = resources.getDrawable(R.drawable.circle,null)
            backgroundView.background.setColorFilter(element.color, PorterDuff.Mode.SRC_OVER)
        }

        setupView(imageView) {
            imageView.setImageResource(element.icon)
            imageView.setColorFilter(Color.parseColor("#FFFFFF"))
        }

    }


    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    private fun toggle() {
        element.check = !element.check
    }

    fun reset() {
        if(element.check) {
            animate(width/2,height/2)
            element.check = false
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        event?.let {
            when(event.action) {
                MotionEvent.ACTION_DOWN -> background.setHotspot(event.x,event.y)
                MotionEvent.ACTION_UP -> {
                    event.apply {
                        if (x > 0 && x < width && y > 0 && y < height)
                            animate(event.x.toInt(),event.y.toInt())
                    }
                }
                else -> {}
            }
        }
        return false
    }

    private fun animate(x : Int, y : Int) {
        val anim : Animator
        if (element.check) {
            anim = ViewAnimationUtils.createCircularReveal(this.backgroundView,
                    x,
                    y,
                    this.height.toFloat() * 1.2F,
                    0F)
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    backgroundView.visibility = View.GONE
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })

        } else {
            this.backgroundView.visibility = View.VISIBLE
            anim = ViewAnimationUtils.createCircularReveal(this.backgroundView,
                    x,
                    y,
                    0F,
                    this.height.toFloat())
        }
        anim.start()
    }

    private fun setupView(view: View, body : () -> Unit) {
        body()
        addView(view)
    }

    public override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (element.check) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    internal class SavedState : View.BaseSavedState {
        var checked: Boolean = false

        constructor(superState: Parcelable) : super(superState)

        private constructor(source : Parcel) : super(source) {
            checked = source.readByte().toInt() != 0
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeByte((if (checked) 1 else 0).toByte())
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source : Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.checked = element.check
        return savedState
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        element.check = savedState.checked
        requestLayout()
    }

}