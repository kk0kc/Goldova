package com.example.fintechfilms.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechfilms.R
import com.example.fintechfilms.adapter.FilmListAdapter
import com.example.fintechfilms.databinding.FragmentErrorBinding
import com.example.fintechfilms.db.FavouriteRepository
import com.example.fintechfilms.db.entity.Favourite
import com.example.fintechfilms.db.entity.toFilm

class ErrorFragment: Fragment(R.layout.fragment_error) {
    private var binding : FragmentErrorBinding? = null
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        binding = FragmentErrorBinding.bind(view)

        binding?.run {
            progressBar.visibility = View.GONE
            btError.setOnClickListener{
                when (arguments?.getString(ARG_FRAGMENT_ID)){
                    "detail" -> parentFragmentManager.beginTransaction()
                        .replace(R.id.container, DetailFragment())
                        .commit()
                    "list" -> parentFragmentManager.beginTransaction()
                        .replace(R.id.container, ListFragment.newInstance("error"))
                        .commit()
                }
            }
            btnFav.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance("ok"))
                    .commit()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        private const val ARG_FRAGMENT_ID = "id_fragment"
        fun newInstance(idFragment: String) : ErrorFragment = ErrorFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_FRAGMENT_ID, idFragment)
            }
        }

    }

}
