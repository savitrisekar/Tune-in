package com.savitrisekar.tuneinapp.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

interface BasePresenter<View> : BasePresenterContract {
    var view: View?

    override val scope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main)

    fun doAttachView(view: View) {
        this.view = view
    }

    fun doDetachView() {
        this.view = null
        scope.cancel()
    }
}