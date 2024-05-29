package com.example.kotlin_2405.dto

import com.example.kotlin_2405.entity.BrandEntity
import com.example.kotlin_2405.entity.CategoryEntity

data class NameRequestDto(
    val name: String
) {
    fun toBrandEntity(): BrandEntity {
        return BrandEntity().apply { this.name = this@NameRequestDto.name }
    }

    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity().apply { this.name = this@NameRequestDto.name }
    }
}
