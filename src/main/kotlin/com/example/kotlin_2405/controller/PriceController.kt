package com.example.kotlin_2405.controller

import com.example.kotlin_2405.dto.CategoryPriceRangeDto
import com.example.kotlin_2405.dto.LowestByCategoryResponseDto
import com.example.kotlin_2405.dto.LowestBySingleBrandResponseDto
import com.example.kotlin_2405.dto.PriceDto
import com.example.kotlin_2405.service.PriceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/prices")
@RestController
class PriceController(private val service: PriceService) {
    @GetMapping("/lowest-category-brands")
    fun getLowestPriceByCategoryAndBrand(): LowestByCategoryResponseDto {
        return service.getLowestPriceByCategoryAndBrand()
    }

    @GetMapping("/lowest-brand")
    fun getLowestPriceBySingleBrand(): LowestBySingleBrandResponseDto {
        return service.getLowestPriceBySingleBrand()
    }

    @GetMapping("/category-price-range")
    fun getPriceRangeByCategory(@RequestParam categoryName: String): CategoryPriceRangeDto {
        return service.getBrandDtoRangeByPrice(categoryName)
    }

    @PostMapping
    fun createPrice(@RequestBody priceDto: PriceDto): ResponseEntity<PriceDto> {
        return ResponseEntity.status(201).body(service.createPrice(priceDto))
    }

    @PutMapping("/{priceId}")
    fun updatePrice(
        @PathVariable priceId: Long,
        @RequestBody priceDto: PriceDto
    ): ResponseEntity<PriceDto> {
        priceDto.id = priceId
        return ResponseEntity.ok(service.updatePrice(priceDto))
    }

    @DeleteMapping("/{priceId}")
    fun deletePrice(@PathVariable priceId: Long): ResponseEntity<Void> {
        service.deletePrice(priceId)
        return ResponseEntity.noContent().build()
    }
}