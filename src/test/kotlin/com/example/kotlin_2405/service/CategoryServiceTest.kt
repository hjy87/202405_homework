package com.example.kotlin_2405.service

import com.example.kotlin_2405.dto.CategoryDto
import com.example.kotlin_2405.dto.NameRequestDto
import com.example.kotlin_2405.entity.CategoryEntity
import com.example.kotlin_2405.exception.ResourceNotFoundException
import com.example.kotlin_2405.repository.CategoryRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class CategoryServiceTest : BehaviorSpec({

    given("createCategory 함수 테스트") {
        val categoryRepository = mockk<CategoryRepository>()
        val categoryService = CategoryService(categoryRepository)

        `when`("중복된 이름으로 카테고리 생성 요청시") {
            val duplicatedName = "중복된 이름"
            val nameRequestDto = NameRequestDto(duplicatedName)
            val duplicatedEntity = CategoryEntity().apply { name = duplicatedName }
            every { categoryRepository.findByName(duplicatedName) } returns listOf(duplicatedEntity)

            then("IllegalStateException이 발생해야 한다.") {
                shouldThrow<IllegalStateException> {
                    categoryService.createCategory(nameRequestDto)
                }
            }
        }

        `when`("중복이 아닌 정상 요청시") {
            val normalName = "정상적인 이름"
            val nameRequestDto = NameRequestDto(normalName)
            val expectedDto = CategoryDto(id = 1L, name = normalName)
            val expectedEntity = CategoryEntity().apply { id = 1L; name = normalName }

            every { categoryRepository.findByName(normalName) } returns listOf()
            every { categoryRepository.save(any<CategoryEntity>()) } answers { expectedEntity }

            val actualResult = categoryService.createCategory(nameRequestDto)

            then("예상되는 객체와 동일해야 한다.") {
                actualResult shouldBe expectedDto
            }
        }
    }

    given("updateCategory 함수 테스트") {
        val categoryRepository = mockk<CategoryRepository>()
        val categoryService = CategoryService(categoryRepository)

        `when`("없는 CategoryId로 수정 요청시") {
            val notExistCategoryId = 999999L
            val nameRequestDto = NameRequestDto(name = "실패하는 카테고리 이름")
            every { categoryRepository.findById(notExistCategoryId) } returns Optional.empty()

            then("ResourceNotFoundException이 발생해야 한다.") {
                shouldThrow<ResourceNotFoundException> {
                    categoryService.updateCategory(nameRequestDto, notExistCategoryId)
                }
            }
        }

        `when`("정상 파라미터로 요청시") {
            val categoryId = 1L
            val oldName = "이전 이름"
            val newName = "변경된 이름"
            val nameRequestDto = NameRequestDto(name = newName)
            val originalEntity = CategoryEntity().apply { id = categoryId; name = oldName }
            val expectedDto = CategoryDto(id = categoryId, name = newName)
            every { categoryRepository.findById(categoryId) } returns Optional.of(originalEntity)
            every { categoryRepository.save(any<CategoryEntity>()) } answers { firstArg() }

            val actualResult = categoryService.updateCategory(nameRequestDto, categoryId)

            then("변경된 이름의 dto가 나와야 한다.") {
                actualResult shouldBe expectedDto
            }
        }
    }

    given("deleteCategory 함수 테스트") {
        val categoryRepository = mockk<CategoryRepository>()
        val categoryService = CategoryService(categoryRepository)

        `when`("존재하지 않는 CategoryId로 삭제 요청시") {
            val notExistCategoryId = 999999L
            every { categoryRepository.findById(notExistCategoryId) } returns Optional.empty()

            then("ResourceNotFoundException이 발생해야 한다.") {
                shouldThrow<ResourceNotFoundException> {
                    categoryService.deleteCategory(notExistCategoryId)
                }
            }
        }

        `when`("정상적인 CategoryId로 삭제 요청시") {
            val categoryId = 1L
            val existingEntity = CategoryEntity().apply { id = categoryId; name = "기존 이름" }
            every { categoryRepository.findById(categoryId) } returns Optional.of(existingEntity)
            every { categoryRepository.delete(existingEntity) } returns Unit

            categoryService.deleteCategory(categoryId)

            then("삭제가 성공해야 한다.") {
                verify { categoryRepository.delete(existingEntity) }
            }
        }
    }
})
