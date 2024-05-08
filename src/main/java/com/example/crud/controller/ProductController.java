package com.example.crud.controller;

import com.example.crud.dto.ProductDTO;
import com.example.crud.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
       return productService.addProduct(productDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam(name = "id", required = true) Long id) {
        return productService.deleteProduct(id);
    }
    @PutMapping("/update")
    public Object updateProduct( @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }

    @GetMapping("/getALLPages")
    public Page<ProductDTO> getProductspages(@RequestParam(required = false, defaultValue = "") String search, Pageable pageable) {
        return productService.getProductsPages(search, pageable);

    }
}
