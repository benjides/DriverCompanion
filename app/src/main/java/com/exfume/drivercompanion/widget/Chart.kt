package com.exfume.drivercompanion.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.exfume.drivercompanion.data.Element
import com.exfume.drivercompanion.util.Tree


class Chart : View {

    private val TAG = "ChartWidget"
    val combos : List<Tree<Element>> = Element.getCombos()
    private var mPieBounds = RectF()

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintStroke: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mPath = Path()
    private var innerCircle = RectF()
    private var outerCircle = RectF()

    private val radius = 110F
    private val diameter : Int by lazy { width - paddingStart - paddingEnd }
    private val ratio : Float by lazy { (diameter - 2 * radius) / 6 }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mPaint.style = Paint.Style.FILL
        mPaintStroke.style = Paint.Style.STROKE
        mPaintStroke.color = Color.WHITE
        mPaintStroke.strokeWidth = 6F
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val diameter = Math.min(w, h).toFloat()
        mPieBounds = RectF(
                0.0F,
                0.0F,
                diameter,
                diameter)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 .. 7) {
            combos[i].apply {
                paintTree(this, 45F*i, canvas!!)
            }
        }
    }


    private fun paintTree(tree : Tree<Element>, start_angle : Float, canvas: Canvas) {
        tree.prefix({ t, depth, index, siblings ->
            val sweep = getSweep(depth) * (3 - siblings)
            val offset = start_angle + getSweep(depth) * index
            mPaint.color = t.data.color
            if (!t.shouldBePainted()) {
                paintSlice(offset,sweep,depth)

                canvas.drawPath(mPath,mPaint)
                canvas.drawPath(mPath,mPaintStroke)
                paintDrawable(offset,sweep,depth,t.data,canvas)
            }
        })
    }

    private fun paintSlice(offset : Float, sweep : Float, depth : Int) {
        val o = ratio * (2 - depth)
        val i = ratio * (3 - depth)
        innerCircle.set(i,i,width - i,width - i)
        outerCircle.set(o,o,width - o,width - o)
        mPath.reset()
        mPath.arcTo(outerCircle,offset,sweep,false)
        mPath.arcTo(innerCircle,offset+sweep, -sweep,false)
        mPath.close()
    }

    private fun paintDrawable(offset: Float, sweep: Float, depth: Int, element: Element, canvas: Canvas)  {
        val r = radius + depth*ratio + ratio/1.4
        val alpha = Math.toRadians((offset + sweep/2).toDouble())
        val x = r * Math.cos(alpha) + width/2
        val y = r * Math.sin(alpha) + width/2
        val rect = Rect((x - ratio/3).toInt(), (y - ratio/3).toInt() , (x + ratio/3).toInt() ,(y + ratio/3).toInt())
        val drawable = resources.getDrawable(element.icon,context.theme)
        drawable.setColorFilter(Color.parseColor("#90FFFFFF"),PorterDuff.Mode.SRC_IN)
        drawable.bounds = rect
        drawable.draw(canvas)
    }

    fun Tree<Element>.shouldBePainted() : Boolean {
        if (rightTree == null && leftTree == null) {
            return data.check
        }
        return rightTree?.shouldBePainted() ?: data.check && leftTree?.shouldBePainted() ?: true
    }


    private fun getSweep(depth : Int) : Float {
        return when (depth) {
            0 -> 45F
            1 -> 22.5F
            2 -> 11.25F
            else -> 0F
        }
    }

}