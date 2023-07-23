package com.savitrisekar.tuneinapp.presentation

import com.savitrisekar.tuneinapp.domain.base.BasePresenter
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist

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