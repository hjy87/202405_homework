package com.example.kotlin_2405.service

import com.example.kotlin_2405.dto.BrandDto
import com.example.kotlin_2405.dto.NameRequestDto
import com.example.kotlin_2405.entity.BrandEntity
import com.example.kotlin_2405.exception.ResourceNotFoundException
import com.example.kotlin_2405.repository.BrandRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class BrandServiceTest : BehaviorSpec({
    given("createBrand 함수 테스트") {
        val brandRepository = mockk<BrandRepository>()
        val brandService = BrandService(brandRepository)
        `when`("중복된 이름 으로 브랜드 생성 요청시") {
            val duplicatedName = "중복된 이름"
            val nameRequestDto = NameRequestDto(duplicatedName)
            val duplicatedEntity = BrandEntity().apply {
                name = duplicatedName
            }
            every { brandRepository.findByName(duplicatedName) } returns listOf(duplicatedEntity)

            then("IllegalStateException 이 발생 해야 한다.") {
                shouldThrow<IllegalStateException> {
                    brandService.createBrand(nameRequestDto)
                }
            }
        }
        `when`("중복이 아닌 정상 요청시") {
            val normalName = "정상적인 이름"
            val nameRequestDto = NameRequestDto(normalName)
            val expectedEntity = BrandEntity().apply {
                id = 1L; name = normalName
            }
            val expectedDto = BrandDto(id = 1L, name = normalName)

            every { brandRepository.findByName(normalName) } returns listOf()
            every { brandRepository.save(any<BrandEntity>()) } answers { expectedEntity }
            val actualResult = brandService.createBrand(nameRequestDto)
            then("예상되는 객체와 동일해야 한다.") {
                actualResult shouldBe expectedDto
            }
        }
    }
    given("updateBrand 함수 테스트") {
        val brandRepository = mockk<BrandRepository>()
        val brandService = BrandService(brandRepository)
        `when`("없는 BrandId로 수정요청시") {
            val notExistBrandId = 999999L
            val nameRequestDto = NameRequestDto(name = "실패하는 브랜드 이름")
            every { brandRepository.findById(notExistBrandId) } returns Optional.empty()
            then("ResourceNotFound Exception이 나야한다.") {
                shouldThrow<ResourceNotFoundException> { brandService.updateBrand(nameRequestDto, notExistBrandId) }
            }
        }
        `when`("정상 파라미터로 요청시") {
            val brandId = 1L
            val oldName = "이전 이름"
            val newName = "변경된 이름"
            val nameRequestDto = NameRequestDto(name = newName)
            val originalEntity = BrandEntity().apply { id = brandId; name = oldName }
            val expectedDto = BrandDto(id = brandId, name = newName)
            every { brandRepository.findById(brandId) } returns Optional.of(originalEntity)
            every { brandRepository.save(any<BrandEntity>()) } answers { firstArg() }
            val actualResult = brandService.updateBrand(nameRequestDto, brandId)
            then("변경된 이름의 dto가 나와야한다.") {
                actualResult shouldBe expectedDto
            }
        }
    }
    given("deleteBrand 함수 테스트") {
        val brandRepository = mockk<BrandRepository>()
        val brandService = BrandService(brandRepository)

        `when`("존재하지 않는 BrandId로 삭제 요청시") {
            val notExistBrandId = 999999L
            every { brandRepository.findById(notExistBrandId) } returns Optional.empty()

            then("ResourceNotFoundException이 발생해야 한다.") {
                shouldThrow<ResourceNotFoundException> {
                    brandService.deleteBrand(notExistBrandId)
                }
            }
        }

        `when`("정상적인 BrandId로 삭제 요청시") {
            val brandId = 1L
            val existingEntity = BrandEntity().apply { id = brandId; name = "브랜드 이름" }
            every { brandRepository.findById(brandId) } returns Optional.of(existingEntity)
            every { brandRepository.delete(existingEntity) } returns Unit
            then("삭제가 성공해야 한다.") {
                shouldNotThrowAny {
                    brandService.deleteBrand(brandId)
                }
                verify { brandRepository.delete(existingEntity) }
            }
        }
    }
})