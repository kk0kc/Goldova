package com.example.fintechfilms.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.example.fintechfilms.databinding.ItemFilmBinding
import com.example.fintechfilms.db.entity.Favourite
import com.example.fintechfilms.response.top.Film

class FilmListAdapter(
    private val action : (Film) -> Unit,
    private val glide: RequestManager,
    private val actionFav: (Film) -> Boolean,
    private val filmFav: List<Favourite>?
) : ListAdapter<Film, FilmItem>(object : DiffUtil.ItemCallback<Film>()
{
    override fun areItemsTheSame(
        oldItem: Film,
        newItem: Film
    ): Boolean = oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(
        oldItem: Film,
        newItem: Film
    ): Boolean = oldItem == newItem
}) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmItem = FilmItem(
        ItemFilmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide,
        action,
        actionFav,
        filmFav
    )

    override fun onBindViewHolder(
        holder: FilmItem,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun submitList(list: List<Film>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}
