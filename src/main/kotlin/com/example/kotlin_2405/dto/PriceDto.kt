package com.example.kotlin_2405.dto

import com.example.kotlin_2405.entity.PriceEntity

data class PriceDto(
    var categoryId: Long = 0,
    var brandId: Long = 0,
    var price: Long = 0
) {
    var id: Long = 0
    fun toEntity(): PriceEntity {
        return PriceEntity().apply {
            price = this@PriceDto.price
            categoryId = this@PriceDto.categoryId
            brandId = this@PriceDto.brandId
        }
    }
}
