package com.sdv.presentation.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.sdv.presentation.R


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: Dialog

    abstract fun layout(): Int
    abstract fun initialization()
    abstract fun showSnackBar()
    private var hasInternet: Boolean = true
    var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initProgressDialog()

        if (layout() != 0) {
            setContentView(layout())
            initialization()
            //  setTransparentStatusBar(isTransparent = true, isDarkTexts = false)
        }
    }

    fun hideKeyboard() {
        val it = currentFocus ?: window?.decorView
        val imm: InputMethodManager? =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(it?.windowToken, 0)
        it?.clearFocus()
    }

    private fun initProgressDialog() {
        progressDialog = Dialog(this, R.style.AppTheme)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
       // progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        progressDialog.setOnCancelListener {
            onBackPressed()
        }
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val winParams: WindowManager.LayoutParams = window.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        window.attributes = winParams
    }

}