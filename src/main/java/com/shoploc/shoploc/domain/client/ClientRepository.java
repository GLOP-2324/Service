package com.shoploc.shoploc.domain.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Long> {

    Optional<ClientEntity> findByEmail(String email);
}
