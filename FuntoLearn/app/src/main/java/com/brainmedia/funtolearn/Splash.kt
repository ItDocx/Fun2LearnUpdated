package com.brainmedia.fun2learn

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {

    private lateinit var splashview: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(1)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_splash)


        splashview = findViewById(R.id.VideoSplash)
        val path = "android.resource://com.brainmedia.fun2learn/" + R.raw.splash_f2l

        val uri = Uri.parse(path)
        splashview.setVideoURI(uri)
        splashview.setOnPreparedListener(OnPreparedListener { mediaPlayer -> mediaPlayer.start() })

        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 6000)


    }
}