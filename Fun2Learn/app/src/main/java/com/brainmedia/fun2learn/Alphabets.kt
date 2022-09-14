package com.brainmedia.fun2learn

import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.brainmedia.fun2learn.databinding.ActivityAlphabetsBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.mikhaellopez.circularimageview.CircularImageView

class Alphabets : AppCompatActivity() {

    private lateinit var binding: ActivityAlphabetsBinding
    private lateinit var playerView:PlayerView
    private lateinit var exoPlayer:SimpleExoPlayer
    private lateinit var mediaSource:MediaSource

    private lateinit var urlType:URLType

    companion object{

        val url ="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val Url2 ="https://rr1---sn-npoldn7s.googlevideo.com/videoplayback?expire=1663177667&ei=Y78hY-mOFMK98wSnjaHADQ&ip=52.146.70.115&id=o-ANJNORhiQhm8oDfSd_IwUWwUPaI-SqymCXE18QH65njq&itag=22&source=youtube&requiressl=yes&pcm2=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=126.734&lmt=1627553059836709&fexp=24001373,24007246&c=ANDROID&rbqsm=fr&txp=1532434&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cpcm2%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhAPI8REZ6mlM89cn_0MQewbEYkP6l0JUln86wa8UMSyj5AiEA56UXAuUgGBY2wEr2IS1xZOWRmRohw_vExf79oYsLkus%3D&redirect_counter=1&rm=sn-p5qe7e7e&req_id=45f56fad903ba3ee&cms_redirect=yes&cmsv=e&mh=ZW&mip=119.152.133.19&mm=34&mn=sn-npoldn7s&ms=ltu&mt=1663160447&mv=m&mvi=1&pl=19&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAMQS-tAww8Kr_2j7Jty1vdzyZjDRvlMPzDZ4772ZoFtcAiEAjYbNXnA9btyyKVe0NUZwL0rBbm6vkOkgBmzPO7ZMeEI%3D"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlphabetsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        playerView = findViewById(R.id.exoPlayerAlphabets)

        initPlayer()

    }

    private fun initPlayer() {
        exoPlayer = SimpleExoPlayer.Builder(this).build()
        exoPlayer.addListener(playerListener)

        playerView.player = exoPlayer

        createMediaSource()
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()

    }

    private fun createMediaSource() {
        urlType = URLType.MP4
        urlType.Url = url

        exoPlayer.seekTo(0)

        when(urlType){
            URLType.MP4->{

                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this,applicationInfo.name))

                mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(Uri.parse(urlType.Url))
                )

            }

            URLType.HSL->{
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this,
                    Util.getUserAgent(this,applicationInfo.name))

                mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(Uri.parse(urlType.Url))
                )

            }

        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val constraintSet = ConstraintSet()

        constraintSet.connect(playerView.id, ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0)
        constraintSet.connect(playerView.id, ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM,0)
        constraintSet.connect(playerView.id, ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,0)
        constraintSet.connect(playerView.id, ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END,0)

        constraintSet.applyTo(binding.root)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            hideSytemUi()
        }
        else{
            showSystemUi()
            val layoutParams = playerView.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.dimensionRatio = "16:9"
        }
        window.decorView.requestLayout()


    }


    private fun hideSytemUi(){

        actionBar?.hide()

        window.decorView.systemUiVisibility =(
        View.SYSTEM_UI_FLAG_IMMERSIVE
        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    private fun showSystemUi(){

        actionBar?.show()

        window.decorView.systemUiVisibility =(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


                )

    }

    override fun onDestroy() {
        super.onDestroy()

        exoPlayer.removeListener(playerListener)
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }


    private var playerListener = object: Player.Listener{
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()

            if (urlType == URLType.HSL){
                playerView.useController = false

            }
            if(urlType == URLType.MP4){

                playerView.useController = true

            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)

            Toast.makeText(this@Alphabets,"Error: "+error.message,Toast.LENGTH_SHORT).show()
        }


    }


}

enum class URLType(var Url:String) {

    MP4(""), HSL("")

}
