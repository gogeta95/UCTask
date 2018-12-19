package `in`.farmguide.myapplication.extensions

import `in`.farmguide.myapplication.R
import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast

fun Activity.longToast(message: String?) {
    if (!message.isNullOrBlank())
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.showLoadingDialog(): ProgressDialog {
    val progressDialog = ProgressDialog(this)
    progressDialog.show()
    progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    progressDialog.setContentView(R.layout.progress_dialog)
    progressDialog.isIndeterminate = true
    progressDialog.setCancelable(false)
    progressDialog.setCanceledOnTouchOutside(false)
    return progressDialog
}