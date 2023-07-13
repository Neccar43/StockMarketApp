package com.novacodestudios.stockmarketapp.presentation.company_listings

import com.novacodestudios.stockmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies:List<CompanyListing> = emptyList(),
    val isLoading:Boolean=false,
    val isRefreshing:Boolean=false,
    val searchQuery:String=""
)