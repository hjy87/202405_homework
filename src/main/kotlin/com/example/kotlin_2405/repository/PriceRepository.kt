package com.example.kotlin_2405.repository

import com.example.kotlin_2405.entity.PriceEntity
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface PriceRepository : JpaRepository<PriceEntity, Long>, KotlinJdslJpqlExecutor