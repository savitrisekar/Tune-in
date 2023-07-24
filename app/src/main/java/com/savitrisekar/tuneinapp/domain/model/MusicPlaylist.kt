package com.savitrisekar.tuneinapp.domain.model

data class MusicPlaylist(
    val id: String = "",
    val artworkUrl100: String = "",
    val trackName: String = "",
    val artistName: String = "",
    val isPlayingNow: Boolean = false,
    val playSetUp: PlaySetUp = PlaySetUp.PLAY,
    val previewUrl: String = ""
) {
    enum class PlaySetUp {
        PLAY,
        PAUSE,
        HIDE
    }
}
