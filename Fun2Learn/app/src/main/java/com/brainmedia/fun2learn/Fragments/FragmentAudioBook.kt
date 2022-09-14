package com.brainmedia.fun2learn.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainmedia.fun2learn.Adapters.AudioAdapter
import com.brainmedia.fun2learn.Models.AudioModels
import com.brainmedia.fun2learn.R
import com.google.firebase.database.*


class FragmentAudioBook : Fragment() {

    lateinit var recycler:RecyclerView
    private lateinit var StoryRef: DatabaseReference
    lateinit var audioList: ArrayList<AudioModels>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_audio_book, container, false)
        StoryRef = FirebaseDatabase.getInstance().reference.child("Songs")

        recycler = v.findViewById(R.id.audioRecycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)

        audioList = arrayListOf<AudioModels>()
        getStoryData()

        return v
    }

    private fun getStoryData() {
        StoryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (usersnapshot in snapshot.children){

                        val audiodata = usersnapshot.getValue(AudioModels::class.java)
                        audioList.add(audiodata!!)

                    }

                    recycler.adapter= AudioAdapter(audioList = audioList, context = requireContext())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

}