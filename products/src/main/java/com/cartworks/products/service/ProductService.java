package com.cartworks.products.service;

import com.cartworks.products.dto.ProductDto;

import java.util.List;

public interface ProductService {

    void createProduct(ProductDto productDto);

    ProductDto getProductById(Long id);

    boolean updateProduct(Long id, ProductDto productDto);

    boolean deleteProduct(Long id);

    List<ProductDto> getAllProducts(); // Added method

}
