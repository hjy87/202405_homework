package com.example.kotlin_2405.repository

import com.example.kotlin_2405.entity.BrandEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<BrandEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByName(name: String): List<BrandEntity>
}