package com.savitrisekar.tuneinapp.presentation.base

import kotlinx.coroutines.CoroutineScope

interface BasePresenterContract {
    val scope: CoroutineScope
}