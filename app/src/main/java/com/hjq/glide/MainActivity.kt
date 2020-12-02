package com.hjq.glide

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hjq.glide.util.GlideRoundTransform
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadImage(image_view, type = GlideRoundTransform.ROUND_TYPE_CIRCLE)
        loadImage(image_view2, type = GlideRoundTransform.ROUND_TYPE_ALL)

        loadImage(image_view3, type = GlideRoundTransform.ROUND_TYPE_TOP_LEFT_RIGHT)
        loadImage(image_view4, type = GlideRoundTransform.ROUND_TYPE_TOP_LEFT)
        loadImage(image_view5, type = GlideRoundTransform.ROUND_TYPE_TOP_RIGHT)

        loadImage(image_view6, type = GlideRoundTransform.ROUND_TYPE_BOTTOM_LEFT_RIGHT)
        loadImage(image_view7, type = GlideRoundTransform.ROUND_TYPE_BOTTOM_LEFT)
        loadImage(image_view8, type = GlideRoundTransform.ROUND_TYPE_BOTTOM_RIGHT)

        loadImage(image_view9, type = GlideRoundTransform.ROUND_TYPE_LEFT_TOP_BOTTOM)
        loadImage(image_view10, type = GlideRoundTransform.ROUND_TYPE_LEFT_TOP)
        loadImage(image_view11, type = GlideRoundTransform.ROUND_TYPE_LEFT_BOTTOM)

        loadImage(image_view12, type = GlideRoundTransform.ROUND_TYPE_RIGHT_TOP_BOTTOM)
        loadImage(image_view13, type = GlideRoundTransform.ROUND_TYPE_RIGHT_TOP)
        loadImage(image_view14, type = GlideRoundTransform.ROUND_TYPE_RIGHT_BOTTOM)

    }

    private fun loadImage(imageView: ImageView, type: Int) {
        Glide.with(this)
                .load(R.drawable.timg)
                .transform(GlideRoundTransform(20, type))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
    }
}