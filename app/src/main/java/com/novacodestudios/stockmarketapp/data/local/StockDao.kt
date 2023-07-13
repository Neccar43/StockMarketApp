package com.novacodestudios.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(companyListingEntities:List<CompanyListingEntity>)

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    /**
     * Bu fonksiyon [query] değeri için arama yapar.
     *
     * || sembolü SQL'de string birleştirir.
     * Kotlin'deki + sembolü gibi.
     */

    @Query("""
        SELECT * FROM CompanyListingEntity 
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
        UPPER(:query) == symbol 
    """)
    suspend fun searchCompanyListings(query: String):List<CompanyListingEntity>
}