package com.example.kotlin_2405.repository

import com.example.kotlin_2405.entity.CategoryEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByName(name: String): List<CategoryEntity>
}