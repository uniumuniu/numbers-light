package com.example.numberslight.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.numberslight.R
import com.example.numberslight.domain.model.NumberModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NumbersListFragment : Fragment(R.layout.fragment_numbers_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: CustomAdapter
    private val viewModel: NumbersListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.listOfItemsRecyclerView)

        customAdapter = CustomAdapter(requireActivity(), emptyList<NumberModel>(), viewModel)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.numbers.observe(viewLifecycleOwner) {
            customAdapter.setData(it)
        }
    }
}