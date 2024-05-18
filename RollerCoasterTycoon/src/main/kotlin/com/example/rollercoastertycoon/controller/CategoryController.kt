package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.dto.CategoryDTO
import com.example.rollercoastertycoon.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    @PostMapping
    fun createCategory(@RequestBody categoryDTO: CategoryDTO): ResponseEntity<CategoryDTO> {
        val createdCategory = categoryService.createCategory(categoryDTO)
        return ResponseEntity.ok(createdCategory)
    }

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long, @RequestBody categoryDTO: CategoryDTO): ResponseEntity<CategoryDTO> {
        if (id != categoryDTO.id) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Category ID mismatch")
        }

        val updatedCategory = categoryService.updateCategory(categoryDTO)
        return ResponseEntity.ok(updatedCategory)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()

    }

    @GetMapping
    fun getAllCategories(): ResponseEntity<List<CategoryDTO>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }
}
