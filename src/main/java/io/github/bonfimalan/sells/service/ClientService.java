package io.github.bonfimalan.sells.service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import io.github.bonfimalan.sells.domain.Client;
import io.github.bonfimalan.sells.exception.NotFoundException;
import io.github.bonfimalan.sells.repository.ClientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository repository;

    public Client getClientById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(clientNotFoundMessage(id)));
    }

    public List<Client> findAllByFilter(Client filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(CONTAINING);
        Example<Client> example = Example.of(filter, matcher);

        return repository.findAll(example);
    }

    public Client save(Client client) {
        return repository.save(client);
    }

    public void deleteById(Integer id) {
        repository
                .findById(id)
                .map(resultClient -> {
                    repository.delete(resultClient);
                    return resultClient;
                })
                .orElseThrow(
                        () -> new NotFoundException(clientNotFoundMessage(id)));
    }

    public Client update(Integer id, Client client) {
        return repository
                .findById(id)
                .map(resultClient -> {
                    client.setId(resultClient.getId());
                    repository.save(client);
                    return resultClient;
                })
                .orElseThrow(
                    () -> new NotFoundException(clientNotFoundMessage(id)));
    }

    private String clientNotFoundMessage(Integer id) {
        return String.format("Client with id %id doesn't exist", id);
    }
}
