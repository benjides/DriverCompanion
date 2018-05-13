package com.exfume.drivercompanion.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.exfume.drivercompanion.config.Constants
import com.exfume.drivercompanion.data.Element
import com.exfume.drivercompanion.util.Tree
import kotlin.math.min


class Chart : View {

    private val items : ArrayList<Item> = arrayListOf()

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintStroke: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mPath = Path()


    private val bounds : Array<RectF> = Array(4, { RectF() })

    private val radius = 110F
    private val ratio : Float by lazy { (width - 2 * radius) / 6 }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mPaint.style = Paint.Style.FILL
        mPaintStroke.style = Paint.Style.STROKE
        mPaintStroke.color = Constants.COLOR_WHITE
        mPaintStroke.strokeWidth = 6F
        prepareItems()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = min(widthMeasureSpec,heightMeasureSpec)
        super.onMeasure(size,size)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        setBounds()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        items.forEach { paintItem(canvas!!,it) }
    }


    private fun Tree<Element>.state() : Boolean {
        if (rightTree == null && leftTree == null) {
            return data.check
        }
        return rightTree?.state() ?: data.check && leftTree?.state() ?: true
    }


    /**
     * 360F/2F.pow(3 + depth)
     */
    private fun getSweep(depth : Int) : Float {
        return when (depth) {
            0 -> 45F
            1 -> 22.5F
            2 -> 11.25F
            else -> 0F
        }
    }
    private fun prepareItems() {
        Element.getCombos().forEachIndexed { index, tree ->
            buildTree(tree,45F*index)
            tree.data.onSwitchListener { invalidate() }
        }
    }
    private fun buildTree(elementTree : Tree<Element>, start_angle : Float)  {
        elementTree.prefix({ t, depth, index, siblings ->
            val sweep = getSweep(depth) * (3 - siblings)
            val offset = start_angle + getSweep(depth) * index
            val drawable = resources.getDrawable(t.data.icon,context.theme)
            drawable.setColorFilter(Constants.COLOR_ALPHA_WHITE,PorterDuff.Mode.SRC_IN)
            val item = Item(t,offset,sweep,getItemBounds(depth),drawable)
            items.add(item)
        })
    }
    private fun setBounds() {
        bounds.mapIndexed({index, rect ->
            val corner = ratio * index
            rect.set(corner,corner,width - corner,width - corner)
        })
        items.map { setDrawableRect(it) }
    }
    private fun getItemBounds(depth: Int) = Pair(bounds[3 - depth],bounds[2 - depth])
    private fun paintItem(canvas: Canvas, item : Item) {
        mPaint.color = if (!item.element.state()) item.element.data.color else Constants.COLOR_HIGHLIGHT
        setPath(item)
        canvas.drawPath(mPath,mPaint)
        canvas.drawPath(mPath,mPaintStroke)
        item.drawable.draw(canvas)
    }
    private fun setPath(item : Item) {
        mPath.reset()
        mPath.arcTo(item.bounds.second,item.offset,item.sweep,false)
        mPath.arcTo(item.bounds.first,item.offset+item.sweep, -item.sweep,false)
        mPath.close()
    }
    private fun setDrawableRect(item : Item) {
        item.apply {
            val r =  bounds.first.right - width/2 + (ratio)/2
            val alpha = Math.toRadians((offset + sweep/2).toDouble())
            val x = r * Math.cos(alpha) + width/2
            val y = r * Math.sin(alpha) + width/2
            item.drawable.bounds  = Rect((x - ratio/3).toInt(), (y - ratio/3).toInt() , (x + ratio/3).toInt() ,(y + ratio/3).toInt())
        }
    }

    class Item(val element: Tree<Element>,val offset: Float,val sweep: Float, var bounds : Pair<RectF, RectF>, val drawable: Drawable)
}