package com.example.kotlin_2405.service

import com.example.kotlin_2405.dto.BrandDto
import com.example.kotlin_2405.dto.NameRequestDto
import com.example.kotlin_2405.entity.BrandEntity
import com.example.kotlin_2405.exception.ResourceNotFoundException
import com.example.kotlin_2405.repository.BrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(private val repository: BrandRepository) {

    private fun findByBrandName(brandName: String): List<BrandEntity> {
        return repository.findByName(brandName)
    }

    fun createBrand(requestDto: NameRequestDto): BrandDto {
        isDuplicatedExists(requestDto.name)
        return repository.save(requestDto.toBrandEntity()).toDto()
    }

    fun updateBrand(requestDto: NameRequestDto, brandId: Long): BrandDto {
        val entity =
            repository.findById(brandId).orElseThrow { throw ResourceNotFoundException("brand entity not found") }
        entity.updateFromDto(requestDto)
        return repository.save(entity).toDto()
    }

    fun deleteBrand(brandId: Long) {
        val entity =
            repository.findById(brandId).orElseThrow { throw ResourceNotFoundException("brand entity not found") }
        repository.delete(entity)
    }

    private fun isDuplicatedExists(name: String) {
        check(findByBrandName(name).isEmpty()) { "duplicated brand name exists." }
    }
}