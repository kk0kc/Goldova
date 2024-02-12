package com.example.fintechfilms.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechfilms.Container
import com.example.fintechfilms.R
import com.example.fintechfilms.adapter.FilmListAdapter
import com.example.fintechfilms.databinding.FragmentListBinding
import com.example.fintechfilms.db.FavouriteRepository
import com.example.fintechfilms.db.entity.Favourite
import com.example.fintechfilms.db.entity.toFilm
import androidx.appcompat.widget.SearchView
import com.bumptech.glide.Glide

import com.example.fintechfilms.response.detail.toFavourite
import com.example.fintechfilms.response.top.Film
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator
import kotlinx.coroutines.launch

class ListFragment : Fragment(R.layout.fragment_list) {

    private var binding : FragmentListBinding? = null
    private var adapter : FilmListAdapter? = null
    private val api = Container.filmApi
    internal var filmRep: List<Film>? = null
    private var repository: FavouriteRepository? = null
    private var filmFav: List<Favourite>? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentListBinding.bind(view)
        repository = FavouriteRepository(requireContext())
        lifecycleScope.launch {
            filmFav = repository?.getAllFavourite()
        }
        getFilmsFromApi()
        super.onViewCreated(view, savedInstanceState)
    }

    internal fun getFilmsFromApi(){
        lifecycleScope.launch {
            try {
                (activity as MainActivity).supportActionBar?.title = getString(R.string.btn_top)
                filmFav = repository?.getAllFavourite()
                filmRep = api.getFilms().films
                onSearch(filmRep)
                binding?.run {
                    progressBar?.visibility = View.GONE
                    btnTop.visibility = View.VISIBLE
                    btnFav.visibility = View.VISIBLE
                    btnTop.setOnClickListener {
                        getFilmsFromApi()
                    }
                    btnFav.setOnClickListener {
                        toFavouriteList()
                        onSearch(filmFav?.map{ it.toFilm() })
                    }
                }
                insertFilmFromApi(filmRep)
            } catch (e: Exception) {
                binding?.run {
                    progressBar?.visibility = View.GONE
                }
                filmFav = repository?.getAllFavourite()
                if (arguments?.getString("message") == "error") {
                    showNetworkError()
                } else {
                    toFavouriteList()
                    onSearch(filmFav?.map{ it.toFilm() })
                    binding?.run {
                        btnTop.visibility = View.VISIBLE
                        btnFav.visibility = View.VISIBLE
                    }
                }
                binding?.btnTop?.setOnClickListener {
                    showNetworkError()
                }
            }
        }
    }

    private fun insertFilmFromApi(films: List<Film>?) {
        onCreateAdapter()
        adapter?.submitList(films)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        repository = null
    }


    private fun showNetworkError() {
        binding?.run{
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, ErrorFragment.newInstance("list"))
                .commit()
        }
    }

    private fun addToFavourite(film: Film) : Boolean {
        lifecycleScope.launch {
            try {
                val filmDetail = api.getDescribtion(film.filmId)
                if (repository?.getFavouriteById(filmDetail.kinopoiskId) == null) {
                    repository?.saveFavourite(filmDetail.toFavourite())
                } else {
                    repository?.deleteFavourite(filmDetail.toFavourite())
                }
                filmFav = repository?.getAllFavourite()
            } catch (e: Exception) {
                showNetworkError()
            }
        }
        return true
    }

    private fun toFavouriteList() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.btn_fav)
        onCreateAdapter()
        adapter?.submitList(filmFav?.map { it.toFilm() })
    }

    private fun onCreateAdapter() {
        binding?.run {
            adapter = FilmListAdapter(
                action = {
                    navigateToDetail(it)
                },
                actionFav = {
                    addToFavourite(it)
                },
                filmFav = filmFav,
                glide = Glide.with(this@ListFragment)
            )
            rvFilm.adapter = adapter
            rvFilm.layoutManager = LinearLayoutManager(context)
            rvFilm.adapter = ScaleInAnimationAdapter(adapter!!)
            rvFilm.itemAnimator = OvershootInLeftAnimator()
        }
    }

    private fun onSearch(list: List<Film>?){
        binding?.search?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val currentFilm = list?.find { film -> query == film.nameRu }
                    if (currentFilm != null) {
                       navigateToDetail(currentFilm)
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun navigateToDetail(film: Film) {
        try {
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, DetailFragment.newInstance(film.filmId))
                        .addToBackStack("ListFragment")
                        .commit()
                }
                Configuration.ORIENTATION_LANDSCAPE -> {
                    parentFragmentManager.beginTransaction()
                        .add(R.id.container, DetailFragment.newInstance(film.filmId))
                        .addToBackStack("ListFragment")
                        .commit()
                }
                else -> {}
            }
        } catch (e: Exception) {
            showNetworkError()
        }
    }
    companion object {
        fun newInstance(message: String) : ListFragment = ListFragment().apply {
            arguments = Bundle().apply {
                putString("message", message)
            }
        }
    }
}
