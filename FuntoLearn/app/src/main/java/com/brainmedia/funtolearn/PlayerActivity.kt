package com.brainmedia.fun2learn

import android.R
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.brainmedia.fun2learn.Dialogues.AboutUsDialogue
import com.brainmedia.fun2learn.Dialogues.RateUsDialogue
import com.brainmedia.fun2learn.Fragments.FragmentAudioBook
import com.brainmedia.fun2learn.databinding.ActivityPlayerBinding
import com.brainmedia.masterdownloader.Utils.NetworkChangeListener
import com.example.jean.jcplayer.model.JcAudio
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlin.system.exitProcess


class PlayerActivity : AppCompatActivity() {

    private lateinit var playertoggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityPlayerBinding
    private val networkchange: NetworkChangeListener = NetworkChangeListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO: Setting Up Navigation Drawer
        setSupportActionBar(binding.playerToolbar)
        playertoggle = ActionBarDrawerToggle(this@PlayerActivity,binding.playerDrawer,binding.playerToolbar,
            com.brainmedia.fun2learn.R.string.open,
            com.brainmedia.fun2learn.R.string.close)
        binding.playerDrawer.addDrawerListener(playertoggle)
        playertoggle.drawerArrowDrawable.color = getColor(com.brainmedia.fun2learn.R.color.border_color)
        playertoggle.syncState()


        dashBoardPlayerAds("https://fun2learn-31c71-default-rtdb.firebaseio.com/bannerPlayer")

            val url = intent.getStringExtra("url")
            val  songname = intent.getStringExtra("name")


        val jcAudios: ArrayList<JcAudio> = ArrayList()
        if (!jcAudios.isEmpty()) {
            jcAudios.add(
                JcAudio.createFromURL(
                    songname.toString(),
                    url = url.toString()
                )
            )
            binding.audioPlayer.initPlaylist(jcAudios, null)
            binding.audioPlayer.createNotification();
        }else{

            jcAudios.add(
                JcAudio.createFromURL(
                    title = "Story-1",
                    url = "https://firebasestorage.googleapis.com/v0/b/fun2learn-31c71.appspot.com/o/03%20Tum%20Saath%20Ho%20-%20Tamasha%20(Arijit%20Singh)%20320Kbps.mp3?alt=media&token=bc9d1b2b-71a4-449f-a890-f64d5c46fbf5"
                )
            )
            binding.audioPlayer.initPlaylist(jcAudios, null)
            binding.audioPlayer.createNotification();
        }

        // TODO: NavigationView Listener
        binding.navPlayer.setNavigationItemSelectedListener{
            when(it.itemId){

                com.brainmedia.fun2learn.R.id.mhome_btn->{
                    startActivity(Intent(this,MainActivity::class.java))
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                    finish()

                }

                com.brainmedia.fun2learn.R.id.mcounting_btn->{
                    startActivity(Intent(this@PlayerActivity,Counting::class.java))
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }

                com.brainmedia.fun2learn.R.id.malphabets_btn->{
                    startActivity(Intent(this@PlayerActivity,Alphabets::class.java))
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }

                com.brainmedia.fun2learn.R.id.mnumbers_btn->{
                    startActivity(Intent(this@PlayerActivity,Numbers::class.java))
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }

                com.brainmedia.fun2learn.R.id.mshapes_btn->{
                    startActivity(Intent(this@PlayerActivity,Shapes::class.java))
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }

                com.brainmedia.fun2learn.R.id.mdrawing_btn->{
                    Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show()
                }

                com.brainmedia.fun2learn.R.id.feedback_btn -> {
                    settingRateUsDialoge()
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }
//                R.id.settings_btn -> Toast.makeText(baseContext,"Settings", Toast.LENGTH_SHORT).show()

                com.brainmedia.fun2learn.R.id.maudio_btn->{
                    val fragment = FragmentAudioBook()
                    supportFragmentManager.beginTransaction().replace(com.brainmedia.fun2learn.R.id.layout_transaction,fragment).addToBackStack(null).commit()
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                    finish()
                }

                com.brainmedia.fun2learn.R.id.mplayer->{
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                    finish()
                }

                com.brainmedia.fun2learn.R.id.aboutUs_btn -> {
                    setAboutUsDialoge()
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }
                com.brainmedia.fun2learn.R.id.exit_btn -> {
                    val intent = Intent(this@PlayerActivity,MainActivity::class.java)
                    intent.putExtra("exit",false)
                    startActivity(intent)
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }
            }
            true
        }


    }


    fun settingRateUsDialoge() {
        val rate =
            RateUsDialogue(this@PlayerActivity)
        rate.getWindow()?.setBackgroundDrawable(
            ColorDrawable(
                resources
                    .getColor(R.color.white)
            )
        )
        rate.setCancelable(false)
        rate.show()
    }

    fun setAboutUsDialoge() {
        val dashBoardDialog = AboutUsDialogue(this@PlayerActivity)
        dashBoardDialog.setCancelable(false)
        dashBoardDialog.show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(playertoggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }
    override fun onStart() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkchange, intentFilter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkchange)
        super.onStop()
    }


    //TODO: Alphabets Banner Implement
    private fun dashBoardPlayerAds(playerBanner: String) {
        Firebase.setAndroidContext(this)
        val firebase = Firebase(playerBanner)
        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(String::class.java)
                val bannerAd = AdView(this@PlayerActivity)
                bannerAd.adUnitId = data
                binding.playerBanner.addView(bannerAd)
                bannerAd.setAdSize(AdSize.SMART_BANNER)
                val adRequest = AdRequest.Builder().build()
                bannerAd.loadAd(adRequest)
            }

            override fun onCancelled(firebaseError: FirebaseError) {}
        })
    }


}