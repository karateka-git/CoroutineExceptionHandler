package com.example.coroutineexceptionhandler

import kotlinx.coroutines.*
import java.io.IOException

class MainPresenter {
    private var view: MainView? = null
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            view?.appendMessageToTextView(throwable.message ?: "")
        }

    fun attachView(view: MainView) {
        this.view = view
    }

    fun onButtonClick() {
        coroutineWithExceptionWithCoroutineScope()
    }

    fun detachView() {
        this.view = null
    }

    private fun simpleCoroutineExample() {
        GlobalScope.launch(Dispatchers.Main) {
            view?.appendMessageToTextView("Simple coroutine example")
        }
    }

    private fun coroutineWithException() {
        view?.appendMessageToTextView("coroutineWithException start")
        GlobalScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            view?.appendMessageToTextView("GlobalScope start")
            // not cancel
            launch {
                delay(200)
                view?.appendMessageToTextView("from first launch")
            }
            // cancel with exception
            launch {
                delay(100)
                throw IOException("exception from second launch")
            }
            view?.appendMessageToTextView("GlobalScope end")
        }
        view?.appendMessageToTextView("coroutineWithException end")
    }

    private fun coroutineWithExceptionWithCoroutineScope() {
        view?.appendMessageToTextView("coroutineWithException start")
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + coroutineExceptionHandler)

        GlobalScope.launch(Dispatchers.Main) {
            view?.appendMessageToTextView("GlobalScope start")
            // not cancel
            scope.launch {
                delay(200)
                view?.appendMessageToTextView("from first launch")
            }
            // cancel with exception
            scope.launch {
                delay(100)
                throw IOException("exception from second launch")
            }
            view?.appendMessageToTextView("GlobalScope end")
        }
        view?.appendMessageToTextView("coroutineWithException end")
    }

    private fun coroutineWithExceptionWithSupervisorScope() {
        view?.appendMessageToTextView("coroutineWithException start")
        GlobalScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            view?.appendMessageToTextView("GlobalScope start")
            // just create scope with job = SupervisorJob
            supervisorScope {
                // not cancel
                launch {
                    delay(200)
                    view?.appendMessageToTextView("from first launch")
                }
                // cancel with exception
                launch {
                    delay(100)
                    throw IOException("exception from second launch")
                }
            }
            view?.appendMessageToTextView("GlobalScope end")
        }
        view?.appendMessageToTextView("coroutineWithException end")
    }
}
