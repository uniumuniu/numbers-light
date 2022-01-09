package com.example.numberslight.data.remote

import com.example.numberslight.domain.model.NumberModel

data class NumberDto(
    val name: String,
    val image: String
)

fun NumberDto.toNumberModel() : NumberModel {
    return NumberModel(
        name,
        image
    )
}