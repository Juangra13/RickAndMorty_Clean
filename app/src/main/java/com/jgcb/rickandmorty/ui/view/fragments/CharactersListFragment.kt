package com.jgcb.rickandmorty.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jgcb.rickandmorty.R
import com.jgcb.rickandmorty.databinding.FragmentCharacterListBinding
import com.jgcb.rickandmorty.ui.view.adapters.CharactersListAdapter
import com.jgcb.rickandmorty.ui.viewmodels.CharactersListViewModel
import com.jgcb.rickandmorty.utils.ContentConstants
import com.jgcb.rickandmorty.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

@AndroidEntryPoint
class CharactersListFragment : Fragment(), CharactersListAdapter.ItemListener {

    private lateinit var binding: FragmentCharacterListBinding
    private val viewModel: CharactersListViewModel by viewModels()
    private lateinit var adapter: CharactersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentCharacterListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init recyclerView
        adapter = CharactersListAdapter(this)
        binding.rvCharacters.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCharacters.adapter = adapter

        //Init observers
        initObservers()
    }

    private fun initObservers() {
        viewModel.characters.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }

                Resource.Status.ERROR ->
                    Snackbar.make(binding.rvCharacters, it.message.toString(), 5000).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onClickItem(characterId: Int) {
        findNavController().navigate(
            R.id.action_listFragment_to_detailsFragment,
            bundleOf(ContentConstants.VALUE_ID to characterId)
        )
    }


}