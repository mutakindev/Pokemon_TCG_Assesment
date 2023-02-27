package com.mutakinsinau.pokemontcg.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mutakinsinau.pokemontcg.data.network.dto.Pokemon
import com.mutakinsinau.pokemontcg.databinding.PokemonItemBinding

class PokemonAdapter : ListAdapter<Pokemon, PokemonAdapter.ViewHolder>(diffUtil), Filterable {
    private val _listPokemon: MutableList<Pokemon> = mutableListOf()

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Pokemon>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(_listPokemon)
            } else {
                filteredList.addAll(_listPokemon.filter {
                    it.name.contains(
                        constraint,
                        ignoreCase = true
                    )
                })
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            @Suppress("UNCHECKED_CAST")
            submitList(results?.values as MutableList<Pokemon>)
        }
    }

    fun setData(listPokemon: List<Pokemon>) {
        _listPokemon.addAll(listPokemon)
        submitList(listPokemon)
    }

    inner class ViewHolder(private val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.pokemonKey, pokemon)
                it.context.startActivity(intent)
            }

            with(binding) {
                tvName.text = pokemon.name
                Glide.with(itemView.context)
                    .load(pokemon.images.small)
                    .into(ivSmallImage)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Pokemon,
                newItem: Pokemon
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getFilter(): Filter {
        return customFilter
    }
}