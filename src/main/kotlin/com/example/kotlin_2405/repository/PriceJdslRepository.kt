package com.example.kotlin_2405.repository

import com.example.kotlin_2405.dto.BrandPriceSumDto
import com.example.kotlin_2405.dto.BrandResponseDto
import com.example.kotlin_2405.dto.CategoryResponseDto
import com.example.kotlin_2405.dto.LowestPriceDto
import com.example.kotlin_2405.entity.BrandEntity
import com.example.kotlin_2405.entity.CategoryEntity
import com.example.kotlin_2405.entity.PriceEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities.entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions.expression
import org.springframework.stereotype.Repository

@Repository
class PriceJdslRepository(private val repository: PriceRepository) {
    val categoryEntity = entity(CategoryEntity::class, alias = "categoryCode")
    val brandEntity = entity(BrandEntity::class, alias = "brandCode")
    val priceEntity = entity(PriceEntity::class, alias = "stock")

    internal fun findLowestPriceByBrandAndCategory(): List<LowestPriceDto> {
        return repository.findAll {
            val innerPriceEntity = entity(PriceEntity::class, alias = "innerStock")
            selectNew<LowestPriceDto>(
                categoryEntity(CategoryEntity::name),
                brandEntity(BrandEntity::name),
                priceEntity(PriceEntity::price),
                priceEntity(PriceEntity::id),
            ).from(
                priceEntity,
                innerJoin(categoryEntity)
                    .on(priceEntity(PriceEntity::categoryId).eq(categoryEntity(CategoryEntity::id))),
                innerJoin(brandEntity)
                    .on(priceEntity(PriceEntity::brandId).eq(brandEntity(BrandEntity::id)))
            ).where(
                priceEntity(PriceEntity::price).eq(
                    select<Long>(min(innerPriceEntity(PriceEntity::price)))
                        .from(innerPriceEntity)
                        .where(
                            innerPriceEntity(PriceEntity::categoryId)
                                .eq(priceEntity(PriceEntity::categoryId)),
                        ).asSubquery()
                )
            )
                .orderBy(priceEntity(PriceEntity::categoryId).asc())
        }.filterNotNull()
    }

    internal fun getPriceSumByBrand(): List<BrandPriceSumDto> {
        val sum = expression(Long::class, "sum")
        return repository.findAll {
            selectNew<BrandPriceSumDto>(
                sum(priceEntity(PriceEntity::price)).`as`(sum),
                priceEntity(PriceEntity::brandId),
                brandEntity(BrandEntity::name)
            ).from(
                priceEntity,
                innerJoin(brandEntity)
                    .on(priceEntity(PriceEntity::brandId).eq(brandEntity(BrandEntity::id)))
            )
                .groupBy(priceEntity(PriceEntity::brandId)).orderBy(sum.asc())
        }.filterNotNull()
    }

    internal fun getCategoryDtoByBrandId(brandId: Long): List<CategoryResponseDto> {
        return repository.findAll {
            selectNew<CategoryResponseDto>(
                categoryEntity(CategoryEntity::name),
                priceEntity(PriceEntity::price)
            ).from(
                priceEntity,
                innerJoin(categoryEntity)
                    .on(priceEntity(PriceEntity::categoryId).eq(categoryEntity(CategoryEntity::id))),
                innerJoin(brandEntity)
                    .on(priceEntity(PriceEntity::brandId).eq(brandEntity(BrandEntity::id)))
            ).where(priceEntity(PriceEntity::brandId).eq(brandId))
        }.filterNotNull()
    }

    internal fun getBrandDtoRangeByCategoryId(categoryId: Long): List<BrandResponseDto> {
        val minStock = entity(PriceEntity::class, alias = "min")
        val maxStock = entity(PriceEntity::class, alias = "max")
        val price = expression(Long::class, "price")
        return repository.findAll {
            selectNew<BrandResponseDto>(
                brandEntity(BrandEntity::name),
                priceEntity(PriceEntity::price).`as`(price)
            ).from(
                priceEntity,
                innerJoin(categoryEntity)
                    .on(priceEntity(PriceEntity::categoryId).eq(categoryEntity(CategoryEntity::id))),
                innerJoin(brandEntity)
                    .on(priceEntity(PriceEntity::brandId).eq(brandEntity(BrandEntity::id)))
            ).where(
                and(
                    priceEntity(PriceEntity::categoryId).eq(categoryId),
                    or(
                        priceEntity(PriceEntity::price).eq(
                            select<Long>(min(minStock(PriceEntity::price)))
                                .from(minStock).where(
                                    minStock(PriceEntity::categoryId)
                                        .eq(categoryId)
                                ).asSubquery()
                        ),
                        priceEntity(PriceEntity::price).eq(
                            select<Long>(max(maxStock(PriceEntity::price)))
                                .from(maxStock).where(
                                    maxStock(PriceEntity::categoryId)
                                        .eq(categoryId)
                                ).asSubquery()
                        ),
                    )
                )
            ).orderBy(price.asc())
        }.filterNotNull()
    }
}