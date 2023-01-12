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

import io.github.bonfimalan.sells.domain.Client;
import io.github.bonfimalan.sells.service.ClientService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientService service;

    @GetMapping("/{id}")
    public Client getById(@PathVariable Integer id) {
        return service.getClientById(id);
    }

    @GetMapping
    public List<Client> find(Client filter) {
        return service.findAllByFilter(filter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client post(@RequestBody Client client) {
        return service.save(client);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void put(@PathVariable Integer id, @RequestBody Client client) {
        service.update(id, client);
    }
}
