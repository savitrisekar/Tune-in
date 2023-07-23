package com.savitrisekar.tunein.presentation

import com.savitrisekar.tunein.domain.base.BasePresenter
import com.savitrisekar.tunein.domain.model.MusicPlaylist

interface MusicPlaylistContract {
    interface View {
        fun initView()
        fun initPresenter()

        fun onLoadingFetchPlaylist(isLoading: Boolean)
        fun onEmptyFetchPlaylist()
        fun onErrorFetchPlaylist(errorMessage: String?)
        fun onSuccessFetchPlaylist(data: List<MusicPlaylist>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPlaylist(term: String)
    }
}