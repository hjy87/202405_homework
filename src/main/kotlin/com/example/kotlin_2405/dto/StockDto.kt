package com.example.kotlin_2405.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class LowestPriceDto (
    val categoryName : String,
    val brandName : String,
    val price: Long,
    @JsonIgnore
    val id: Long
)

fun List<LowestPriceDto>.toResponseDto(): LowestByCategoryResponseDto {
    val totalPrice = this.sumOf { it.price }
    return LowestByCategoryResponseDto(
        lowestPriceList = this,
        totalPrice = totalPrice
    )
}

fun List<LowestPriceDto>.filterFirstByCategory(): List<LowestPriceDto> {
    return this.groupBy { it.categoryName }
        .mapValues { entry -> entry.value.first() }
        .values
        .toList()
}