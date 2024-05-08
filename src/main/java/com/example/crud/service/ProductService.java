package com.example.crud.service;

import com.example.crud.dto.PageableDto;
import com.example.crud.dto.ProductDTO;
import com.example.crud.entity.Product;
import com.example.crud.exception.ProductException;
import com.example.crud.mapper.ProductMapper;
import com.example.crud.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PageableDto<ProductDTO> getProducts(String search, Integer page, Integer size) {
        int offSet = page * size;
        List<ProductDTO> result = productMapper.toDto(productRepository.findAll());
        if(search != null && !search.isBlank())
        {
            result = result.stream()
                           .filter(productDTO -> productDTO.getName().contains(search))
                           .collect(Collectors.toList());
        }
        int totalElements = result.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int toIndex = Math.min(offSet + size, totalElements);
        result = result.subList(offSet, toIndex);
        return PageableDto.<ProductDTO>builder()
                            .content(result)
                            .page(page)
                            .size(size)
                            .totalElements(totalElements)
                            .totalPages(totalPages)
                            .build();
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

    public Page<ProductDTO> getProductsPages(String search, Pageable pageable) {
        Page<Product> result = productRepository.findByName(search,pageable);
        return result.map(productMapper::toDto);
    }
}
