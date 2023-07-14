package com.novacodestudios.stockmarketapp.data.mapper

import com.novacodestudios.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.novacodestudios.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo():IntradayInfo{
    val pattern="yyyy-MM-dd HH:mm:ss"
    val formatter=DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime= LocalDateTime.parse(timestamp,formatter)
    return IntradayInfo(localDateTime,close)
}