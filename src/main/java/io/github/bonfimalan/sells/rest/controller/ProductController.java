package io.github.bonfimalan.sells.rest.controller;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.repository.ProductRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductRepository repository;

    @GetMapping("/{id}")
    public Product getByid(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> notFoundException(id));
    }

    @GetMapping
    public List<Product> get(Product product) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING);
        Example<Product> example = Example.of(product, matcher);

        return repository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product post(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable Integer id, @RequestBody Product product) {
        repository
                .findById(id)
                .map(result -> {
                    product.setId(result.getId());
                    return repository.save(product);
                })
                .orElseThrow(() -> notFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(result -> {
                    repository.delete(result);
                    return result;
                })
                .orElseThrow(() -> notFoundException(id));
    }

    private ResponseStatusException notFoundException(Integer id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Product with id %id doesn't exist", id));
    }
}
