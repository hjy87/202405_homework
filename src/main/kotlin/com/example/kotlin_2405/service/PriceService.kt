package com.example.kotlin_2405.service

import com.example.kotlin_2405.dto.*
import com.example.kotlin_2405.exception.ResourceNotFoundException
import com.example.kotlin_2405.repository.PriceJdslRepository
import com.example.kotlin_2405.repository.PriceRepository
import org.springframework.stereotype.Service

@Service
class PriceService(
    private val repository: PriceRepository,
    private val jdslRepository: PriceJdslRepository,
    private val categoryService: CategoryService
) {
    fun getLowestPriceByCategoryAndBrand(): LowestByCategoryResponseDto {
        return jdslRepository.findLowestPriceByBrandAndCategory()
            .filterFirstByCategory()
            .toResponseDto()
    }

    fun getLowestPriceBySingleBrand(): LowestBySingleBrandResponseDto {
        val lowestBrand = jdslRepository.getPriceSumByBrand().first()
        val categoryDtoList = jdslRepository.getCategoryDtoByBrandId(lowestBrand.brandId)
        return LowestBySingleBrandResponseDto(
            SingleBrandDto(
                brandName = lowestBrand.brandName, categoryList = categoryDtoList, sum = lowestBrand.priceSum
            )
        )
    }

    fun getBrandDtoRangeByPrice(categoryName: String): CategoryPriceRangeDto {
        val categoryDto = categoryService.findByCategoryName(categoryName).firstOrNull()?.toDto()
            ?: throw ResourceNotFoundException("unable to find category")
        val brandDtoList = jdslRepository.getBrandDtoRangeByCategoryId(categoryDto.id)
        check(brandDtoList.size == 2) { "error occurs during min max operation" }
        return CategoryPriceRangeDto(categoryName, brandDtoList[0], brandDtoList[1])
    }

    fun createPrice(priceDto: PriceDto): PriceDto {
        return repository.save(priceDto.toEntity()).toDto()
    }

    fun updatePrice(priceDto: PriceDto): PriceDto {
        val entity =
            repository.findById(priceDto.id).orElseThrow { throw ResourceNotFoundException("price entity not found") }
        entity.updateFromDto(priceDto)
        return repository.save(entity).toDto()
    }

    fun deletePrice(priceId: Long) {
        val entity =
            repository.findById(priceId).orElseThrow { throw ResourceNotFoundException("price entity not found") }
        repository.delete(entity)
    }
}