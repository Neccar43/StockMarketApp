package com.novacodestudios.stockmarketapp.data.csv

import com.novacodestudios.stockmarketapp.data.mapper.toIntradayInfo
import com.novacodestudios.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.novacodestudios.stockmarketapp.domain.model.CompanyListing
import com.novacodestudios.stockmarketapp.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//Eğer bir sınıfın nasıl oluşturulacağını biliyorsa o zaman onun için provide fonksiyonu yazmamıza gerek kalmaz
//sadece kullanacağımız sınıfın constructor ına @Inject eklememiz yeter
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)// ilk eleman sadece kolon bilgisi içeriyor
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(1) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(timestamp, close.toDouble())
                    dto.toIntradayInfo()
                }.filter {
                    //sadece dünün haberlerini grafiğimizde göstermek istedik
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }.sortedBy {
                    it.date.hour
                }.also {
                    csvReader.close()
                }
        }
    }
}