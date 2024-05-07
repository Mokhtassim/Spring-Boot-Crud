package com.example.crud.service;

import com.example.crud.dto.ProductDTO;
import com.example.crud.entity.Product;
import com.example.crud.exception.ProductException;
import com.example.crud.mapper.ProductMapper;
import com.example.crud.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO addProduct(ProductDTO productDTO) {

        if (productDTO != null && (productDTO.getName() == null || productDTO.getName().isEmpty() || productDTO.getPrice() == 0)) {
            throw new IllegalArgumentException("Product name and price are required.");
        }
        else
        {
         Product product = productMapper.toEntity(productDTO);
         productRepository.save(product);
         return productMapper.toDto(product);
        }
    }

    public ResponseEntity<String> deleteProduct(Long id){
        try{
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()){
                productRepository.deleteById(id);
                return ResponseEntity.ok().body("product "+ id +" has been deleted successfully.");
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("this product "+ id + " not found");
            }

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the product.");
        }
    }
    public List<ProductDTO> getProducts() {
        return productMapper.toDto(productRepository.findAll());
    }
    public Object updateProduct(ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(productDTO.getId());
        if(product.isPresent()){
            Product productMapperEntity = productMapper.toEntity(productDTO);
            productRepository.save(productMapperEntity);

        }else{
            throw new ProductException("Product with ID " + productDTO.getId() + " not found");
        }
     //           .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productDTO.getId() + " not found"));
        //Product product = productMapper.toEntity(productDTO);
        return productDTO;
    }
}
