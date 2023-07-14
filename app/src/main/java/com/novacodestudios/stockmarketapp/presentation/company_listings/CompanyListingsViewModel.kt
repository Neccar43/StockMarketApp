package com.novacodestudios.stockmarketapp.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.novacodestudios.stockmarketapp.domain.repository.StockRepository
import com.novacodestudios.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository,
) : ViewModel() {

    var state by mutableStateOf(CompanyListingsState())

    private var searchJob:Job?=null

    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state=state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob=viewModelScope.launch {
                    delay(500L )
                    getCompanyListings()
                }
            }
            is CompanyListingsEvent.Refresh -> getCompanyListings(fetchFromRemote = true)
        }
    }
    init {
        getCompanyListings()
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            repository.getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> Unit
                        is Resource.Loading -> state = state.copy(isLoading = true)
                        is Resource.Success -> {
                            result.data?.let {
                                state = state.copy(companies = it)
                            }

                        }
                    }

                }
        }
    }
}