package com.example.numberslight.domain.use_case

import com.example.numberslight.common.Resource
import com.example.numberslight.domain.model.NumberModel
import com.example.numberslight.domain.repository.INumbersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetNumbersUseCase @Inject constructor(
    private val repository: INumbersRepository
) {

    operator fun invoke(): Flow<Resource<List<NumberModel>>> = flow {
        try {
            val numbers = repository.getNumbers()
            emit(Resource.Success<List<NumberModel>>(numbers))
        } catch (e: IOException) {
            emit(Resource.Error<List<NumberModel>>("Couldn't reach the server. Please check your internet connection and try again."))
        } catch (e: Exception) {
            emit(Resource.Error<List<NumberModel>>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}