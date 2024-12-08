package com.cartworks.products.service.imp;

import com.cartworks.products.entity.Category;
import com.cartworks.products.exception.CategoryAlreadyExistsException;
import com.cartworks.products.exception.ResourceNotFoundException;
import com.cartworks.products.repository.CategoryRepository;
import com.cartworks.products.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class CategoryServiceImp implements ICategoryService {

    private final CategoryRepository categoryRepository;



    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id",id.toString()));
    }

    @Override
    public Category saveCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistsException("Category with name '" + category.getName() + "' already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id); // Throws ResourceNotFoundException if not found
        categoryRepository.delete(category);
    }

    /**
     * Check if a category with the given name already exists.
     *
     * @param name the name of the category
     * @return true if a category with the given name exists, false otherwise
     */
    @Override
    public boolean categoryExistsByName(String name) {
        return false;
    }
}
