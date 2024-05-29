package com.example.kotlin_2405.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LowestBySingleBrandResponseDto (
    @JsonProperty("최저가")
    val lowest : SingleBrandDto
)