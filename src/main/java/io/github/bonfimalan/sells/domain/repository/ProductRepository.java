package io.github.bonfimalan.sells.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.bonfimalan.sells.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
