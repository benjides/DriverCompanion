package com.exfume.drivercompanion.util

class Tree<T>(val data : T, var rightTree: Tree<T>? = null, var leftTree: Tree<T>? = null) {


    fun iterator(it : (T) -> Unit) {
        it(data)
        rightTree?.iterator(it)
        leftTree?.iterator(it)
    }


    fun prefix(it : (Tree<T>, Int, Int, Int) -> Unit, depth : Int = 0, index : Int = 0, span : Int = getChildCount()){
        rightTree?.prefix(it,depth + 1,2*index,getChildCount())
        leftTree?.prefix(it,depth + 1,2*index+1,getChildCount())
        it(this,depth,index,span)
    }

    fun getChildCount() : Int {
        var span = 0
        rightTree?.apply { span++ }
        leftTree?.apply { span++ }
        return span
    }

}