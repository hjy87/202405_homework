package com.example.kotlin_2405.controller

import com.example.kotlin_2405.dto.CategoryDto
import com.example.kotlin_2405.dto.NameRequestDto
import com.example.kotlin_2405.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/categories")
@RestController
class CategoryController(private val categoryService: CategoryService) {
    @PostMapping
    fun createCategory(@RequestBody requestDto: NameRequestDto): ResponseEntity<CategoryDto> {
        return ResponseEntity.status(201).body(categoryService.createCategory(requestDto))
    }

    @PutMapping("/{categoryId}")
    fun updateCategory(
        @RequestBody requestDto: NameRequestDto,
        @PathVariable categoryId: Long
    ): ResponseEntity<CategoryDto> {
        return ResponseEntity.ok(categoryService.updateCategory(requestDto, categoryId))
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Long): ResponseEntity<Void> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity.noContent().build()
    }

}