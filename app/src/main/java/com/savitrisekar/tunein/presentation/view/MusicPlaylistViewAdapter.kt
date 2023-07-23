package com.savitrisekar.tunein.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.savitrisekar.tunein.R
import com.savitrisekar.tunein.databinding.ItemMusicPlaylistBinding

class MusicPlaylistViewAdapter(private val onItemPlaylistClick: (MusicPlaylistViewData) -> Unit) :
    RecyclerView.Adapter<MusicPlaylistViewAdapter.ViewHolder>() {

    private var items: MutableList<MusicPlaylistViewData> = mutableListOf()

    fun getItems() = items

    fun setItems(items: List<MusicPlaylistViewData>) {
        clearItems()
        addItems(items)
        this.notifyDataSetChanged()
    }

    fun addItems(items: List<MusicPlaylistViewData>) {
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
        this.notifyItemRemoved(position)
    }

    fun clearItems() {
        this.items.clear()
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemMusicPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemPlaylistClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class ViewHolder(
        private val binding: ItemMusicPlaylistBinding,
        private val onItemPlaylistClick: (MusicPlaylistViewData) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: MusicPlaylistViewData) {
            with(binding) {

                itemPlaylistMusicPicture.load(item.picture)

                itemPlaylistMusicSong.apply {
                    text = item.title
                    when (item.playSetUp) {
                        MusicPlaylistViewData.PlaySetUp.PLAY -> {
                            setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_equalizer_16,
                                0, 0, 0
                            )
                            setTextColor(ContextCompat.getColor(context, R.color.pink_FF5284))
                        }

                        MusicPlaylistViewData.PlaySetUp.PAUSE -> {
                            setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_stop_music,
                                0, 0, 0
                            )
                            setTextColor(ContextCompat.getColor(context, R.color.pink_FF5284))
                        }

                        else -> {
                            setTextColor(ContextCompat.getColor(context, R.color.white))
                        }
                    }
                }

                itemPlaylistMusicArtist.text = item.artist

                root.setOnClickListener {
                    onItemPlaylistClick.invoke(item)
                }
            }
        }
    }
}