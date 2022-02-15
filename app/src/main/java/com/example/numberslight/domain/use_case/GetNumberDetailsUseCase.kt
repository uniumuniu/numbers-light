package com.example.numberslight.domain.use_case

import com.example.numberslight.common.Resource
import com.example.numberslight.domain.model.NumberDetailModel
import com.example.numberslight.domain.repository.INumbersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class GetNumberDetailsUseCase @Inject constructor(
    private val repository: INumbersRepository
) {

    operator fun invoke(name: String): Flow<Resource<NumberDetailModel>> = flow {
        try {
            val number = repository.getNumberByName(name)
            emit(Resource.Success<NumberDetailModel>(number))
        } catch (e: IOException) {
            emit(Resource.Error<NumberDetailModel>("Couldn't reach the server. Please check your internet connection and try again."))
        } catch (e: Exception) {
            emit(Resource.Error<NumberDetailModel>(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}