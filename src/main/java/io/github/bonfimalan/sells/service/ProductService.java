package io.github.bonfimalan.sells.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.exception.NotFoundException;
import io.github.bonfimalan.sells.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> notFoundException(id));
    }

    public List<Product> findAllByFilter(Product filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING);
        Example<Product> example = Example.of(filter, matcher);

        return repository.findAll(example);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product update(Integer id, Product product) {
        return repository
                .findById(id)
                .map(result -> {
                    product.setId(result.getId());
                    return repository.save(product);
                })
                .orElseThrow(() -> notFoundException(id));
    }

    public void deleteById(Integer id) {
        repository
                .findById(id)
                .map(result -> {
                    repository.delete(result);
                    return result;
                })
                .orElseThrow(() -> notFoundException(id));
    }
    

    private NotFoundException notFoundException(Integer id) {
        return new NotFoundException(String.format("Product with id %id doesn't exist", id));
    }
}
