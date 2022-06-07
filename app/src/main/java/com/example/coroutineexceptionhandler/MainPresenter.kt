package com.example.coroutineexceptionhandler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter {
    private var view: MainView? = null

    fun attachView(view: MainView) {
        this.view = view
    }

    fun onButtonClick() {
        simpleCoroutineExample()
    }

    fun detachView() {
        this.view = null
    }

    private fun simpleCoroutineExample() {
        GlobalScope.launch(Dispatchers.Main) {
            view?.appendMessageToTextView("Simple coroutine example")
        }
    }
}
