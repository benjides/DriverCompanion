package com.exfume.drivercompanion

interface BaseView<T : BasePresenter> {

    fun setPresenter(presenter: T)

}