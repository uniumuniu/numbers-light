package com.example.numberslight.data.repository

import com.example.numberslight.data.remote.NumbersApi
import com.example.numberslight.data.remote.toNumberDetailModel
import com.example.numberslight.data.remote.toNumberModel
import com.example.numberslight.domain.model.NumberDetailModel
import com.example.numberslight.domain.model.NumberModel
import com.example.numberslight.domain.repository.INumbersRepository

class NumbersRepository(
    private val api: NumbersApi
) : INumbersRepository {

    override suspend fun getNumbers(): List<NumberModel> {
        return api.getNumbers().map { it.toNumberModel() }
    }

    override suspend fun getNumberByName(name: String): NumberDetailModel {
        return api.getNumberByName(name).toNumberDetailModel()
    }
}