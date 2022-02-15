package com.example.numberslight.presentation

import androidx.lifecycle.*
import com.example.numberslight.common.Constants
import com.example.numberslight.common.Resource
import com.example.numberslight.domain.model.NumberDetailModel
import com.example.numberslight.domain.use_case.GetNumberDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NumberDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNumberDetailsUseCase: GetNumberDetailsUseCase
): ViewModel() {

    private var _numberDetail = MutableLiveData<NumberDetailModel?>(null)
    val numberDetail: LiveData<NumberDetailModel?> = _numberDetail

    private var _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        val number = savedStateHandle.get<String>("number")
        if (number != null) {
            getNumber(number)
        }
    }

    private fun getNumber(name: String) {
        getNumberDetailsUseCase(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _error.value = null
                    _numberDetail.value = result.data
                }
                is Resource.Error -> {
                    _error.value = result.message ?: Constants.UNEXPECTED_ERROR_TEXT
//                    setSelectedNumber(null)
                }
            }
        }.launchIn(viewModelScope)
    }
}