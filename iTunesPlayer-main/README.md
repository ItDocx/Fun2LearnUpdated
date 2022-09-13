# iTunesPlayer
<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
<!--
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
-->



<!-- PROJECT LOGO -->
<div align="center">
<!--   <a href="https://github.com/aditasha/iTunesPlayer">
  </a> -->

  <p align="center">
    iTunesPlayer is an Android based application where you can search your favorite music artists from the iTunes library. The app shows all the songs created by the artist and a preview of the song that can be played.
    <br /><br />
    <a href="https://github.com/aditasha/iTunesPlayer/issues">Report Bug</a>
    ·
    <a href="https://github.com/aditasha/iTunesPlayer/issues">Request Feature</a>
  </p>
</div>


<!-- ABOUT THE PROJECT -->
## About The Project
This project was created using Kotlin, utilizing RecylerView and Retrofit to show and collect data, Reactive Programming using Flow, state management for flow of data using Kotlin sealed class and Media Player for streaming song previews inside the app.

### Supported Devices
* Android devices from Android 12 (Api 32) to Android 5.0 (Api 21)

### Supported Features
* Artist song/music searches
* Song previews streaming with play/pause, previous/next and progress bar
* Song playing indicator
* Album art, artist name and album name shown on list

### Built With

* [Kotlin](https://kotlinlang.org/)
* [Android Studio](https://developer.android.com/studio)
* [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview) ||
For showing scrollable music list and layout reusability to improve performance on low end device
* [Retrofit](https://square.github.io/retrofit) ||
For REST API connection to iTunes API and music data collection
* [Glide](https://github.com/bumptech/glide) ||
For showing music album pictures on ImageView more efficiently with built in cache
* [Kotlin Flow](https://developer.android.com/kotlin/flow) ||
For streaming data from Retrofit class towards the activity/fragment class
* [Media Player](https://developer.android.com/reference/android/media/MediaPlayer) ||
For streaming and manipulating song previews url fetched from iTunes Api

## Instructions
1. Download and extract the .zip file of this repository
2. Open Android Studio and select the extracted folder
3. Wait for Android Studio to index and finishing build for the first time
4. Deploy to your selected device using ADB or Wireless Debugging
5. Enjoy your iTunes Player

<!-- ROADMAP -->
## TO-DO

- [ ] Unit Testing

<!-- CONTACT -->
## Contact

- Aditasha | aditasha9@gmail.com

Project Link: [https://github.com/aditasha/iTunesPlayer](https://github.com/aditasha/iTunesPlayer)
 
