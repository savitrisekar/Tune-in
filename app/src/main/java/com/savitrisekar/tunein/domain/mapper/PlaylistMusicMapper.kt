package com.savitrisekar.tunein.domain.mapper

import com.savitrisekar.tunein.domain.data.model.response.MusicPlaylistResponse
import com.savitrisekar.tunein.domain.model.MusicPlaylist

fun MusicPlaylistResponse.toMusicPlaylist(): MusicPlaylist {
    return MusicPlaylist(
        id = trackId.orEmpty(),
        artworkUrl100 = artworkUrl100.orEmpty(),
        trackName = trackName.orEmpty(),
        artistName = artistName.orEmpty(),
        previewUrl = previewUrl.orEmpty()
    )
}