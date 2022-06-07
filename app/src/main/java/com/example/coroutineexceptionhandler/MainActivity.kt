package com.example.coroutineexceptionhandler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()
        initListeners()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun appendMessageToTextView(message: String) {
        simple_text_view.apply {
            append(message)
            append("\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun initPresenter() {
        presenter = MainPresenter().apply {
            attachView(this@MainActivity)
        }
    }

    private fun initListeners() {
        simple_button.setOnClickListener {
            presenter.onButtonClick()
        }
    }
}
