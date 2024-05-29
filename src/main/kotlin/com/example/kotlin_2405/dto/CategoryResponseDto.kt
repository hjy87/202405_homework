package com.example.kotlin_2405.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryResponseDto(
    @JsonProperty("카테고리")
    val categoryName: String,
    @JsonProperty("가격")
    val price: Long
)
