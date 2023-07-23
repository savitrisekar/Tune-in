package com.savitrisekar.tunein.domain.model

data class MusicPlaylist(
    val id: String = "",
    val artworkUrl100: String = "",
    val trackName: String = "",
    val artistName: String = "",
    val isPlay: Boolean = false,
    val playSetUp: PlaySetUp = PlaySetUp.HIDE,
    val previewUrl: String = ""
) {
    enum class PlaySetUp {
        PLAY,
        PAUSE,
        HIDE
    }
}
