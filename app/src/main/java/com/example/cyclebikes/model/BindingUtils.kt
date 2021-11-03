package com.example.cyclebikes.model

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.cyclebikes.R

@BindingAdapter("availableBikes")
fun TextView.setAvailableBikes(item: Station?) {
    item?.let {
        text = context.resources.getQuantityString(R.plurals.available_bikes, item.numBikes, item.numBikes)
    }
}

@BindingAdapter("availableRacks")
fun TextView.setAvailableRacks(item: Station?) {
    item?.let {
        text = context.resources.getQuantityString(R.plurals.available_docks, item.numDocks, item.numDocks)
    }
}