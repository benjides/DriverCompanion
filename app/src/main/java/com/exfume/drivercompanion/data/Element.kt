package com.exfume.drivercompanion.data

import android.support.annotation.ColorInt
import com.exfume.drivercompanion.config.CombosConfig.combos
import com.exfume.drivercompanion.config.ElementConfig.elements
import com.exfume.drivercompanion.util.Tree

class Element(val name: String, val icon: Int, @ColorInt val color: Int, val seal : Int) {

    var check : Boolean = false
    set(value) {
        field = value
        listeners.map { it(value) }
    }
    private val listeners : ArrayList<(Boolean) -> Unit> = arrayListOf()
    private val resetListeners : ArrayList<() -> Unit> = arrayListOf()

    fun reset() {
        resetListeners.map { it() }
        check = false
    }

    fun toggle() {
        check = !check
    }

    fun onSwitchListener(onSwitch : (Boolean) -> Unit) {
      listeners.add(onSwitch)
    }

    fun onResetListener(onReset : () -> Unit) {
        resetListeners.add(onReset)
    }

    companion object {
        fun getElement(element : String) : Element = elements[element]!!
        fun getElements() : List<Element> = elements.toList().map { it.second }
        fun getCombos() :  List<Tree<Element>> = combos.toList().map { it.second }
    }
}

