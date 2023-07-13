package com.novacodestudios.stockmarketapp.di

import android.app.Application
import androidx.room.Room
import com.novacodestudios.stockmarketapp.data.local.StockDatabase
import com.novacodestudios.stockmarketapp.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create()
            ).build().create()
    }

    @Singleton
    @Provides
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room
            .databaseBuilder(app, StockDatabase::class.java, "stockdb.db")
            .build()
    }
}