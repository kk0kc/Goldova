package com.example.fintechfilms.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.fintechfilms.databinding.ItemFilmBinding
import com.example.fintechfilms.db.entity.Favourite
import com.example.fintechfilms.response.top.Film

class FilmItem(
    private val binding: ItemFilmBinding,
    private val glide: RequestManager,
    private val action : (Film) -> Unit,
    private val actionFav: (Film) -> Boolean,
    private val filmFav: List<Favourite>?
) : RecyclerView.ViewHolder(binding.root) {
    private var option = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
    fun onBind(film: Film) {
        with(binding) {
            tvTitle.text = film.nameRu
            tvYear.text = film.year
            glide
                .load(film.posterUrl)
                .apply(option)
                .into(ivIcon)
            filmFav?.forEach {
                if (it.idFilm == film.filmId){
                    ivFav.visibility = View.VISIBLE
                }
            }
            root.setOnClickListener{
                action(film)
            }
            root.setOnLongClickListener {
                ivFav.visibility = View.VISIBLE
                actionFav(film)
            }
        }
    }
}
