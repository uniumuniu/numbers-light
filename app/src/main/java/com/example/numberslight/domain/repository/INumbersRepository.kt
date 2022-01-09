package com.example.numberslight.domain.repository

import com.example.numberslight.domain.model.NumberDetailModel
import com.example.numberslight.domain.model.NumberModel

interface INumbersRepository {
    suspend fun getNumbers() : List<NumberModel>

    suspend fun getNumberByName(name: String) : NumberDetailModel
}