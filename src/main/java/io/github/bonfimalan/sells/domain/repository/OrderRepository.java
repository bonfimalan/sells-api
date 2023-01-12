package io.github.bonfimalan.sells.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.bonfimalan.sells.domain.entity.Client;
import io.github.bonfimalan.sells.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClient(Client client);
}
