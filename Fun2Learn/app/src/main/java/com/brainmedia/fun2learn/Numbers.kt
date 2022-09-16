package com.brainmedia.fun2learn

import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.brainmedia.fun2learn.databinding.ActivityCountingBinding
import com.brainmedia.fun2learn.databinding.ActivityNumbersBinding
import com.brainmedia.masterdownloader.Utils.NetworkChangeListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.database.DatabaseReference

class Numbers : AppCompatActivity() {

    private lateinit var binding: ActivityNumbersBinding
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private val networkchange: NetworkChangeListener = NetworkChangeListener()

    private lateinit var urlType:URLTypeNumbers

    companion object{

        val url ="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val Url2 ="https://firebasestorage.googleapis.com/v0/b/fun2learn-31c71.appspot.com/o/Counting%2Fcount.mp4?alt=media&token=768829e6-667c-4a83-bc3d-4f307ae9a0c3"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumbersBinding.inflate(layoutInflater)
        val  view = binding.numbersRoot
        setContentView(view)
        //TODO: ActionBar Hide
        actionBar?.hide()

        //TODO: Screen Ratios
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        playerView = findViewById(R.id.exoPlayerNumber)

        // TODO: Initialize Player
        initPlayer()

        //TODO: Implement Banner
        dashBoardNumbAds("https://fun2learn-31c71-default-rtdb.firebaseio.com/bannerNumber")
    }

    //TODO: ExoPlayer Builder
    private fun initPlayer() {
        exoPlayer = SimpleExoPlayer.Builder(this).build()
        exoPlayer.addListener(playerListenernumbers)

        playerView.player = exoPlayer

        createMediaSource()
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()

    }

    //TODO: Fetching Media Source
    private fun createMediaSource() {

        urlType = URLTypeNumbers.MP4
        urlType.Url = url

        exoPlayer.seekTo(0)

        when(urlType){
            URLTypeNumbers.MP4->{

                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this,
                    Util.getUserAgent(this,applicationInfo.name))

                mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(Uri.parse(urlType.Url))
                )

            }

            URLTypeNumbers.HSL->{
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

        constraintSet.connect(playerView.id, ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,0)
        constraintSet.connect(playerView.id, ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,0)
        constraintSet.connect(playerView.id, ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,0)
        constraintSet.connect(playerView.id, ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,0)

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

    // TODO: Implement LifeCycles
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

        exoPlayer.removeListener(playerListenernumbers)
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
        unregisterReceiver(networkchange)
        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }


    private var playerListenernumbers = object: Player.Listener{
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()

            if (urlType == URLTypeNumbers.HSL){
                playerView.useController = false

            }
            if(urlType == URLTypeNumbers.MP4){

                playerView.useController = true

            }
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)

            Toast.makeText(this@Numbers,"Error: "+error.message, Toast.LENGTH_SHORT).show()
        }


    }
    override fun onStart() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkchange, intentFilter)
        super.onStart()
    }

    //TODO: Number Banner Implement
    private fun dashBoardNumbAds(Numbbanner: String) {
        Firebase.setAndroidContext(this)
        val firebase = Firebase(Numbbanner)
        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(String::class.java)
                val bannerAd = AdView(this@Numbers)
                bannerAd.adUnitId = data
                binding.numbBanner.addView(bannerAd)
                bannerAd.setAdSize(AdSize.SMART_BANNER)
                val adRequest = AdRequest.Builder().build()
                bannerAd.loadAd(adRequest)
            }

            override fun onCancelled(firebaseError: FirebaseError) {}
        })
    }


}

enum class URLTypeNumbers(var Url:String) {

    MP4(""), HSL("")

}