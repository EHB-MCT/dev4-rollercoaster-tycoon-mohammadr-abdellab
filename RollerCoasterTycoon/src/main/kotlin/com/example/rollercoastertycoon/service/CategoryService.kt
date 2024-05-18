package com.example.rollercoastertycoon.service

import com.example.rollercoastertycoon.model.Category
import com.example.rollercoastertycoon.repositories.CategoryRepository
import com.example.rollercoastertycoon.repositories.AttractionRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val attractionRepository: AttractionRepository
) {

    fun createCategory(name: String): Category {
        val category = Category(name = name)
        return categoryRepository.save(category)
    }

    fun updateCategory(id: Long, name: String): Category {
        val category = categoryRepository.findById(id).orElseThrow { NoSuchElementException("Category not found") }
        val updatedCategory = category.copy(name = name)
        return categoryRepository.save(updatedCategory)
    }

    fun deleteCategory(id: Long) {
        val category = categoryRepository.findById(id).orElseThrow { NoSuchElementException("Category not found") }
        
        if (attractionRepository.existsByCategory(category)) {
            throw IllegalStateException("Category cannot be deleted because it has associated attractions")
        }

        categoryRepository.delete(category)
    }

    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }
}

