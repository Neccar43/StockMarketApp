package com.novacodestudios.stockmarketapp.di

import com.novacodestudios.stockmarketapp.data.csv.CSVParser
import com.novacodestudios.stockmarketapp.data.csv.CompanyListingsParser
import com.novacodestudios.stockmarketapp.data.repository.StockRepositoryImpl
import com.novacodestudios.stockmarketapp.domain.model.CompanyListing
import com.novacodestudios.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Burda hilte CSVParser<CompanyListing> interface sini kullanıyorsak bize bu companyListingsParser: CompanyListingsParser Sınıfını sağla
     */
    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser,
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ):StockRepository

}