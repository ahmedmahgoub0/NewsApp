package com.example.newsapp.ui.home.newsFragment

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.example.newsapp.ui.ViewError

fun Fragment.showMessage(
    message: String,
    posActionName: String? = null,
    posAction: DialogInterface.OnClickListener? = null,
    negActionName: String? = null,
    negAction: DialogInterface.OnClickListener? = null
    ): AlertDialog{

    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setMessage(message)
    if(posActionName!=null)
        dialogBuilder.setPositiveButton(posActionName, posAction)
    if(negActionName!=null)
        dialogBuilder.setNegativeButton(negActionName, negAction)
    return dialogBuilder.show()
}
fun interface OnTryAgainClickListener{
    fun onTryAgainClick()
}
fun Fragment.handleError(viewError: ViewError){
    showMessage(message = viewError.message?:"something went wrong",
        posActionName = "try again",
        posAction = {dialogInterface, i ->
            dialogInterface.dismiss()
            viewError.onTryAgainClickListener?.onTryAgainClick()
        },
        negActionName = "cancel",
        negAction = {dialogInterface, i ->
            dialogInterface.dismiss()
        }
    )
}