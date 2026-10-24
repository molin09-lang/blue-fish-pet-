package com.example.bluefishpet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.View

class FishView(context: Context) : View(context) {
    var dx = 0f
    var dy = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#1688E8")
        val tail = Path().apply {
            moveTo(110f, 160f)
            quadraticTo(30f, 90f, 20f, 150f)
            quadraticTo(30f, 220f, 110f, 180f)
            close()
        }
        canvas.drawPath(tail, paint)

        val finTop = Path().apply {
            moveTo(190f, 90f)
            quadraticTo(230f, 40f, 275f, 90f)
            quadraticTo(230f, 110f, 190f, 90f)
            close()
        }
        canvas.drawPath(finTop, paint)

        val finBottom = Path().apply {
            moveTo(190f, 215f)
            quadraticTo(230f, 265f, 280f, 215f)
            quadraticTo(230f, 200f, 190f, 215f)
            close()
        }
        canvas.drawPath(finBottom, paint)

        paint.color = Color.parseColor("#3AA9FF")
        canvas.drawOval(RectF(90f, 80f, 350f, 240f), paint)

        paint.color = Color.parseColor("#8BD4FF")
        canvas.drawOval(RectF(130f, 140f, 300f, 225f), paint)

        paint.color = Color.parseColor("#39A8F0")
        val side = Path().apply {
            moveTo(180f, 170f)
            quadraticTo(220f, 215f, 265f, 175f)
            quadraticTo(220f, 160f, 180f, 170f)
            close()
        }
        canvas.drawPath(side, paint)

        paint.color = Color.WHITE
        canvas.drawCircle(300f, 130f, 26f, paint)
        paint.color = Color.parseColor("#12395C")
        canvas.drawCircle(308f, 136f, 13f, paint)
        paint.color = Color.WHITE
        canvas.drawCircle(313f, 131f, 4f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 7f
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = Color.parseColor("#12395C")
        val mouth = Path().apply {
            moveTo(320f, 175f)
            quadraticTo(340f, 195f, 362f, 175f)
        }
        canvas.drawPath(mouth, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.argb(180, 255, 158, 199)
        canvas.drawOval(RectF(330f, 185f, 375f, 205f), paint)

        paint.color = Color.argb(150, 255, 255, 255)
        canvas.drawCircle(50f, 50f, 8f, paint)
        canvas.drawCircle(72f, 28f, 5f, paint)
        canvas.drawCircle(370f, 60f, 6f, paint)
    }
}
