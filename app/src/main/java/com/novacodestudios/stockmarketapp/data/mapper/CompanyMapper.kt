package com.novacodestudios.stockmarketapp.data.mapper

import com.novacodestudios.stockmarketapp.data.local.CompanyListingEntity
import com.novacodestudios.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.novacodestudios.stockmarketapp.domain.model.CompanyInfo
import com.novacodestudios.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return CompanyListing(
       name =  name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity{
    return CompanyListingEntity(
        name =  name,
        symbol = symbol,
        exchange = exchange
    )
}

 fun CompanyInfoDto.toCompanyInfo():CompanyInfo{
     return CompanyInfo(
         symbol ?:"",
         description ?:"",
         name ?:"",
         country ?:"",
         industry ?:""
     )
 }
