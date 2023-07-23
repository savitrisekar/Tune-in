package com.savitrisekar.tuneinapp.domain.mapper

import com.savitrisekar.tuneinapp.domain.data.model.response.MusicPlaylistResponse
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist

fun MusicPlaylistResponse.toMusicPlaylist(): MusicPlaylist {
    return MusicPlaylist(
        id = trackId.orEmpty(),
        artworkUrl100 = artworkUrl100.orEmpty(),
        trackName = trackName.orEmpty(),
        artistName = artistName.orEmpty(),
        previewUrl = previewUrl.orEmpty()
    )
}