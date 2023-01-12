package io.github.bonfimalan.sells.controller;

import java.util.List;

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

import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.service.ProductService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService service;

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Product> get(Product product) {
        return service.findAllByFilter(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product post(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{id}")
    public Product put(@PathVariable Integer id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
