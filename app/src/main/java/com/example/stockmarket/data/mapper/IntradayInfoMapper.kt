package com.example.stockmarket.data.mapper

import com.example.stockmarket.data.remote.dto.CompanyInfoDto
import com.example.stockmarket.data.remote.dto.IntradayInfoDto
import com.example.stockmarket.domain.model.CompanyInfo
import com.example.stockmarket.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val dateFormat = "yyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close,
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )
}
