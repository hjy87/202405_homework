package com.example.kotlin_2405.entity

import com.example.kotlin_2405.dto.CategoryDto
import com.example.kotlin_2405.dto.NameRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "category")
class CategoryEntity : BaseNameEntity() {
    fun toDto(): CategoryDto {
        return CategoryDto(
            this.id,
            this.name
        )
    }

    fun updateFromDto(requestDto: NameRequestDto) {
        name = requestDto.name
    }
}