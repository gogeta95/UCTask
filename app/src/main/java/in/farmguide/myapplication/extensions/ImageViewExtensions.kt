package `in`.farmguide.myapplication.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(path: String?) {
    if (!path.isNullOrEmpty())
        Glide.with(context).load(path).into(this)
    else
        setImageDrawable(null)
}