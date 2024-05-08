package com.example.crud.mapper;

import com.example.crud.dto.ProductDTO;
import com.example.crud.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    Product toEntity(ProductDTO productDTO);

    ProductDTO toDto(Product product);
}
