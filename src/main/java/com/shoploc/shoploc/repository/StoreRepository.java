package com.shoploc.shoploc.repository;

import com.shoploc.shoploc.entity.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository  extends CrudRepository<Store,Long> {

    Optional<Store> findById (Long id);

    void deleteById (Long id);

}
