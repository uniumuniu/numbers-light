package com.example.numberslight.data.remote

import com.example.numberslight.domain.model.NumberDetailModel

data class NumberDetailDto(
    val name: String,
    val text: String,
    val image: String
)

fun NumberDetailDto.toNumberDetailModel() : NumberDetailModel {
    return NumberDetailModel(
        name,
        text,
        image
    )
}