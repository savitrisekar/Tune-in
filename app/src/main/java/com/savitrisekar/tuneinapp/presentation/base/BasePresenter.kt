package com.savitrisekar.tuneinapp.presentation.base

interface BasePresenter<View> {
    var view: View?

    fun doAttachView(view: View) {
        this.view = view
    }

    fun doDetachView() {
        this.view = null
    }
}