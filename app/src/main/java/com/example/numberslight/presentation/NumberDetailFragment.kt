package com.example.numberslight.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.numberslight.NumbersApplication
import com.example.numberslight.R
import com.squareup.picasso.Picasso


class NumberDetailFragment : Fragment(R.layout.fragment_number_detail) {

    private val viewModel: NumbersViewModel by activityViewModels {
        (requireActivity().applicationContext as NumbersApplication).appComponent.viewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView: ImageView = view.findViewById(R.id.detailImageImageView)
        val nameTextView: TextView = view.findViewById(R.id.detailNameTextView)
        val textTextView: TextView = view.findViewById(R.id.detailTextTextView)

        viewModel.numberDetail.observe(requireActivity(), {
            if (it != null) {
                nameTextView.text = it.name
                val correctedUrl: String = it.image.replace("http", "https")
                Picasso.get().load(correctedUrl).resize(800, 0).into(imageView)
                textTextView.text = it.text
            }
        })
    }
}