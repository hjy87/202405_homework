package com.example.kotlin_2405.service

import com.example.kotlin_2405.dto.CategoryDto
import com.example.kotlin_2405.dto.NameRequestDto
import com.example.kotlin_2405.exception.ResourceNotFoundException
import com.example.kotlin_2405.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val repository: CategoryRepository) {

    fun createCategory(requestDto: NameRequestDto): CategoryDto {
        isDuplicatedExists(requestDto.name)
        return repository.save(requestDto.toCategoryEntity()).toDto()
    }

    fun updateCategory(requestDto: NameRequestDto, categoryId: Long): CategoryDto {
        val entity =
            repository.findById(categoryId).orElseThrow { throw ResourceNotFoundException("category entity not found") }
        entity.updateFromDto(requestDto)
        return repository.save(entity).toDto()
    }

    fun deleteCategory(categoryId: Long) {
        val entity =
            repository.findById(categoryId).orElseThrow { throw ResourceNotFoundException("category entity not found") }
        repository.delete(entity)
    }

    internal fun findByCategoryName(categoryName: String) = repository.findByName(categoryName)
    
    private fun isDuplicatedExists(name: String) {
        check(findByCategoryName(name).isEmpty()) { "duplicated category name exists." }
    }
}