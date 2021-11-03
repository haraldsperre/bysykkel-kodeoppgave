package com.example.cyclebikes.locator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cyclebikes.R
import com.example.cyclebikes.databinding.FragmentLocatorBinding
import com.example.cyclebikes.model.Result

class LocatorFragment : Fragment() {

    private val viewModel: LocatorViewModel by lazy {
        ViewModelProvider(this, LocatorViewModelFactory())
            .get(LocatorViewModel::class.java)
    }

    private lateinit var binding: FragmentLocatorBinding
    private lateinit var adapter: LocatorRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocatorBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = LocatorRecyclerViewAdapter()
        binding.locatorRecycler.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Observe the state (Loading, Error, Success) to show
         * loading indicator, error message or success indication
         */
        viewModel.state.observe(viewLifecycleOwner, { result ->
            when(result.status) {
                Result.Status.SUCCESS -> {
                    binding.locatorFragmentProgress.visibility = View.GONE
                    binding.buttonRefresh.isEnabled = true
                    Toast.makeText(activity, getString(R.string.data_retrieved), Toast.LENGTH_SHORT)
                }
                Result.Status.ERROR -> {
                    binding.locatorFragmentProgress.visibility = View.GONE
                    binding.buttonRefresh.isEnabled = true
                    result.message?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                    }
                    result.messageString?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                    }
                }
                Result.Status.LOADING -> {
                    binding.locatorFragmentProgress.visibility = View.VISIBLE
                    binding.buttonRefresh.isEnabled = false
                }
            }
        })

        /**
         * Observe the Station List in the View model, and pass it
         * to the adapter when it changes
         */
        viewModel.stationList.observe(viewLifecycleOwner, { stations ->
            adapter.submitList(stations)
        })

    }
}