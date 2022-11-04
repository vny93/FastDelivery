package vn.vunganyen.fastdelivery.data.model.classSupport

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface


class StartAlertDialog {
    var clickOk: (()->Unit)?=null
    fun showStartDialog1(str : String,context: Context){
        AlertDialog.Builder(context)
            .setTitle("")
            .setMessage(str)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })
            .create().show()
    }
    fun showStartDialog2(str : String,context: Context){
        AlertDialog.Builder(context)
            .setTitle("")
            .setMessage(str)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> clickOk?.invoke()
            })
            .create().show()
    }
    fun showStartDialog3(str : String,context: Context){
        AlertDialog.Builder(context)
            .setTitle("")
            .setMessage(str)
            .setCancelable(false)
            .setPositiveButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })
            .create().show()
    }

    fun showStartDialog4(str : String,context: Context){
        AlertDialog.Builder(context)
            .setTitle("")
            .setMessage(str)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> clickOk?.invoke()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
            .create().show()
    }

//    fun showStartDialog4(str : String,context: Context){
//        var alertDialog : AlertDialog.Builder = AlertDialog.Builder(context)
//        alertDialog.setTitle("")
//        alertDialog.setMessage(str)
//        alertDialog.setCancelable(false)
//        alertDialog.setPositiveButton("Cancel", DialogInterface.OnClickListener {
//                dialog, id -> dialog.dismiss()
//        }).create().show()
//        val dialog: AlertDialog = alertDialog.create()
//        dialog.show()
//        val btnPositive: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
//        layoutParams.weight = 50f
//        btnPositive.layoutParams = layoutParams
//        val messageView = dialog.findViewById<View>(R.id.message) as TextView
//        messageView.gravity = Gravity.CENTER
//    }
}