package com.jgcb.rickandmorty.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.databinding.CharacterDetailFragmentBinding
import com.jgcb.rickandmorty.ui.viewmodels.CharactersDetailViewModel
import com.jgcb.rickandmorty.utils.ContentConstants
import com.jgcb.rickandmorty.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by @Juan Gabriel Corrales on 22/07/2023.
 */

@AndroidEntryPoint
class CharactersDetailFragment : Fragment() {

    private lateinit var binding: CharacterDetailFragmentBinding
    private val viewModel: CharactersDetailViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ContentConstants.VALUE_ID)?.let {
            viewModel.start(it)
        }
        // Init observers
        initObservers()
    }

    private fun initObservers() {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data -> bindCharacter(data) }
                    binding.progressBar.visibility = View.GONE
                    binding.clCharacterMain.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Snackbar.make(binding.clCharacterMain, it.message.toString(), 5000).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.clCharacterMain.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(character: Character) {
        Glide.with(binding.root)
            .load(character.image)
            .into(binding.ivImageCharacter)
        binding.tvName.text = character.name
        binding.tvSpeciesDescription.text = character.species
        binding.tvStatusDescription.text = character.status
        binding.tvGenderDescription.text = character.gender
    }
}