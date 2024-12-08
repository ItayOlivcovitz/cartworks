package com.cartworks.products.service.imp;

import com.cartworks.products.dto.ProductDto;
import com.cartworks.products.entity.Product;
import com.cartworks.products.exception.ProductAlreadyExistsException;
import com.cartworks.products.exception.ResourceNotFoundException;
import com.cartworks.products.mapper.ProductMapper;
import com.cartworks.products.repository.ProductRepository;
import com.cartworks.products.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // Injected ProductMapper

    @Override
    public void createProduct(ProductDto productDto) {
        // Check if a product with the same name already exists
        if (productRepository.findByName(productDto.getName()).isPresent()) {
            throw new ProductAlreadyExistsException("Product already exists with the given name: " + productDto.getName());
        }

        // Map ProductDto to Product entity and save
        Product product = productMapper.mapToProduct(productDto, new Product());
        productRepository.save(product);
    }

    @Override
    public ProductDto getProductById(Long id) {
        // Find product by ID or throw an exception if not found
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        // Map Product entity to ProductDto
        return productMapper.mapToProductDto(product);
    }

    @Override
    public boolean updateProduct(Long id, ProductDto productDto) {
        // Check if the product exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        // Map ProductDto to existing Product entity and save
        Product updatedProduct = productMapper.mapToProduct(productDto, existingProduct);
        productRepository.save(updatedProduct);
        return true;
    }

    @Override
    public boolean deleteProduct(Long id) {
        // Check if the product exists
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id.toString());
        }

        // Delete the product
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        // Retrieve all products and map them to ProductDto
        return productRepository.findAll().stream()
                .map(productMapper::mapToProductDto)
                .toList();
    }
}
