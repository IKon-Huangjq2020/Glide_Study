package com.hjq.glide.util

import android.content.res.Resources
import android.graphics.*
import androidx.annotation.IntDef
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.security.MessageDigest


/**
 * @Description: Glide圆角封装类
 * @Author: hjq
 * @CreateDate: 2020/12/2 14:20
 */
class GlideRoundTransform(dp: Int = 4, @RoundTransformType var type: Int = ROUND_TYPE_CIRCLE) :
    BitmapTransformation() {


    companion object {
        //圆
        const val ROUND_TYPE_CIRCLE = 0

        //顶部左右保留圆角
        const val ROUND_TYPE_TOP_LEFT_RIGHT = 1

        //顶部左保留圆角
        const val ROUND_TYPE_TOP_LEFT = 2

        //顶部右保留圆角
        const val ROUND_TYPE_TOP_RIGHT = 3

        //底部左右保留圆角
        const val ROUND_TYPE_BOTTOM_LEFT_RIGHT = 4

        //底部左保留圆角
        const val ROUND_TYPE_BOTTOM_LEFT = 5

        //底部右保留圆角
        const val ROUND_TYPE_BOTTOM_RIGHT = 6

        //左部上下保留圆角
        const val ROUND_TYPE_LEFT_TOP_BOTTOM = 7

        //左部上保留圆角
        const val ROUND_TYPE_LEFT_TOP = 8

        //左部下保留圆角
        const val ROUND_TYPE_LEFT_BOTTOM = 9

        //右部上下保留圆角
        const val ROUND_TYPE_RIGHT_TOP_BOTTOM = 10

        //右部上保留圆角
        const val ROUND_TYPE_RIGHT_TOP = 11

        //右部下保留圆角
        const val ROUND_TYPE_RIGHT_BOTTOM = 12

        //四个角都保留圆角
        const val ROUND_TYPE_ALL = 13
    }

    @IntDef(
        ROUND_TYPE_CIRCLE,
        ROUND_TYPE_TOP_LEFT_RIGHT,
        ROUND_TYPE_TOP_LEFT,
        ROUND_TYPE_TOP_RIGHT,
        ROUND_TYPE_BOTTOM_LEFT_RIGHT,
        ROUND_TYPE_BOTTOM_LEFT,
        ROUND_TYPE_BOTTOM_RIGHT,
        ROUND_TYPE_LEFT_TOP_BOTTOM,
        ROUND_TYPE_LEFT_TOP,
        ROUND_TYPE_LEFT_BOTTOM,
        ROUND_TYPE_RIGHT_TOP_BOTTOM,
        ROUND_TYPE_RIGHT_TOP,
        ROUND_TYPE_RIGHT_BOTTOM,
        ROUND_TYPE_ALL
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class RoundTransformType

    private var radius = 0f

    private val paint = Paint()

    init {
        radius = Resources.getSystem().displayMetrics.density * dp

        paint.isAntiAlias = true
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return roundCrop(pool, toTransform)!!
    }


    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null
        var result: Bitmap? =
            pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(
                source.width,
                source.height,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(result!!)
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        when (type) {
            ROUND_TYPE_CIRCLE -> {
                val size = Math.min(source.width, source.height)
                val r = size / 2f
                canvas.drawCircle(r, r, r, paint)
            }

            ROUND_TYPE_ALL -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
            }

            ROUND_TYPE_TOP_LEFT_RIGHT -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound = RectF(0f, radius, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound, paint)
            }

            ROUND_TYPE_TOP_LEFT -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, radius, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound, paint)

                val rectRound2 =
                    RectF(radius, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_TOP_RIGHT -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, radius, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound, paint)

                val rectRound2 =
                    RectF(0f, 0f, source.width.toFloat() - radius, source.height.toFloat())
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_BOTTOM_LEFT_RIGHT -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, 0f, source.width.toFloat(), source.height.toFloat() - radius)
                canvas.drawRect(rectRound, paint)
            }

            ROUND_TYPE_BOTTOM_LEFT -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, 0f, source.width.toFloat(), source.height.toFloat() - radius)
                canvas.drawRect(rectRound, paint)
                val rectRound2 =
                    RectF(radius, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_BOTTOM_RIGHT -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, 0f, source.width.toFloat(), source.height.toFloat() - radius)
                canvas.drawRect(rectRound, paint)
                val rectRound2 =
                    RectF(0f, 0f, source.width.toFloat() - radius, source.height.toFloat())
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_LEFT_TOP_BOTTOM -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)

                val rectRound =
                    RectF(radius, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound, paint)
            }

            ROUND_TYPE_LEFT_TOP -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)

                val rectRound =
                    RectF(radius, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound, paint)

                val rectRound2 =
                    RectF(0f, radius, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_LEFT_BOTTOM -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)

                val rectRound =
                    RectF(radius, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound, paint)

                val rectRound2 =
                    RectF(0f, 0f, source.width.toFloat(), source.height.toFloat() - radius)
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_RIGHT_TOP_BOTTOM -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, 0f, source.width.toFloat() - radius, source.height.toFloat())
                canvas.drawRect(rectRound, paint)
            }

            ROUND_TYPE_RIGHT_TOP -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, 0f, source.width.toFloat() - radius, source.height.toFloat())
                canvas.drawRect(rectRound, paint)

                val rectRound2 =
                    RectF(0f, radius, source.width.toFloat(), source.height.toFloat())
                canvas.drawRect(rectRound2, paint)
            }

            ROUND_TYPE_RIGHT_BOTTOM -> {
                val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
                canvas.drawRoundRect(rectF, radius, radius, paint)
                val rectRound =
                    RectF(0f, 0f, source.width.toFloat() - radius, source.height.toFloat())
                canvas.drawRect(rectRound, paint)

                val rectRound2 =
                    RectF(0f, 0f, source.width.toFloat(), source.height.toFloat() - radius)
                canvas.drawRect(rectRound2, paint)
            }
        }

        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        val idBytes =
            "com.hjq.glide.util.GlideRoundTransform${Math.random() * radius}".toByteArray(Key.CHARSET)
        messageDigest.update(idBytes)
    }


}