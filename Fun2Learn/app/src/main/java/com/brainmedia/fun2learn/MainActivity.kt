package com.brainmedia.fun2learn

import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.brainmedia.fun2learn.Dialogues.AboutUsDialogue
import com.brainmedia.fun2learn.Dialogues.RateUsDialogue
import com.brainmedia.fun2learn.Fragments.FragmentAudioBook
import com.brainmedia.fun2learn.databinding.ActivityMainBinding
import com.brainmedia.masterdownloader.Utils.NetworkChangeListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var dashBoardInter: InterstitialAd? = null
    private var onBackPressed: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val networkchange: NetworkChangeListener = NetworkChangeListener()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getBooleanExtra("exit",false)){
            finish()
        }

        // TODO: Setting Up Navigation Drawer
            setSupportActionBar(binding.mainToolbar)
            toggle = ActionBarDrawerToggle(this@MainActivity,binding.mainDrawer,binding.mainToolbar,R.string.open,R.string.close)
            binding.mainDrawer.addDrawerListener(toggle)
            toggle.drawerArrowDrawable.color = getColor(R.color.border_color)
            toggle.syncState()

        // TODO: DashBoard Main Banner
        dashBoardAds("https://fun2learn-31c71-default-rtdb.firebaseio.com/mainBanner")


        //TODO: DashBoard Interstitials
        getCountingInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interCount")
        getAlphaInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interAlpha")
        getNumbInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interNumb")
        getShapesInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interShape")
        getAudiBookInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interAudio")
        getDrawingInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interDraw")


        //TODO: Adding Click Listeners on Card Views
        binding.layoutAudioBook.setOnClickListener{


            if (dashBoardInter != null) {

                dashBoardInter!!.show(this@MainActivity)
                dashBoardInter!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {

                        // Calling Audio Book Fragment
                        onBackPressed = true
                        val fragment = FragmentAudioBook()
                        supportFragmentManager.beginTransaction().
                        replace(R.id.layout_transaction,fragment).
                        addToBackStack(null).commit()

                        // Implement Audio Boook Interstitial Ad
                        getAudiBookInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interAudio")
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)

                        Toast.makeText(
                            this@MainActivity,
                            "Error: " + adError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.llAlphabets.setOnClickListener{

            if (dashBoardInter != null) {

                dashBoardInter!!.show(this@MainActivity)
                dashBoardInter!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {

                        // Calling Alphabets Activity
                        onBackPressed = true
                        startActivity(Intent(this@MainActivity,Alphabets::class.java))

                        // Implement Audio Boook Interstitial Ad
                        getAlphaInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interAlpha")
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)

                        Toast.makeText(
                            this@MainActivity,
                            "Error: " + adError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.llCountdown.setOnClickListener{
            if (dashBoardInter != null) {

                dashBoardInter!!.show(this@MainActivity)
                dashBoardInter!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {

                        // Calling CountDown Activity
                        onBackPressed = true
                        startActivity(Intent(this@MainActivity,Counting::class.java))

                        // Implement Count Down Interstitial Ad
                        getCountingInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interCount")
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)

                        Toast.makeText(
                            this@MainActivity,
                            "Error: " + adError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.llNumbers.setOnClickListener{

            if (dashBoardInter != null) {

                dashBoardInter!!.show(this@MainActivity)
                dashBoardInter!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {

                        // Calling Numbers Activity
                        onBackPressed = true
                        startActivity(Intent(this@MainActivity,Numbers::class.java))

                        // Implement Numbers Interstitial Ad
                        getNumbInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interNumb")
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)

                        Toast.makeText(
                            this@MainActivity,
                            "Error: " + adError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.llShapes.setOnClickListener{

            if (dashBoardInter != null) {

                dashBoardInter!!.show(this@MainActivity)
                dashBoardInter!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {

                        // Calling Shape Activity
                        startActivity(Intent(this@MainActivity,Shapes::class.java))

                        // Implement Shape Interstitial Ad
                        getShapesInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interShape")
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)

                        Toast.makeText(
                            this@MainActivity,
                            "Error: " + adError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.llDrawing.setOnClickListener{

            if (dashBoardInter != null) {

                dashBoardInter!!.show(this@MainActivity)
                dashBoardInter!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {

                        // Calling Drawing Activity
                        Toast.makeText(this@MainActivity,"Coming Soon",Toast.LENGTH_SHORT).show()

                        // Implement Drawing Interstitial Ad
                        getDrawingInter("https://fun2learn-31c71-default-rtdb.firebaseio.com/interDraw")
                        super.onAdDismissedFullScreenContent()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        super.onAdFailedToShowFullScreenContent(adError)

                        Toast.makeText(
                            this@MainActivity,
                            "Error: " + adError.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        // TODO: NavigationView Listener
        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId){

                R.id.mhome_btn->{
                    startActivity(Intent(this,MainActivity::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                    finish()

                }
                R.id.mcounting_btn->{
                    startActivity(Intent(this@MainActivity,Counting::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }

                R.id.malphabets_btn->{
                    startActivity(Intent(this@MainActivity,Alphabets::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }

                R.id.mnumbers_btn->{
                    startActivity(Intent(this@MainActivity,Numbers::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }

                R.id.mshapes_btn->{
                    startActivity(Intent(this@MainActivity,Shapes::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }

                R.id.mdrawing_btn->{
                    Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show()
                }

                R.id.feedback_btn -> {
                    settingRateUsDialoge()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }
//                R.id.settings_btn -> Toast.makeText(baseContext,"Settings", Toast.LENGTH_SHORT).show()

                R.id.maudio_btn->{
                    val fragment = FragmentAudioBook()
                    supportFragmentManager.beginTransaction().replace(R.id.layout_transaction,fragment).addToBackStack(null).commit()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }

                R.id.mplayer->{
                    startActivity(Intent(this@MainActivity,PlayerActivity::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                    finish()
                }

                R.id.aboutUs_btn -> {
                    setAboutUsDialoge()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }
                R.id.exit_btn ->
                {
                    exitProcess(1)
                }
            }
            true
        }

    }


    fun settingRateUsDialoge() {
        val rate =
            RateUsDialogue(this@MainActivity)
        rate.getWindow()?.setBackgroundDrawable(
            ColorDrawable(
                resources
                    .getColor(android.R.color.white)
            )
        )
        rate.setCancelable(false)
        rate.show()
    }

    fun setAboutUsDialoge() {
        val dashBoardDialog = AboutUsDialogue(this@MainActivity)
        dashBoardDialog.setCancelable(false)
        dashBoardDialog.show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if (onBackPressed){
            super.onBackPressed()
        }
        else{
            onBackPressed = true
            Toast.makeText(this, "Press Again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ onBackPressed = false }, 3000)
        }
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


    //TODO: Setting Banner Ads For Main Activity

    private fun dashBoardAds(bannerId: String) {
        Firebase.setAndroidContext(this)
        val firebase = Firebase(bannerId)
        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(String::class.java)
                val bannerAd = AdView(this@MainActivity)
                bannerAd.adUnitId = data
                binding.dashContainer.addView(bannerAd)
                bannerAd.setAdSize(AdSize.SMART_BANNER)
                val adRequest = AdRequest.Builder().build()
                bannerAd.loadAd(adRequest)
            }

            override fun onCancelled(firebaseError: FirebaseError) {}
        })
    }



    //TODO: Adding Interstitial Ad

    //TODO: Counting Interstitial
    private fun getCountingInter(Countingad_Id: String) {
        Firebase.setAndroidContext(this@MainActivity)
        val interFirebase = Firebase(Countingad_Id)
        interFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val CountingInterData = dataSnapshot.getValue(String::class.java)
                setCountingInterstetial(CountingInterData)
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + firebaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Set Counting Interstitial Ad
    private fun setCountingInterstetial(CountinginterstetialId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            CountinginterstetialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    dashBoardInter = null
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + loadAdError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    dashBoardInter = interstitialAd
                }
            })
    }

    //TODO: Alphabets Inter
    private fun getAlphaInter(Alphaad_Id: String) {
        Firebase.setAndroidContext(this@MainActivity)
        val interFirebase = Firebase(Alphaad_Id)
        interFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val alphaInterData = dataSnapshot.getValue(String::class.java)
                setAlphaInterstetial(alphaInterData)
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + firebaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Set Alphabets Interstitial Ad
    private fun setAlphaInterstetial(alphainterstetialId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            alphainterstetialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    dashBoardInter = null
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + loadAdError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    dashBoardInter = interstitialAd
                }
            })
    }

    //TODO: Numbers Inter
    private fun getNumbInter(numbad_Id: String) {
        Firebase.setAndroidContext(this@MainActivity)
        val interFirebase = Firebase( numbad_Id)
        interFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val numbInterData = dataSnapshot.getValue(String::class.java)
                setNumbInterstetial(numbInterData)
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + firebaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Set Numbers Interstitial Ad
    private fun setNumbInterstetial(setinterstetialId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            setinterstetialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    dashBoardInter = null
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + loadAdError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    dashBoardInter = interstitialAd
                }
            })
    }

    //TODO: Shapes Inter
    private fun getShapesInter(shapesad_Id: String) {
        Firebase.setAndroidContext(this@MainActivity)
        val interFirebase = Firebase(shapesad_Id)
        interFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val shapesInterData = dataSnapshot.getValue(String::class.java)
                setShapesInterstetial(shapesInterData)
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + firebaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Set Shapes Interstitial Ad
    private fun setShapesInterstetial(ShapesinterstetialId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            ShapesinterstetialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    dashBoardInter = null
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + loadAdError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    dashBoardInter = interstitialAd
                }
            })
    }

    //TODO: AudioBook Inter
    private fun getAudiBookInter(AudioBookad_Id: String) {
        Firebase.setAndroidContext(this@MainActivity)
        val interFirebase = Firebase(AudioBookad_Id)
        interFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val audiobokInterData = dataSnapshot.getValue(String::class.java)
                setAudioBookInterstetial(audiobokInterData)
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + firebaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Set Audio Interstitial Ad
    private fun setAudioBookInterstetial(audiointerstetialId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            audiointerstetialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    dashBoardInter = null
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + loadAdError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    dashBoardInter = interstitialAd
                }
            })
    }

    // TODO: Drawing Inter
    private fun getDrawingInter(drawingad_Id: String) {
        Firebase.setAndroidContext(this@MainActivity)
        val interFirebase = Firebase(drawingad_Id)
        interFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val DrawingInterData = dataSnapshot.getValue(String::class.java)
                setDrawingInterstetial(DrawingInterData)
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + firebaseError.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Set Drawing Interstitial Ad
    private fun setDrawingInterstetial(drawinginterstetialId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            drawinginterstetialId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    dashBoardInter = null
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + loadAdError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    dashBoardInter = interstitialAd
                }
            })
    }

}