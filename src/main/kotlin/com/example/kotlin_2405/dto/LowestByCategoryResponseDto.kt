package com.example.kotlin_2405.dto

data class LowestByCategoryResponseDto(
    val lowestPriceList: List<LowestPriceDto>,
    val totalPrice: Long,
)