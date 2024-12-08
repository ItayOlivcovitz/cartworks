package com.cartworks.products.service;

import com.cartworks.products.entity.Category;

import java.util.List;

public interface ICategoryService {

    /**
     * Retrieve all categories.
     *
     * @return a list of all categories
     */
    List<Category> getAllCategories();

    /**
     * Retrieve a category by its ID.
     *
     * @param id the ID of the category
     * @return the category entity
     * @throws com.cartworks.products.exception.ResourceNotFoundException if the category is not found
     */
    Category getCategoryById(Long id);

    /**
     * Save a new category or update an existing category.
     *
     * @param category the category entity to save
     * @return the saved category entity
     * @throws com.cartworks.products.exception.CategoryAlreadyExistsException if a category with the same name already exists
     */
    Category saveCategory(Category category);

    /**
     * Delete a category by its ID.
     *
     * @param id the ID of the category to delete
     * @throws com.cartworks.products.exception.ResourceNotFoundException if the category is not found
     */
    void deleteCategory(Long id);

    /**
     * Check if a category with the given name already exists.
     *
     * @param name the name of the category
     * @return true if a category with the given name exists, false otherwise
     */
    boolean categoryExistsByName(String name);
}
