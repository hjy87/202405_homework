package com.example.kotlin_2405.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryPriceRangeDto(
    @JsonProperty("카테고리")
    val categoryName: String,
    @JsonProperty("최저가")
    val lowestItem: BrandResponseDto,
    @JsonProperty("최고가")
    val highestItem: BrandResponseDto
)
