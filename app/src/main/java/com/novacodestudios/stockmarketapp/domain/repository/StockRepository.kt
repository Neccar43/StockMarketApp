package com.novacodestudios.stockmarketapp.domain.repository

import android.app.DownloadManager.Query
import com.novacodestudios.stockmarketapp.domain.model.CompanyListing
import com.novacodestudios.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(fetchFromApi:Boolean, query: String):Flow<Resource<List<CompanyListing>>>
}