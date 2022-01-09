package com.example.numberslight.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.numberslight.NumbersApplication
import com.example.numberslight.R

class NoConnectionFragment : Fragment(R.layout.fragment_no_connection) {

    private val viewModel: NumbersViewModel by activityViewModels {
        (requireActivity().applicationContext as NumbersApplication).appComponent.viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tryAgainButton: Button = view.findViewById(R.id.tryAgainButton)
        tryAgainButton.setOnClickListener {
            viewModel.getNumbers()
        }

        val errorTextView: TextView = view.findViewById(R.id.errorTextView)
        viewModel.error.observe(this, {
            errorTextView.text = it
        })
    }
}