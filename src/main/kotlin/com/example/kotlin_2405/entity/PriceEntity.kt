package com.example.kotlin_2405.entity

import com.example.kotlin_2405.dto.PriceDto
import jakarta.persistence.*

@Entity
@Table(name = "price")
class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var brandId: Long = 0
    var categoryId: Long = 0
    var price: Long = 0

    fun toDto(): PriceDto {
        return PriceDto().apply {
            id = this@PriceEntity.id
            brandId = this@PriceEntity.brandId
            categoryId = this@PriceEntity.categoryId
            price = this@PriceEntity.price
        }
    }

    fun updateFromDto(priceDto: PriceDto) {
        brandId = priceDto.brandId
        categoryId = priceDto.categoryId
        price = priceDto.price
    }
}