package com.example.rollercoastertycoon.service

import com.example.rollercoastertycoon.dto.CategoryDTO
import com.example.rollercoastertycoon.model.Category
import com.example.rollercoastertycoon.repositories.CategoryRepository
import com.example.rollercoastertycoon.repositories.AttractionRepository
import org.springframework.stereotype.Service
import org.springframework.data.repository.findByIdOrNull

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val attractionRepository: AttractionRepository
) {

    fun createCategory(categoryDTO: CategoryDTO): CategoryDTO {
        val category = Category(name = categoryDTO.name)
        val savedCategory = categoryRepository.save(category)
        return CategoryDTO(savedCategory.id, savedCategory.name)
    }

    fun updateCategory(categoryDTO: CategoryDTO): CategoryDTO {
        val category = categoryRepository.findByIdOrNull(categoryDTO.id)
            ?: throw NoSuchElementException("Category not found")

        category.name = categoryDTO.name
        val updatedCategory = categoryRepository.save(category)
        return CategoryDTO(updatedCategory.id, updatedCategory.name)
    }

    fun deleteCategory(categoryId: Long) {
        val category = categoryRepository.findByIdOrNull(categoryId)
            ?: throw NoSuchElementException("Category not found")

        if (attractionRepository.existsByCategory(category)) {
            throw IllegalStateException("Category cannot be deleted because it has associated attractions")
        }

        categoryRepository.delete(category)
    }

    fun getAllCategories(): List<CategoryDTO> {
        return categoryRepository.findAll().map { CategoryDTO(it.id, it.name) }
    }
}