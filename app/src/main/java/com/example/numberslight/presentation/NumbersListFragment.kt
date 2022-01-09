package com.example.numberslight.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.numberslight.NumbersApplication
import com.example.numberslight.R
import com.example.numberslight.domain.model.NumberModel


class NumbersListFragment : Fragment(R.layout.fragment_numbers_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: CustomAdapter

    private val viewModel: NumbersViewModel by activityViewModels {
        (requireActivity().applicationContext as NumbersApplication).appComponent.viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.listOfItemsRecyclerView)

        customAdapter = CustomAdapter(requireActivity(), emptyList<NumberModel>(), viewModel)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity())

        viewModel.numbers.observe(requireActivity(), {
            customAdapter.setData(it)
        })

        viewModel.selectedNumber.observe(requireActivity(), {
            customAdapter.setSelectedNumber(it)
        })
    }
}