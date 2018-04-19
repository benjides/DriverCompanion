package com.exfume.drivercompanion.data

import android.support.annotation.ColorInt
import com.exfume.drivercompanion.config.ElementConfig.elements
import com.exfume.drivercompanion.config.CombosConfig.combos
import com.exfume.drivercompanion.util.Tree

class Element(val name: String, val icon: Int, @ColorInt val color: Int, var check : Boolean = false) {

    companion object {
        fun getElement(element : String) : Element = elements[element]!!
        fun getCombo(element: String) : Tree<Element> = combos[element]!!
        fun getCombos() :  List<Tree<Element>> = combos.toList().map { it.second }
    }
}

