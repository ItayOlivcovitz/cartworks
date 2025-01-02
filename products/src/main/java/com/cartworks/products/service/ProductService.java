package com.cartworks.products.service;

import com.cartworks.products.dto.ProductsDto;

import java.util.List;

public interface ProductService {

    void createProduct(ProductsDto productDto);

    ProductsDto getProductById(Long id);

    boolean updateProduct(Long id, ProductsDto productDto);

    boolean deleteProduct(Long id);

    List<ProductsDto> getAllProducts(); // Added method

}
