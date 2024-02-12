package com.example.fintechfilms.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.fintechfilms.Container
import com.example.fintechfilms.R
import com.example.fintechfilms.databinding.FragmentDetailBinding
import com.example.fintechfilms.db.FavouriteRepository
import com.example.fintechfilms.db.entity.toFilm
import com.example.fintechfilms.db.entity.toFilmDetailResponce
import com.example.fintechfilms.response.detail.FilmDetailResponse
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var binding : FragmentDetailBinding? = null
    private val api = Container.filmApi
    private var film: FilmDetailResponse? = null
    private var repository: FavouriteRepository? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentDetailBinding.bind(view)
        repository = FavouriteRepository(requireContext())

        val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        arguments?.getInt(ARG_FILM_ID)?.let { sharedPreferences?.edit()?.putInt("id", it)?.apply() }
        if (sharedPreferences?.contains("id") != true) {
            arguments?.getInt(ARG_FILM_ID)?.let { sharedPreferences?.edit()?.putInt("id", it)?.apply() }
            getDataFromApi(arguments?.getInt(ARG_FILM_ID))
        } else {
            getDataFromApi(sharedPreferences.getInt("id", 0))
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getDataFromApi(filmId: Int?) {
        binding?.run {
            lifecycleScope.launch {
                try {
                    if (filmId?.let { repository?.getFavouriteById(it) } != null) {
                        film = repository?.getFavouriteById(filmId)?.toFilmDetailResponce()
                    } else {
                        film = api.getDescribtion(filmId)
                    }
                    progressBar?.visibility = View.GONE
                    tvTitle.text = film?.nameRu
                    tvDescription.text = film?.description
                    tvCountry.text = getString(R.string.country,
                        film?.countries?.joinToString(getString(R.string.separator)) { it.country })
                    tvGenre.text = getString(R.string.genre,
                        film?.genres?.joinToString(getString(R.string.separator)) { it.genre })
                    ivIcon.load(film?.posterUrl)
                } catch (e: Exception) {
                    showNetworkError()
                }
            }
        }
    }

    private fun showNetworkError() {
        binding?.run{
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ErrorFragment.newInstance("detail"))
                .commit()
        }
    }

    companion object {
        private const val ARG_FILM_ID = "id"

        fun newInstance(id: Int): DetailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_FILM_ID, id)
            }
        }
    }
}
