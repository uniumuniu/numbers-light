package com.example.numberslight.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numberslight.common.Constants.UNEXPECTED_ERROR_TEXT
import com.example.numberslight.common.Resource
import com.example.numberslight.domain.model.NumberDetailModel
import com.example.numberslight.domain.model.NumberModel
import com.example.numberslight.domain.use_case.GetNumberDetailUseCase
import com.example.numberslight.domain.use_case.GetNumbersUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NumbersViewModel @Inject constructor(
    private val getNumbersUseCase: GetNumbersUseCase,
    private val getNumberDetailUseCase: GetNumberDetailUseCase
) : ViewModel() {

    private var _numbers = MutableLiveData<List<NumberModel>>()
    val numbers: LiveData<List<NumberModel>> = _numbers

    private var _selectedNumber = MutableLiveData<NumberModel?>(null)
    val selectedNumber: LiveData<NumberModel?> = _selectedNumber

    private var _numberDetail = MutableLiveData<NumberDetailModel?>(null)
    val numberDetail: LiveData<NumberDetailModel?> = _numberDetail

    private var _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        getNumbers()
    }

    private fun getNumber(name: String) {
        getNumberDetailUseCase(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _error.value = null
                    _numberDetail.value = result.data
                }
                is Resource.Error -> {
                    _error.value = result.message ?: UNEXPECTED_ERROR_TEXT
                    setSelectedNumber(null)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getNumbers() {
        getNumbersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _error.value = null
                    _numbers.value = result.data ?: emptyList()
                }
                is Resource.Error -> {
                    _error.value = result.message ?: UNEXPECTED_ERROR_TEXT
                    setSelectedNumber(null)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setSelectedNumber(model: NumberModel?) {
        _selectedNumber.value = model

        if (model != null) {
            getNumber(model.name)
        } else {
            _numberDetail.value = null
        }
    }
}
