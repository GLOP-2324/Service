package com.shoploc.shoploc.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository  extends JpaRepository<Store,Long> {

    Optional<Store> findById (Long id);

    void deleteById (Long id);

}