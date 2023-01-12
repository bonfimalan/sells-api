package io.github.bonfimalan.sells.rest.controller;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

import io.github.bonfimalan.sells.domain.entity.Client;
import io.github.bonfimalan.sells.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientRepository repository;

    @GetMapping("/{id}")
    public Client getById(@PathVariable Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> notFoundException(id));
    }

    @GetMapping
    public List<Client> find(Client filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(CONTAINING);
        Example<Client> example = Example.of(filter, matcher);

        return repository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client post(@RequestBody Client client) {
        return repository.save(client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        repository
                .findById(id)
                .map(resultClient -> {
                    repository.delete(resultClient);
                    return resultClient;
                })
                .orElseThrow(() -> notFoundException(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable Integer id,
            @RequestBody Client client) {
                repository
                        .findById(id)
                        .map(resultClient -> {
                            client.setId(resultClient.getId());
                            repository.save(client);
                            return resultClient;
                        })
                        .orElseThrow(() -> notFoundException(id));
    }

    private ResponseStatusException notFoundException(Integer id) {
        return new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("Client with id %id doesn't exist", id));
    }
}
