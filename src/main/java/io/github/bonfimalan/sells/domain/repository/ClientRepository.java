package io.github.bonfimalan.sells.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.github.bonfimalan.sells.domain.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query(value = "SELECT * FROM client WHERE name LIKE %?1%", nativeQuery = true)
    List<Client> findByNameLike(String name);
}
