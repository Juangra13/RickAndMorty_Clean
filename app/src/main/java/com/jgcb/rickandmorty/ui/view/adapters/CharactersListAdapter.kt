package com.jgcb.rickandmorty.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jgcb.rickandmorty.data.model.Character
import com.jgcb.rickandmorty.databinding.ItemCharacterListBinding

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

class CharactersListAdapter(private val listener: ItemListener) : RecyclerView.Adapter<CharacterViewHolder>() {

    private val items = ArrayList<Character>()

    fun setItems(items: ArrayList<Character>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    interface ItemListener {
        fun onClickItem(characterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterListBinding =
            ItemCharacterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        return holder.bind(items[position])
    }
}

class CharacterViewHolder(
        private val itemBinding: ItemCharacterListBinding,
        private val listener: CharactersListAdapter.ItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

    init {
        itemBinding.root.setOnClickListener(this)
    }

    private lateinit var character: Character

    fun bind(item: Character) {
        this.character = item
        itemBinding.tvName.text = item.name
        itemBinding.tvSpecies.text = item.species
        itemBinding.tvStatus.text = item.status
        Glide.with(itemBinding.root)
            .load(item.image)
            .into(itemBinding.ivImageMain)
    }

    override fun onClick(p0: View?) {
        listener.onClickItem(characterId = character.id)
    }
}