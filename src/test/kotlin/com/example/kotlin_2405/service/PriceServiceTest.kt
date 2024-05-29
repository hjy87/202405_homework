package com.example.kotlin_2405.service

import com.example.kotlin_2405.dto.BrandResponseDto
import com.example.kotlin_2405.dto.CategoryPriceRangeDto
import com.example.kotlin_2405.dto.PriceDto
import com.example.kotlin_2405.entity.CategoryEntity
import com.example.kotlin_2405.entity.PriceEntity
import com.example.kotlin_2405.exception.ResourceNotFoundException
import com.example.kotlin_2405.repository.PriceJdslRepository
import com.example.kotlin_2405.repository.PriceRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class PriceServiceTest : BehaviorSpec({

    given("getBrandDtoRangeByPrice 테스트") {
        val repository = mockk<PriceRepository>()
        val jdslRepository = mockk<PriceJdslRepository>()
        val categoryService = mockk<CategoryService>()
        val service = PriceService(repository, jdslRepository, categoryService)
        `when`("존재하는 '상의' 카테고리로 검색시") {
            val categoryName = "상의"
            every { categoryService.findByCategoryName(categoryName) } returns listOf(CategoryEntity().apply {
                name = "상의"; id = 1L
            })
            val queriedLowestItem = BrandResponseDto(brandName = "C", 10000)
            val queriedHighestItem = BrandResponseDto("I", 11400)
            val jdslResultList = listOf(queriedLowestItem, queriedHighestItem)
            every { jdslRepository.getBrandDtoRangeByCategoryId(1L) } returns jdslResultList
            val expectedResult = CategoryPriceRangeDto(
                categoryName = "상의",
                lowestItem = queriedLowestItem,
                highestItem = queriedHighestItem
            )
            val actualResult = service.getBrandDtoRangeByPrice(categoryName)
            then("실제 결과가 예상결과와 같아야한다.") {
                actualResult shouldBe expectedResult
            }
        }
        `when`("존재하지 않는 팬티로 검색시") {
            val categoryName = "팬티"
            every { categoryService.findByCategoryName(categoryName) } returns listOf()
            then("ResourceNotFound Exception이 나야한다.") {
                shouldThrow<ResourceNotFoundException> { service.getBrandDtoRangeByPrice(categoryName) }
            }
        }
    }
    given("createPrice 함수 테스트") {
        val priceRepository = mockk<PriceRepository>()
        val priceService = PriceService(priceRepository, mockk(), mockk())

        `when`("정상적인 경우") {
            val priceDto = PriceDto(1L, 1L, 1000)
            val expectedEntity = PriceEntity().apply { id = 1L; brandId = 1L; categoryId = 1L; price = 1000 }
            every { priceRepository.save(any<PriceEntity>()) } answers { expectedEntity }

            val actualResult = priceService.createPrice(priceDto)

            then("예상되는 결과와 동일해야 한다.") {
                actualResult shouldBe priceDto
            }
        }
    }

    given("updatePrice 함수 테스트") {
        val priceRepository = mockk<PriceRepository>()
        val priceService = PriceService(priceRepository, mockk(), mockk())

        `when`("존재하지 않는 PriceId로 수정 요청시") {
            val priceDto = PriceDto(categoryId = 1L, 1L, 1000)
            priceDto.id = 999999L
            every { priceRepository.findById(999999L) } returns Optional.empty()

            then("ResourceNotFoundException이 발생해야 한다.") {
                shouldThrow<ResourceNotFoundException> {
                    priceService.updatePrice(priceDto)
                }
            }
        }

        `when`("정상적인 경우") {
            val priceDto = PriceDto(1L, 1L, 2000)
            priceDto.id = 10L
            val existingEntity = PriceEntity().apply { id = 10L; brandId = 1L; categoryId = 1L; price = 1000 }
            val expectedResponse = PriceDto(1L, 1L, 2000)
            every { priceRepository.findById(10L) } returns Optional.of(existingEntity)
            every { priceRepository.save(any<PriceEntity>()) } answers { firstArg() }

            val actualResult = priceService.updatePrice(priceDto)

            then("예상되는 결과와 동일해야 한다.") {
                actualResult shouldBe expectedResponse
            }
        }
    }

    given("deletePrice 함수 테스트") {
        val priceRepository = mockk<PriceRepository>()
        val priceService = PriceService(priceRepository, mockk(), mockk())

        `when`("존재하지 않는 PriceId로 삭제 요청시") {
            val notExistPriceId = 999999L
            every { priceRepository.findById(notExistPriceId) } returns Optional.empty()

            then("ResourceNotFoundException이 발생해야 한다.") {
                shouldThrow<ResourceNotFoundException> {
                    priceService.deletePrice(notExistPriceId)
                }
            }
        }

        `when`("정상적인 PriceId로 삭제 요청시") {
            val priceId = 1L
            val existingEntity = PriceEntity().apply { id = priceId; brandId = 1L; categoryId = 1L; price = 1000 }
            every { priceRepository.findById(priceId) } returns Optional.of(existingEntity)
            every { priceRepository.delete(existingEntity) } returns Unit

            priceService.deletePrice(priceId)

            then("삭제가 성공해야 한다.") {
                verify { priceRepository.delete(existingEntity) }
            }
        }
    }
})
