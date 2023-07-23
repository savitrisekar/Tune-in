package com.savitrisekar.tunein.presentation.view

data class MusicPlaylistViewData(
    val id: String = "",
    val picture: String = "",
    val title: String = "",
    val artist: String = "",
    val isPlay: Boolean = false,
    val playSetUp: PlaySetUp = PlaySetUp.HIDE
) {
    enum class PlaySetUp {
        PLAY,
        PAUSE,
        HIDE
    }
}
