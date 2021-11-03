package com.example.cyclebikes.locator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cyclebikes.databinding.StationItemBinding
import com.example.cyclebikes.model.Station

class LocatorRecyclerViewAdapter :
    ListAdapter<Station, LocatorRecyclerViewAdapter.StationsHolder>(StationCallback) {

    inner class StationsHolder(
        private val binding: StationItemBinding ) :
            RecyclerView.ViewHolder(binding.root) {

                fun bind(station: Station) {
                    binding.station = station
                }
            }

    companion object StationCallback : DiffUtil.ItemCallback<Station>() {
        override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationsHolder {
        return StationsHolder(
            StationItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: StationsHolder, position: Int) {
        val station = getItem(position)
        holder.bind(station)
    }

}