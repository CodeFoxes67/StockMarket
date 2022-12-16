package com.example.stockmarket.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarket.domain.model.CompanyInfo
import com.example.stockmarket.domain.model.IntradayInfo
import com.example.stockmarket.domain.repository.StockRepository
import com.example.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository,
): ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = repository.getCompanyInfo(symbol)
            val intradayInfoResult = repository.getIntradayInfo(symbol)
            var company: CompanyInfo? = null
            var error: String? = null
            var stockInfo: List<IntradayInfo> = emptyList()
            when (companyInfoResult) {
                is Resource.Success -> {
                    company = companyInfoResult.data
                }
                is Resource.Error -> {
                    error = companyInfoResult.message
                }
                else -> Unit
            }
            when (intradayInfoResult) {
                is Resource.Success -> {
                    stockInfo = intradayInfoResult.data ?: emptyList()
                }
                is Resource.Error -> {
                    error = intradayInfoResult.message
                }
                else -> Unit
            }
            state = state.copy(
                stockInfo = stockInfo,
                company = company,
                isLoading = false,
                error = error,
            )
        }
    }
}
