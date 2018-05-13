package com.exfume.drivercompanion.util

import android.graphics.Color
import android.support.annotation.ColorInt
import com.exfume.drivercompanion.data.Element

object Dsl {

    /*
    Element DSL builder
    */

    fun elements (block: ElementsBuilder.() -> Unit): Map<String,Element> = ElementsBuilder().apply(block).build()

    class ElementBuilder {
        var name: String = ""
        var icon: Int = 0
        @ColorInt private var c: Int = 0
        var color: String = ""
            set(value) {
                c = Color.parseColor(value)
            }
        var seal: Int = 0
        fun build(): Element = Element(name, icon, c, seal)
    }

    class ElementsBuilder {
        private var elements : MutableMap<String,Element> = mutableMapOf()

        fun element(block: ElementBuilder.() -> Unit) {
            val e = ElementBuilder().apply(block).build()
            elements[e.name] = e
        }

        fun build(): Map<String,Element> = elements
    }


    /*
    ElementCombo
    */

    fun elementCombos (block: ElementCombosBuilder.() -> Unit): Map<String,Tree<Element>> = ElementCombosBuilder().apply(block).build()

    class ElementComboBuilder {
        private var d : Element? = null
        var element : String = ""
            set(value) {
                d = Element.getElement(value)
            }

        private var rightTree : Tree<Element>? = null
        private var leftTree : Tree<Element>? = null

        fun rightTree (block: ElementComboBuilder.() -> Unit) {
            rightTree = ElementComboBuilder().apply(block).build()
        }
        fun leftTree (block: ElementComboBuilder.() -> Unit) {
            leftTree = ElementComboBuilder().apply(block).build()
        }

        fun build() : Tree<Element> = Tree(d!!,rightTree,leftTree)

    }

    class ElementCombosBuilder {
        private var elementCombos : MutableMap<String,Tree<Element>> = mutableMapOf()
        fun combo(block: ElementComboBuilder.() -> Unit) {
            val c = ElementComboBuilder().apply(block).build()
            elementCombos[c.data.name] = c
        }

        fun build(): Map<String,Tree<Element>> = elementCombos
    }






}