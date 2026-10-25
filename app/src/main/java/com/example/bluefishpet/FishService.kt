package com.example.bluefishpet

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import kotlin.math.abs

class FishService : Service() {

    private lateinit var wm: WindowManager
    private lateinit var fishView: FishView
    private lateinit var params: WindowManager.LayoutParams
    private val handler = Handler(Looper.getMainLooper())
    private var running = false
    private var vx = 4f
    private var vy = 3f

    private val moveRunnable = object : Runnable {
        override fun run() {
            if (!running) return
            val dm = resources.displayMetrics
            params.x += vx.toInt()
            params.y += vy.toInt()
            if (params.x < 0) { params.x = 0; vx = abs(vx) }
            if (params.y < 0) { params.y = 0; vy = abs(vy) }
            if (params.x + params.width > dm.widthPixels) {
                params.x = dm.widthPixels - params.width; vx = -abs(vx)
            }
            if (params.y + params.height > dm.heightPixels) {
                params.y = dm.heightPixels - params.height; vy = -abs(vy)
            }
            try { wm.updateViewLayout(fishView, params) } catch (_: Exception) {}
            handler.postDelayed(this, 30)
        }
    }

    override fun onCreate() {
        super.onCreate()
        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        fishView = FishView(this)

        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else
            WindowManager.LayoutParams.TYPE_PHONE

        params = WindowManager.LayoutParams(
            400, 320, type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 80
            y = 400
        }

        fishView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    fishView.dx = event.rawX - params.x
                    fishView.dy = event.rawY - params.y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = (event.rawX - fishView.dx).toInt()
                    params.y = (event.rawY - fishView.dy).toInt()
                    try { wm.updateViewLayout(fishView, params) } catch (_: Exception) {}
                    true
                }
                else -> false
            }
        }

        try { wm.addView(fishView, params) } catch (e: Exception) { e.printStackTrace() }
        running = true
        handler.post(moveRunnable)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "fish_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(channelId, "桌宠", NotificationManager.IMPORTANCE_LOW)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(ch)
        }
        val notif: Notification = Notification.Builder(this, channelId)
            .setContentTitle("蓝色大肥鱼桌宠运行中")
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .build()
        startForeground(1, notif)
        return START_STICKY
    }

    override fun onDestroy() {
        running = false
        handler.removeCallbacksAndMessages(null)
        try { wm.removeView(fishView) } catch (_: Exception) {}
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
