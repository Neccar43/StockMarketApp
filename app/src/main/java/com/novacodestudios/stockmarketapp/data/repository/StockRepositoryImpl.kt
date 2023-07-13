package com.novacodestudios.stockmarketapp.data.repository

import com.novacodestudios.stockmarketapp.data.csv.CSVParser
import com.novacodestudios.stockmarketapp.data.csv.CompanyListingsParser
import com.novacodestudios.stockmarketapp.data.local.StockDao
import com.novacodestudios.stockmarketapp.data.local.StockDatabase
import com.novacodestudios.stockmarketapp.data.mapper.toCompanyListing
import com.novacodestudios.stockmarketapp.data.mapper.toCompanyListingEntity
import com.novacodestudios.stockmarketapp.data.remote.StockApi
import com.novacodestudios.stockmarketapp.domain.model.CompanyListing
import com.novacodestudios.stockmarketapp.domain.repository.StockRepository
import com.novacodestudios.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
) : StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromApi: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val localListing = dao.searchCompanyListings(query)
            emit(Resource.Success(data = localListing.map { it.toCompanyListing() }))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromApi
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data."))
                null
            }

            remoteListings?.let {listings->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(data = dao.searchCompanyListings("").map { it.toCompanyListing() }))
                emit(Resource.Loading(false))
            }

        }
    }

}