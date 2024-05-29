package com.example.kotlin_2405.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SingleBrandDto(
    @JsonProperty("브랜드")
    val brandName: String,
    @JsonProperty("카테고리")
    val categoryList: List<CategoryResponseDto>,
    @JsonProperty("총액")
    val sum: Long
)
