package com.example.crud.controller;

import com.example.crud.dto.ProductDTO;
import com.example.crud.entity.Product;
import com.example.crud.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getALL")
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }
    @PutMapping("/update")
    public Object updateProduct( @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }
}
