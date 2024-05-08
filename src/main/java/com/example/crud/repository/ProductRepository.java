package com.example.crud.repository;
import com.example.crud.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.name like concat('%',:search ,'%') or :search = '' ")
    Page<Product> findByName(String search , Pageable pageable);
}
