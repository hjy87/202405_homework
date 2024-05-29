package com.example.kotlin_2405.entity

import com.example.kotlin_2405.dto.BrandDto
import com.example.kotlin_2405.dto.NameRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "brand")
class BrandEntity : BaseNameEntity() {
    fun toDto(): BrandDto {
        return BrandDto(
            this.id,
            this.name
        )
    }

    fun updateFromDto(requestDto: NameRequestDto) {
        name = requestDto.name
    }
}