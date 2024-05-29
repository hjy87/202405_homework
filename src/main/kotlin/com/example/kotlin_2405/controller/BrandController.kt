package com.example.kotlin_2405.controller

import com.example.kotlin_2405.dto.BrandDto
import com.example.kotlin_2405.dto.NameRequestDto
import com.example.kotlin_2405.service.BrandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/v1/brands")
@RestController
class BrandController(private val brandService: BrandService) {
    @PostMapping
    fun createBrand(@RequestBody requestDto: NameRequestDto): ResponseEntity<BrandDto> {
        return ResponseEntity.status(201).body(brandService.createBrand(requestDto))
    }

    @PutMapping("/{brandId}")
    fun updateBrand(
        @RequestBody requestDto: NameRequestDto,
        @PathVariable brandId: Long
    ): ResponseEntity<BrandDto> {
        return ResponseEntity.ok(brandService.updateBrand(requestDto, brandId))
    }

    @DeleteMapping("/{brandId}")
    fun deleteBrand(@PathVariable brandId: Long): ResponseEntity<Void> {
        brandService.deleteBrand(brandId)
        return ResponseEntity.noContent().build()
    }
}