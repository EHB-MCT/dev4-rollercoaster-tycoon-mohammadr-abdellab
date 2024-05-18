package com.example.rollercoastertycoon.config

import com.example.rollercoastertycoon.model.Category
import com.example.rollercoastertycoon.repositories.CategoryRepository
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CategoryDeserializer(@Autowired private val categoryRepository: CategoryRepository) : JsonDeserializer<Category>() {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Category {
        val categoryName = p.valueAsString
        return categoryRepository.findByName(categoryName) ?: throw RuntimeException("Category not found")
    }
}