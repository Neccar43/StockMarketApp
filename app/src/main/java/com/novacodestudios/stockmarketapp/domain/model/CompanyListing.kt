package com.novacodestudios.stockmarketapp.domain.model

/**
Bu sınıfı oluşturmamızın nedeni yarın bir gün veri kaynağımızı değiştirmemiz
gerektiğinde sadece veri katmanında değişiklik yapmamızı sağlaması için oluşturuyoruz
kısacası burda soyutlama benzeri bir iş yapıyoruz
*/
data class CompanyListing(
    val name:String,
    val symbol:String,
    val exchange:String,
)
