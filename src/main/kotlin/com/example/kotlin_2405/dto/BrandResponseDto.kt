package com.example.kotlin_2405.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class BrandResponseDto(
    @JsonProperty("브랜드")
    val brandName: String,
    @JsonProperty("가격")
    val price: Long
)
