package com.example.numberslight.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.numberslight.R
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NumberDetailsFragment : Fragment(R.layout.fragment_number_detail) {

    private val viewModel: NumberDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView: ImageView = view.findViewById(R.id.detailImageImageView)
        val nameTextView: TextView = view.findViewById(R.id.detailNameTextView)
        val textTextView: TextView = view.findViewById(R.id.detailTextTextView)

        viewModel.numberDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                nameTextView.text = it.name
                Picasso.get().load(it.image).resize(800, 0).into(imageView)
                textTextView.text = it.text
            }
        }
    }
}